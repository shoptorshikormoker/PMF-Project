package com.seu.pfmfx.models;

import java.time.LocalDate;

public class Expense {

    private int id;
    private int userId;          // FK → User
    private int categoryId;      // FK → Category
    private double amount;
    private LocalDate date;
    private String description;

    public Expense() {}

    public Expense(int expenseId, int userId, int categoryId,
                   double amount, LocalDate date, String description) {
        this.id = expenseId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.date = date;
        this.description = description;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
