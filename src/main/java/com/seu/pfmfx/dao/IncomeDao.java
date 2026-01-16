package com.seu.pfmfx.dao;

import com.seu.pfmfx.models.Income;

import java.util.List;

public interface IncomeDao {

    void save(Income income);

    List<Income> findByUser(int userId);
}
