package glhf.happyfamily.lib;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import glhf.happyfamily.models.Balance;

public class CalculatorTest {
    final String accountingActivityID = "c3sd";
    Balance[] balances = {
            new Balance().setUserID("user1").setPaid(100).setAccountingActivityID(accountingActivityID),
            new Balance().setUserID("user2").setPaid(50).setAccountingActivityID(accountingActivityID),
            new Balance().setUserID("user3").setAccountingActivityID(accountingActivityID),
            new Balance().setUserID("user4").setAccountingActivityID(accountingActivityID),
    };

    @Test
    void InitCalculatorObjectShouldPass() {
        assertDoesNotThrow(() -> {
            new Calculator(balances, 150);
        });
    };

    @Test
    void InitCalculatorObjectShouldFailedWhenTotalIsNotMatched() {
        ExceptionInInitializerError exception = assertThrows(
                ExceptionInInitializerError.class,
                () -> {
                    new Calculator(balances, 50);
                });
        assertEquals("the total paid isn't match to input total, total paid:150, input total:50",
                exception.getMessage());
    }
}
