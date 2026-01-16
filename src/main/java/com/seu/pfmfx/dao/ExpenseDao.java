package com.seu.pfmfx.dao;

import com.seu.pfmfx.models.Expense;

import java.util.List;

public interface ExpenseDao {

    void save(Expense expense);

    List<Expense> findByUser(int userId);
}
