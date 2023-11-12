package glhf.happyfamily.lib;

import java.util.ArrayList;
import javax.management.RuntimeErrorException;
import glhf.happyfamily.models.Balance;

public class Calculator {
    private ArrayList<Balance> balances;
    private int total;

    Calculator(ArrayList<Balance> balances, int total) throws ExceptionInInitializerError {
        this.balances = balances;
        this.total = total;
    }

    public Balance[] calculate() throws RuntimeErrorException {
        if (!canCalculate(total)) {
            throw new RuntimeException("can't pass the validation");
        }
        return null;
    }

    public void appendBalance(Balance balance) {
        this.balances.add(balance);
    }

    private Boolean canCalculate(int total) throws IllegalArgumentException {
        int balanceTotal = 0;
        for (Balance balance : balances) {
            balanceTotal += balance.getPaid();
        }
        if (balanceTotal != this.total) {
            throw new IllegalArgumentException(
                    String.format("the total paid isn't match to input total, total paid:%d, input total:%d",
                            balanceTotal, total));
        }
        return true;
    }

    public ArrayList<Balance> getBalances() {
        return this.balances;
    }

    public void setBalance(ArrayList<Balance> balances) {
        this.balances = balances;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
