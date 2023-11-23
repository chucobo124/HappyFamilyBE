package glhf.happyfamily.lib;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import javax.management.RuntimeErrorException;

import glhf.happyfamily.models.Balance;
import glhf.happyfamily.models.Lent;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Calculator {
    private ArrayList<Balance> balances;
    private int total;

    Calculator(ArrayList<Balance> balances, int total) throws ExceptionInInitializerError {
        this.balances = balances;
        this.total = total;
    }

    public Balance[] calculateEqually() throws RuntimeErrorException {
        if (!canCalculate(total)) {
            throw new RuntimeException("can't pass the validation");
        }
        this.balances.sort((x, y) -> Integer.compare(x.getPaid(), y.getPaid()));
        Stack<Object[]> paymentStack = new Stack<Object[]>();
        Map<String, Set<Lent>> lentList = new HashMap<>();
        Integer equalPaid = total / this.balances.size();
        this.balances.forEach((x) -> {
            Integer diff = equalPaid - x.getPaid();
            // when diff is negative, meaning the user lent the money to someone and add
            // to lent list
            if (diff > 0) {
                Set<Lent> currentLent = new HashSet<Lent>();
                currentLent.add(Lent.builder().lent(diff).build());
                x.setLents(currentLent);
                lentList.put(x.getUserID(), x.getLents());
                return;
            }
            // gathering the paid list by user
            Object[] paymentUnit = new Object[2];
            paymentUnit[0] = x.getUserID();
            paymentUnit[1] = Math.abs(diff);
            paymentStack.add(paymentUnit);
        });
        // get lent list
        Object[] payer = paymentStack.pop();
        for (Map.Entry<String, Set<Lent>> x : lentList.entrySet()) {
            String userID = x.getKey();
            Set<Lent> lent = x.getValue();
            Integer totalLent = 0;
            for (Lent l : lent) {
                totalLent += l.getLent();
            }
            Set<Lent> currentUserLentList = new HashSet<Lent>();
            while (totalLent > 0) {
                String payerUserID = payer[0].toString();
                Integer payerAmount = (Integer) payer[1];
                Integer diff = payerAmount - totalLent;
                Integer currentUserLent = totalLent;
                if (diff >= 0) {
                    payer[1] = diff;
                }
                if (diff < 0) {
                    currentUserLent = payerAmount;
                    payer = paymentStack.pop();
                }
                currentUserLentList.add(Lent.builder().lent(currentUserLent).toUserID(payerUserID).build());
                lentList.put(userID, currentUserLentList);
                totalLent -= currentUserLent;
            }
        }
        // convert to balances
        lentList.forEach((userID, lent) -> {
            this.balances.forEach((b) -> {
                if (b.getUserID() == userID) {
                    b.setLents(lent);
                }
            });
        });

        Balance[] result = new Balance[this.balances.size()];
        result = this.balances.toArray(result);

        return result;
    }

    public void appendBalance(Balance balance) {
        this.balances.add(balance);
    }

    // Validate the total to see if total payment can equal to input total
    public Boolean canCalculate(int total) throws IllegalArgumentException {
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
}
