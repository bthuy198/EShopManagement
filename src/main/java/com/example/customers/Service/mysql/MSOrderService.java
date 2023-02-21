package com.example.customers.Service.mysql;

import com.example.customers.Service.IOrderService;
import com.example.customers.model.Order;
import com.example.customers.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MSOrderService extends DBContext implements IOrderService {
    private static final String SELECT_ALL_ORDERS = "SELECT * FROM order_";
    private static final String SELECT_ORDER_BY_ID = "SELECT * FROM order_ where (`id` = ?)";
    private static final String ADD_ORDER_ITEM = "INSERT INTO `order_item` (`id_order`, `id_product`, `quantity`) VALUES (?, ?, ?)";

    private static final String ADD_ORDER = "INSERT INTO order_product (`name`, `phone`, `address`, `total`) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_PRODUCT_QUANTITY = "insert into product (quantity) values (?)";
    private static final String SELECT_PRODUCT = "select * from product where productId = ?";

    @Override
    public List<Order> getAllOrder() {
        Connection connection = getConnection();
        List<Order> orderList = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(SELECT_ALL_ORDERS);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Order o = getOrderFromRS(rs);
                orderList.add(o);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orderList;
    }

    public Order getOrderFromRS(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String name = rs.getString("name");
        String phone = rs.getString("phone");
        String address = rs.getString("address");
        double total = rs.getDouble("total");

        Order order = new Order(id, name, phone, address, total);
        return order;
    }

    @Override
    public Order getOrderById(long id) {
        Connection connection = getConnection();
        try{
            PreparedStatement ps = connection.prepareStatement(SELECT_ORDER_BY_ID);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Order order = getOrderFromRS(rs);
                return order;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void saveOrder(Order order) {
        Connection connection = getConnection();
        try {
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(ADD_ORDER);

            preparedStatement.setString(1, order.getName());
            preparedStatement.setString(2, order.getPhone());
            preparedStatement.setString(3, order.getAddress());
            preparedStatement.setDouble(4, order.getTotal());
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.executeQuery("SELECT LAST_INSERT_ID()");
            while (rs.next()) {
                order.setId(rs.getLong(1));
            }
            for (int i = 0; i < order.getOrderItems().size(); i++) {
                PreparedStatement ps = connection.prepareStatement(ADD_ORDER_ITEM);
                ps.setLong(1, order.getId());
                ps.setLong(2, order.getOrderItems().get(i).getIdProduct());
                ps.setInt(3, order.getOrderItems().get(i).getQuantity());
                ps.executeUpdate();

//                PreparedStatement ps2 = connection.prepareStatement();
//                connection.prepareStatement("INSERT INTO `order_item` (`id_order`, `id_product`, `quantity`) VALUES ('21', '3', '1')").executeUpdate();
            }

            connection.commit();
        } catch (SQLException sqlException) {
            printSQLException(sqlException);
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
