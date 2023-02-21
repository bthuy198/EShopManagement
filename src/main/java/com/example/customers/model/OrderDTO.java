package com.example.customers.model;

import java.util.List;

public class OrderDTO {
    private long id;
    private String name;
    private String phone;
    private String address;
    private List<OrderItemDTO> orderItemDTOS;
    private double total;

    public OrderDTO() {
    }

    public OrderDTO(long id, String name,String phone, String address, List<OrderItemDTO> orderItemDTOS, double total) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.orderItemDTOS = orderItemDTOS;
        this.total = total;
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

    public List<OrderItemDTO> getOrderItemDTOS() {
        return orderItemDTOS;
    }

    public void setOrderItemDTOS(List<OrderItemDTO> orderItemDTOS) {
        this.orderItemDTOS = orderItemDTOS;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
