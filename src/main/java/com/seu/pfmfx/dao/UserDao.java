package com.seu.pfmfx.dao;

import com.seu.pfmfx.models.User;

public interface UserDao {
	
	void save(User user);
	
	User findByEmail(String email);
	
	User findById(int id);
}
