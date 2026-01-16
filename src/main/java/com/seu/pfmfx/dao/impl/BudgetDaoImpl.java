package com.seu.pfmfx.dao.impl;

import com.seu.pfmfx.dao.BaseDao;
import com.seu.pfmfx.dao.BudgetDao;
import com.seu.pfmfx.models.Budget;

import java.sql.*;
import java.time.YearMonth;

public class BudgetDaoImpl extends BaseDao implements BudgetDao {

    @Override
    public void save(Budget budget) {
        String sql = """
            INSERT INTO budget (user_id, category_id, month_year, amount)
            VALUES (?, ?, ?, ?)
        """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, budget.getUserId());
            ps.setInt(2, budget.getCategoryId());
            ps.setString(3, budget.getMonth().toString());
            ps.setDouble(4, budget.getAmount());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save budget", e);
        }
    }

    @Override
    public Budget findByUserAndMonth(int userId, YearMonth month) {
        String sql = "SELECT * FROM budget WHERE user_id = ? AND month_year = ?";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, month.toString());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Budget b = new Budget();
                b.setId(rs.getInt("id"));
                b.setUserId(userId);
                b.setCategoryId(rs.getInt("category_id"));
                b.setMonth(month);
                b.setAmount(rs.getDouble("amount"));
                return b;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find budget", e);
        }
        return null;
    }
}
