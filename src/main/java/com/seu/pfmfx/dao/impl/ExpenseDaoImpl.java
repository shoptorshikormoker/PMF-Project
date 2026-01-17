package com.seu.pfmfx.dao.impl;

import com.seu.pfmfx.dao.BaseDao;
import com.seu.pfmfx.dao.ExpenseDao;
import com.seu.pfmfx.models.Expense;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDaoImpl extends BaseDao implements ExpenseDao {
	@Override
	public void save(Expense expense) {
		
		String sql = """
            INSERT INTO expense (user_id, category_id, amount, expense_date, description)
            VALUES (?, ?, ?, ?, ?)
        """;
		
		try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
			ps.setInt(1, expense.getUserId());
			ps.setInt(2, expense.getCategoryId());
			ps.setDouble(3, expense.getAmount());
			ps.setDate(4, Date.valueOf(expense.getDate()));
			ps.setString(5, expense.getDescription());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException("Failed to save expense", e);
		}
	}
	
	@Override
	public void update(Expense expense) {
		
		String sql = """
            UPDATE expense
            SET category_id = ?, amount = ?, expense_date = ?, description = ?
            WHERE id = ? AND user_id = ?
        """;
		
		try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
			ps.setInt(1, expense.getCategoryId());
			ps.setDouble(2, expense.getAmount());
			ps.setDate(3, Date.valueOf(expense.getDate()));
			ps.setString(4, expense.getDescription());
			ps.setInt(5, expense.getId());
			ps.setInt(6, expense.getUserId());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException("Failed to update expense", e);
		}
	}
	
	@Override
	public void deleteById(int id) {
		
		String sql = "DELETE FROM expense WHERE id = ?";
		
		try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
			ps.setInt(1, id);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException("Failed to delete expense", e);
		}
	}
	
	@Override
	public List<Expense> findByUser(int userId) {
		
		List<Expense> list = new ArrayList<>();
		String sql = "SELECT * FROM expense WHERE user_id = ? ORDER BY expense_date DESC";
		
		try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Expense e = new Expense();
				e.setId(rs.getInt("id"));
				e.setUserId(userId);
				e.setCategoryId(rs.getInt("category_id"));
				e.setAmount(rs.getDouble("amount"));
				e.setDate(rs.getDate("expense_date").toLocalDate());
				e.setDescription(rs.getString("description"));
				list.add(e);
			}
			
		} catch (SQLException ex) {
			throw new RuntimeException("Failed to load expenses", ex);
		}
		
		return list;
	}
}
