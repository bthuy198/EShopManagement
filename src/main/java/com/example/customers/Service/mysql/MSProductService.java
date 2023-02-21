package com.example.customers.Service.mysql;

import com.example.customers.Service.IProductService;
import com.example.customers.config.Resource;
import com.example.customers.model.Customer;
import com.example.customers.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MSProductService extends DBContext implements IProductService {
    private static final String SELECT_ALL_PRODUCTS = "SELECT * FROM product";
    private static final String SELECT_PRODUCT_BY_ID = "SELECT * FROM quanlykhachhang.product WHERE `productId` = ?";
    private static final String INSERT_PRODUCT = "INSERT INTO `product` (`productId`, `name`,`img`, `price`, `quantity`,`description`, `create_at`, `category_id`) VALUES (?, ?, ?, ?, ?, ?, ?,?)";
    private static final String DELETE_PRODUCT_BY_ID = "DELETE FROM `product` WHERE `productId` = ?";

    private static final String UPDATE_PRODUCT_BY_ID = "UPDATE `product` SET `name` = ?,`img` = ?, `price` = ?,`quantity` = ?, `description` = ?, `create_at` = ?, `category_id` = ? WHERE `productId` = ?";
    private static final String CHECK_IMAGE_EXIST = "SELECT * FROM `product` where `img` = ?";
    private static final String SELECT_PRODUCT_BY_CATEGORY_ID = "SELECT * FROM product where category_id = ?";

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        Connection connection = getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(SELECT_ALL_PRODUCTS);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = getProductFromRs(rs);
                products.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    private Product getProductFromRs(ResultSet rs) throws SQLException {
        Long id = rs.getLong("productId");
        String name = rs.getString("name");
        String img = rs.getString("img");
        double price = rs.getDouble("price");
        int quantity = rs.getInt("quantity");
        String description = rs.getString("description");
        java.sql.Date sqlCreate = rs.getDate("create_at");
        java.util.Date uCreate = new java.util.Date(sqlCreate.getTime());

        int category_id = rs.getInt("category_id");

        Product p = new Product(id, name, img, price, quantity, description, uCreate, category_id);
        return p;
    }

    @Override
    public Product findProductById(Long id) {
        Connection connection = getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(SELECT_PRODUCT_BY_ID);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = getProductFromRs(rs);
                return p;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void editProduct(Product product) {
        Connection connection = getConnection();
        try {
            //UPDATE `product` SET `name` = ?,`img` = ?, `price` = ?,`quantity` = ?,
            // `description` = ?, `create_at` = ?, `category_id` = ? WHERE `productId` = ?
            PreparedStatement ps = connection.prepareStatement(UPDATE_PRODUCT_BY_ID);
            ps.setString(1, product.getProductName());
            ps.setString(2, product.getImg());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getQuantity());
            ps.setString(5, product.getDescription());
            ps.setDate(6, new java.sql.Date(product.getCreateAt().getTime()));
            ps.setInt(7, product.getCategoryId());
            ps.setLong(8, product.getProductId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void deleteProductById(Long id) {
        Connection connection = getConnection();
        //DELETE FROM customer` WHERE (`id` = ?)
        try {
            PreparedStatement ps = connection.prepareStatement(DELETE_PRODUCT_BY_ID);
            ps.setLong(1, id);

            ps.executeUpdate();
        } catch (SQLException sqlException) {
            printSQLException(sqlException);
        }
    }

    @Override
    public void createProduct(Product product) {
        Connection connection = getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(INSERT_PRODUCT);
            ps.setLong(1, product.getProductId());
            ps.setString(2, product.getProductName());
            ps.setString(3, product.getImg());
            ps.setDouble(4, product.getPrice());
            ps.setInt(5, product.getQuantity());
            ps.setString(6, product.getDescription());
            ps.setDate(7, new java.sql.Date(product.getCreateAt().getTime()));
            ps.setInt(8, product.getCategoryId());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException sqlException) {
            printSQLException(sqlException);
        }
    }

    @Override
    public boolean checkImageExists(String fileName) {
        Connection connection = getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(CHECK_IMAGE_EXIST);
            ps.setString(1, fileName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public List<Product> getAllProductsByCategoryId(int categoryId) {
        Connection connection = getConnection();
        List<Product> products = new ArrayList<>();
        try{
            PreparedStatement ps = connection.prepareStatement(SELECT_PRODUCT_BY_CATEGORY_ID);
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Product p = getProductFromRs(rs);
                products.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    @Override
    public void updateProductQuantity() {

    }
}
