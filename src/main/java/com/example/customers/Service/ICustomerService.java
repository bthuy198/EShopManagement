package com.example.customers.Service;

import com.example.customers.model.Customer;
import com.example.customers.model.CustomerType;

import java.util.List;

public interface ICustomerService {
    List<Customer> getAllCustomer();
    Customer findCustomerById(Long id);
    void editCustomer(Customer customer);
    void deleteCustomerById(Long id);
    void createCustomer(Customer customer);
    boolean checkImageExists(String fileName);
    List<Customer> searchCustomerAndPaggingNoSort(String keyword, long idCustomerType, int offset, int limit);
    List<Customer> searchCustomerAndPagging(String sortName,String sortOption, String keyword, long idCustomerType, int offset, int limit);
    int getNoOfRecords();
    void setNoOfRecords(int noOfRecords);
    List<Customer> sortByNameASC();
}
