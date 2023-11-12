package glhf.happyfamily.lib;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import glhf.happyfamily.models.Balance;

public class CalculatorTest {
    final String accountingActivityID = "c3sd";
    private ArrayList<Balance> balances = new ArrayList<Balance>();

    @BeforeEach
    void SetUp() {
        balances = new ArrayList<Balance>();
        balances.add(new Balance().setUserID("user1").setPaid(100).setAccountingActivityID(accountingActivityID));
        balances.add(new Balance().setUserID("user2").setPaid(50).setAccountingActivityID(accountingActivityID));
        balances.add(new Balance().setUserID("user3").setAccountingActivityID(accountingActivityID));
        balances.add(new Balance().setUserID("user4").setAccountingActivityID(accountingActivityID));
    }

    @Test
    void TestInitCalculatorObjectShouldPass() {
        assertDoesNotThrow(() -> {
            new Calculator(balances, 150);
        });
    };

    @Test
    void TestAppendBalance() {
        Calculator calculator = new Calculator(balances, 150);
        assertEquals(4, balances.size(), "assert the origin balance");
        calculator.appendBalance(new Balance().setUserID("user5").setAccountingActivityID(accountingActivityID));
        assertEquals(5, balances.size(), "the balance append should plus one");
    }

    @Test
    void TestCalculateFunctionShouldFailedWhenTotalIsNotMatched() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new Calculator(balances, 50).calculate();
                });
        assertEquals("the total paid isn't match to input total, total paid:150, input total:50",
                exception.getMessage());
    }
}
