package com.seu.pfmfx.dao;

import com.seu.pfmfx.models.Expense;

import java.util.List;

public interface ExpenseDao {
	
	void save(Expense expense);
	
	void update(Expense expense);
	
	void deleteById(int id);
	
	List<Expense> findByUser(int userId);
}
