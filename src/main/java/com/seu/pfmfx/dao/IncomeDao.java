package com.seu.pfmfx.dao;

import com.seu.pfmfx.models.Income;
import java.util.List;

public interface IncomeDao {
	
	void save(Income income);
	
	void update(Income income);
	
	void deleteById(int id);
	
	List<Income> findByUserId(int userId);
}
