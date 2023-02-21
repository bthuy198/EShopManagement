package com.example.customers.Service;

import com.example.customers.model.CustomerType;

import java.util.List;

public interface ICustomerTypeService {
    List<CustomerType> getAllCustomerType();
    CustomerType getCustomerTypeById(long id);
}
