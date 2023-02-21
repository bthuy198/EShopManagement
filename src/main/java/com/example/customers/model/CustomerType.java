package com.example.customers.model;

public class CustomerType {
    private long id;
    private String type;
    public CustomerType(long id, String type) {
        this.id = id;
        this.type = type;
    }

    public CustomerType(){
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
