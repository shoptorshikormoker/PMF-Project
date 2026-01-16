package com.seu.pfmfx.dao.impl;

import com.seu.pfmfx.dao.BaseDao;
import com.seu.pfmfx.dao.CategoryDao;
import com.seu.pfmfx.enumaration.CategoryType;
import com.seu.pfmfx.models.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl extends BaseDao implements CategoryDao {

    @Override
    public void save(Category category) {
        String sql = "INSERT INTO category (name, type) VALUES (?, ?)";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, category.getName());
            ps.setString(2, category.getType().name());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save category", e);
        }
    }

    @Override
    public List<Category> findAll() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM category";

        try (Statement st = getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load categories", e);
        }
        return list;
    }

    @Override
    public List<Category> findByType(CategoryType type) {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM category WHERE type = ?";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, type.name());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load categories by type", e);
        }
        return list;
    }

    private Category map(ResultSet rs) throws SQLException {
        Category c = new Category();
        c.setId(rs.getInt("id"));
        c.setName(rs.getString("name"));
        c.setType(CategoryType.valueOf(rs.getString("type")));
        return c;
    }
}
