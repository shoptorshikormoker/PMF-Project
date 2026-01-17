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
        String sql = "INSERT INTO category (name, type, user_id) VALUES (?, ?, ?)";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, category.getName());
            ps.setString(2, category.getType().name());
            ps.setInt(3, category.getUserId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save category", e);
        }
    }
	
	@Override
	public Category findById(int id) {
		List<Category> list = new ArrayList<>();
		String sql = "SELECT * FROM category WHERE id = ? ";
		
		try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				return mapToCategory(rs);
			}
		} catch (SQLException e) {
			throw new RuntimeException("Failed to load categories by id", e);
		}
		return null;
	}
	
	@Override
	public List<Category> findAllByUserId(int userId) {
		List<Category> list = new ArrayList<>();
		String sql = "SELECT * FROM category where user_id = ?";
		
		try (PreparedStatement ps = getConnection().prepareStatement(sql)){
		     ps.setInt(1, userId);
		     ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				list.add(mapToCategory(rs));
			}
		} catch (SQLException e) {
			throw new RuntimeException("Failed to load categories", e);
		}
		return list;
	}
	
	@Override
    public List<Category> findAll() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM category";

        try (Statement st = getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapToCategory(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load categories", e);
        }
        return list;
    }

    @Override
    public List<Category> findByType(CategoryType type) {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM category WHERE type = ? ";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, type.name());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapToCategory(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load categories by type", e);
        }
        return list;
    }
	
	@Override
	public List<Category> findByNameAndType(String name, String type) {
		List<Category> list = new ArrayList<>();
		String sql = "SELECT * FROM category WHERE name = ? and type = ? ";
		
		try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
			ps.setString(1, name);
			ps.setString(2, type);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				list.add(mapToCategory(rs));
			}
		} catch (SQLException e) {
			throw new RuntimeException("Failed to load categories by type", e);
		}
		return list;
	}
	
	@Override
	public void update(Category category) {
		
		String sql = "UPDATE category SET name = ?, type = ? WHERE id = ?";
		
		try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
			
			ps.setString(1, category.getName());
			ps.setString(2, category.getType().name());
			ps.setInt(3, category.getId());
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException("Failed to update category", e);
		}
	}
	
	@Override
	public void deleteById(int id) {
		
		String sql = "DELETE FROM category WHERE id = ?";
		
		try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
			
			ps.setInt(1, id);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException("Failed to delete category", e);
		}
	}
	
	private Category mapToCategory(ResultSet rs) throws SQLException {
        Category c = new Category();
        c.setId(rs.getInt("id"));
        c.setName(rs.getString("name"));
        c.setType(CategoryType.valueOf(rs.getString("type")));
        return c;
    }
}
