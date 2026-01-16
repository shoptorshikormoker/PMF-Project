package com.seu.pfmfx.models;

import com.seu.pfmfx.enumaration.CategoryType;

public class Category {

    private int id;
    private String name;
    private CategoryType type;
	private int userId;

    public Category() {}

    public Category(int id, String name, CategoryType type, int userId) {
        this.id = id;
        this.name = name;
        this.type = type;
		this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryType getType() {
        return type;
    }

    public void setType(CategoryType type) {
        this.type = type;
    }
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
}
