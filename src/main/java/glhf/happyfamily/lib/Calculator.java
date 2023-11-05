package glhf.happyfamily.lib;

import glhf.happyfamily.models.Balance;

public class Calculator {
    private Balance[] balances;
    private int total;

    Calculator(Balance[] balances, int total) throws ExceptionInInitializerError {
        this.balances = balances;
        this.total = total;
        int balanceTotal = 0;
        for (Balance balance : balances) {
            balanceTotal += balance.getPaid();
        }
        if (balanceTotal != this.total) {
            throw new ExceptionInInitializerError(
                    String.format("the total paid isn't match to input total, total paid:%d, input total:%d",
                            balanceTotal, total));
        }
    }

    public Balance[] getBalances() {
        return this.balances;
    }

    public void setBalance(Balance[] balances) {
        this.balances = balances;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
