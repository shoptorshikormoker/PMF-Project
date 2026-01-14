package com.seu.pfmfx.models;

import java.time.YearMonth;

public class Budget {

    private int id;        // PK
    private int userId;          // FK → User
    private double amount;
    private YearMonth month;     // e.g. 2026-01

    public Budget() {}

    public Budget(int budgetId, int userId, double amount, YearMonth month) {
        this.id = budgetId;
        this.userId = userId;
        this.amount = amount;
        this.month = month;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public YearMonth getMonth() {
        return month;
    }

    public void setMonth(YearMonth month) {
        this.month = month;
    }
}
