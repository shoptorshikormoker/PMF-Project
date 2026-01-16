package com.seu.pfmfx.dao;

import com.seu.pfmfx.enumaration.CategoryType;
import com.seu.pfmfx.models.Category;

import java.util.List;

public interface CategoryDao {

    void save(Category category);

    List<Category> findAll();

    List<Category> findByType(CategoryType type);
}
