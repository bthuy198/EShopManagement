package com.example.customers.model;

public class OrderItem {
    private long id;
    private long idProduct;
    private int quantity;

    public OrderItem() {
    }

    public OrderItem(long id, long idProduct, int quantity) {
        this.id = id;
        this.idProduct = idProduct;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(long idProduct) {
        this.idProduct = idProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
