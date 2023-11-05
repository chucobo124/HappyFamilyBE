package glhf.happyfamily.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Balance {
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;
    private String accountingActivityID;
    private String title;
    private String description;
    private int paid;
    private int lent;
    private String userID;

    public String getUuid() {
        return this.uuid;
    }

    public String getAccountingActivityID() {
        return this.accountingActivityID;
    }

    public Balance setAccountingActivityID(String accountingActivityID) {
        this.accountingActivityID = accountingActivityID;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Balance setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public Balance setDescription(String description) {
        this.description = description;
        return this;
    }

    public double getPaid() {
        return this.paid;
    }

    public Balance setPaid(int paid) {
        this.paid = paid;
        return this;
    }

    public double getLent() {
        return this.lent;
    }

    public Balance setLent(int lent) {
        this.lent = lent;
        return this;
    }

    public String getUserID() {
        return this.userID;
    }

    public Balance setUserID(String userID) {
        this.userID = userID;
        return this;
    }

}
