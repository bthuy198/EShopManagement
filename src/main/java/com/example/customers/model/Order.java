package com.example.customers.model;

import com.example.customers.Service.IProductService;
import com.example.customers.Service.mysql.MSProductService;

import java.util.List;

public class Order {
    private long id;
    private String name;
    private String phone;
    private String address;
    private List<OrderItem> orderItems;
    private double total;
    private final IProductService iProductService = new MSProductService();

    public Order() {
    }

    public Order(long id, String name, String phone, String address, double total) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.total = total;
    }

    public Order(long id, String name, String phone, String address, List<OrderItem> orderItems, double total) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.orderItems = orderItems;
        this.total = total;
    }
    public OrderDTO toDTO(){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(this.getId());
        orderDTO.setName(this.getName());
        orderDTO.setPhone(this.getPhone());
        orderDTO.setAddress(this.getAddress());
        return orderDTO;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;

    }

    public double getTotal() {
//        for(int i = 0; i < getOrderItems().size(); i ++){
//            Product p = iProductService.findProductById(getOrderItems().get(i).getIdProduct());
//            this.total += p.getPrice() * orderItems.get(i).getQuantity();
//        }
        return this.total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
