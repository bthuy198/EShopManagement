package com.example.customers.Service.mysql;

import com.example.customers.Service.ICustomerTypeService;
import com.example.customers.model.CustomerType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MSCustomerTypeService extends DBContext implements ICustomerTypeService{
    private static final String SELLECT_ALL_CUSTOMER_TYPE = "SELECT * FROM customer_type";
    private static final String SELECT_CUSTOMER_TYPE_BY_ID = "SELECT * FROM customer_type where `id` = ?";
    private String jdbcURL = "jdbc:mysql://localhost:3306/quanlykhachhang?allowPublicKeyRetrieval=true&useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "123456";

    @Override
    public List<CustomerType> getAllCustomerType() {
        List<CustomerType> customerTypes = new ArrayList<>();
        Connection connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(SELLECT_ALL_CUSTOMER_TYPE);
            while (rs.next()) {
                CustomerType c = getCustomerTypeFromRs(rs);
                customerTypes.add(c);
            }

        } catch (SQLException e) {
            printSQLException(e);
        }
        return customerTypes;
    }
    private CustomerType getCustomerTypeFromRs(ResultSet rs) throws SQLException {
        CustomerType customerType = new CustomerType();
        long id = rs.getLong("id");
        String type = rs.getString("type");

        customerType.setId(id);
        customerType.setType(type);
        return customerType;
    }

    @Override
    public CustomerType getCustomerTypeById(long id) {
        Connection connection = getConnection();
        try {

            PreparedStatement ps = connection.prepareStatement(SELECT_CUSTOMER_TYPE_BY_ID);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CustomerType c = getCustomerTypeFromRs(rs);
                return c;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return null;
    }
}
