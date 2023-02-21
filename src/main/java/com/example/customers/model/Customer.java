package com.example.customers.model;

import java.util.Date;

public class Customer {
    private long id;
    private String name;
    private Date birthday;
    private String address;
    private String img;
    private long idType;

    public Customer() {
    }

    public Customer(String name, Date birthday, String address, String img, long idType) {
        this.name = name;
        this.birthday = birthday;
        this.address = address;
        this.img = img;
        this.idType = idType;
    }
    public Customer(long id, String name, Date birthday, String address, String img,long idType) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.address = address;
        this.img = img;
        this.idType = idType;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public long getIdType() {
        return idType;
    }

    public void setIdType(long idType) {
        this.idType = idType;
    }
}
