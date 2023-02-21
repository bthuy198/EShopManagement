package com.example.customers.model;

import java.util.Date;

public class Product {
    private long productId;
    private String img;
    private String productName;
    private double price;
    private int quantity;
    private String description;
    private Date createAt;
    private int categoryId;

    public Product() {
    }

    public Product(long productId, String productName, String img, double price, int quantity, String description, Date createAt, int categoryId) {
        this.productId = productId;
        this.img = img;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.createAt = createAt;
        this.categoryId = categoryId;
    }

    public Product(String productName, String img, double price, int quantity, String description, Date createAt, int categoryId) {
        this.productName = productName;
        this.img = img;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.createAt = createAt;
        this.categoryId = categoryId;
    }


    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
