package glhf.happyfamily.lib;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import glhf.happyfamily.models.Balance;
import glhf.happyfamily.models.Lent;

public class CalculatorTest {
    final String accountingActivityID = "c3sd";
    private ArrayList<Balance> balances = new ArrayList<Balance>();
    private Integer expectedTotal = 150;

    @BeforeEach
    void SetUp() {
        balances = new ArrayList<Balance>();
        balances.add(Balance.builder().userID("user1").paid(100).accountingActivityID(accountingActivityID).build());
        balances.add(Balance.builder().userID("user2").paid(50).accountingActivityID(accountingActivityID).build());
        balances.add(Balance.builder().userID("user3").accountingActivityID(accountingActivityID).build());
        balances.add(Balance.builder().userID("user4").accountingActivityID(accountingActivityID).build());
    }

    @Test
    void TestInitCalculatorObjectShouldPass() {
        assertDoesNotThrow(() -> {
            new Calculator(balances, expectedTotal);
        });
    };

    @Test
    void TestAppendBalance() {
        Calculator calculator = new Calculator(balances, expectedTotal);
        assertEquals(4, balances.size(), "assert the origin balance");
        calculator.appendBalance(Balance.builder().userID("user5").accountingActivityID(accountingActivityID).build());
        assertEquals(5, balances.size(), "the balance append should plus one");
    }

    @Test
    void TestCalculateFunctionShouldFailedWhenTotalIsNotMatched() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new Calculator(balances, 50).calculateEqually();
                });
        assertEquals("the total paid isn't match to input total, total paid:150, input total:50",
                exception.getMessage());
    }

    @Test
    void TestCalculatorShouldReturnsTheEquallyResult() {
        Calculator calculator = new Calculator(balances, expectedTotal);
        Balance[] balances = calculator.calculateEqually();
        assertEquals(balances.length, 4);
        // Total paid 150, $37.5 (trim to 37) for each when separate equally
        for (Balance b : balances) {
            switch (b.getUserID()) {
                case "user1":
                    assertEquals(null, b.getLents(),
                            "the first user paid the bill, shouldn't lent to anyone");
                    break;
                case "user2":
                    assertEquals(null, b.getLents(),
                            "the second user paid the bill, shouldn't lent to anyone");
                    break;
                case "user3":
                    // the first user had borrow 63 to others
                    // the second user had borrow 13 to others
                    Lent actualLent = (Lent) b.getLents().toArray()[0];
                    assertEquals(
                            actualLent.getToUserID(), "user1",
                            "lent to first user, since first user paid the hedgiest amount");
                    assertEquals(
                            actualLent.getLent(), 37,
                            "lent to first user, since first user paid the hedgiest amount");
                    break;
                case "user4":
                    // the first user had borrow 26 to others
                    // the second user had borrow 13 to others
                    Lent actualLent1 = (Lent) b.getLents().toArray()[0];
                    Lent actualLent2 = (Lent) b.getLents().toArray()[1];
                    assertEquals(
                            "user1",
                            actualLent2.getToUserID(),
                            "lent to first user, since the balance is separate between both");
                    assertEquals(
                            actualLent2.getLent(), 26,
                            "lent to second user, since the balance is separate between both");
                    assertEquals(
                            actualLent1.getToUserID(), "user2",
                            "lent to first user, since the balance is separate between both");
                    assertEquals(
                            actualLent1.getLent(), 11,
                            "lent to second user, since the balance is separate between both");
                    break;
            }
        }
    }
}
