package com.seu.pfmfx.dao;

import com.seu.pfmfx.enumaration.CategoryType;
import com.seu.pfmfx.models.Category;

import java.util.List;

public interface CategoryDao {
	
    void save(Category category);
	
	Category findById(int id);
	
    List<Category> findAllByUserId(int userId);
    List<Category> findAll();
	
    List<Category> findByType(CategoryType type);
	
	List<Category> findByNameAndType(String name, String type);
	
	void update(Category category);
	
	void deleteById(int id);
}
