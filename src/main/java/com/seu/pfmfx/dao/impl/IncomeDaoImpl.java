package com.seu.pfmfx.dao.impl;

import com.seu.pfmfx.dao.BaseDao;
import com.seu.pfmfx.dao.IncomeDao;
import com.seu.pfmfx.models.Income;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IncomeDaoImpl extends BaseDao implements IncomeDao {
	
	@Override
	public void save(Income income) {
		
		String sql = """
            INSERT INTO income (user_id, category_id, amount, income_date, description)
            VALUES (?, ?, ?, ?, ?)
        """;
		
		try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
			ps.setInt(1, income.getUserId());
			ps.setInt(2, income.getCategoryId());
			ps.setDouble(3, income.getAmount());
			ps.setDate(4, Date.valueOf(income.getDate()));
			ps.setString(5, income.getDescription());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException("Failed to save income", e);
		}
	}
	
	@Override
	public void update(Income income) {
		
		String sql = """
            UPDATE income
            SET category_id = ?, amount = ?, income_date = ?, description = ?
            WHERE id = ? AND user_id = ?
        """;
		
		try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
			ps.setInt(1, income.getCategoryId());
			ps.setDouble(2, income.getAmount());
			ps.setDate(3, Date.valueOf(income.getDate()));
			ps.setString(4, income.getDescription());
			ps.setInt(5, income.getId());
			ps.setInt(6, income.getUserId());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException("Failed to update income", e);
		}
	}
	
	@Override
	public void deleteById(int id) {
		
		String sql = "DELETE FROM income WHERE id = ?";
		
		try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
			ps.setInt(1, id);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException("Failed to delete income", e);
		}
	}
	
	@Override
	public List<Income> findByUserId(int userId) {
		
		List<Income> list = new ArrayList<>();
		String sql = "SELECT * FROM income WHERE user_id = ? ORDER BY income_date DESC";
		
		try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Income i = new Income();
				i.setId(rs.getInt("id"));
				i.setUserId(userId);
				i.setCategoryId(rs.getInt("category_id"));
				i.setAmount(rs.getDouble("amount"));
				i.setDate(rs.getDate("income_date").toLocalDate());
				i.setDescription(rs.getString("description"));
				list.add(i);
			}
			
		} catch (SQLException e) {
			throw new RuntimeException("Failed to load incomes", e);
		}
		
		return list;
	}
}
