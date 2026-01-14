package com.seu.pfmfx.models;

import java.time.LocalDate;

public class Transaction {

    private int id;
    private String type;       // INCOME / EXPENSE
    private String category;
    private double amount;
    private LocalDate date;

    public Transaction() {}

    public Transaction(int id, String type, String category,
                       double amount, LocalDate date) {
        this.id = id;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
}
