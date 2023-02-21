package com.example.customers.Service.inmemory;

import com.example.customers.Service.ICustomerTypeService;
import com.example.customers.model.CustomerType;

import java.util.ArrayList;
import java.util.List;

public class CustomerTypeService implements ICustomerTypeService {
    private List<CustomerType> customerTypes;
    public CustomerTypeService(){
        customerTypes = new ArrayList<>();
        customerTypes.add(new CustomerType(1, "VIP"));
        customerTypes.add(new CustomerType(2, "Normal"));
    }

    @Override
    public List<CustomerType> getAllCustomerType() {
        return this.customerTypes;
    }

    @Override
    public CustomerType getCustomerTypeById(long id) {
        for(int i = 0; i < customerTypes.size(); i++){
            if(customerTypes.get(i).getId() == id){
                return customerTypes.get(i);
            }
        }
        return null;
    }
}
