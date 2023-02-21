package com.example.customers.model;

public class OrderItemDTO {
    private long id;
    private Product product;
    private int quantity;
    private double total;

    public OrderItemDTO() {
    }

    public OrderItemDTO(long id, Product product, double total, int quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.total = total;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
