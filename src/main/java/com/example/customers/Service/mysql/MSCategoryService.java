package com.example.customers.Service.mysql;

import com.example.customers.Service.ICategory;
import com.example.customers.model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MSCategoryService extends DBContext implements ICategory {
    private static final String SELECT_ALL_CATEGORIES = "SELECT * FROM quanlykhachhang.category";
    private static final String SELECT_CATEGORY_BY_ID = "SELECT * FROM quanlykhachhang.category WHERE `category_id` = ?";
    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        Connection connection = getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(SELECT_ALL_CATEGORIES);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Category c = getCategoryFromRs(rs);
                categories.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categories;
    }

    public Category getCategoryFromRs(ResultSet rs) throws SQLException {
        Category category = new Category();
        int id = rs.getInt("category_id");
        String name = rs.getString("name");

        category.setId(id);
        category.setName(name);
        return category;
    }
    @Override
    public Category getCategoryById(int id) {
        Connection connection = getConnection();
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CATEGORY_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Category c = getCategoryFromRs(rs);
                return c;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return null;
    }
}
