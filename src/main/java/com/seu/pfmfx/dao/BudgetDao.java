package com.seu.pfmfx.dao;

import com.seu.pfmfx.models.Budget;

import java.time.YearMonth;

public interface BudgetDao {

    void save(Budget budget);

    Budget findByUserAndMonth(int userId, YearMonth month);
}
