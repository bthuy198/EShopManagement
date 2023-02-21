package com.example.customers.Service.inmemory;

import com.example.customers.Service.ICustomerService;
import com.example.customers.model.Customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerService implements ICustomerService {
    private List<Customer> customerList;

    public CustomerService() {
        customerList = new ArrayList<>();

        //String name, Date birthday, String address, String img
        customerList.add(new Customer(1L, "Mai Văn Hoàn",new Date(), "HN", null, 1));
        customerList.add(new Customer(2L, "Mai Văn Nam",new Date(), "HN", null, 2));
        customerList.add(new Customer(3L, "Trần Đăng Khoa",new Date(), "Huế", null, 1));
        customerList.add(new Customer(4L, "Nguyễn Đình Thi",new Date(), "SG", null, 1));

    }

    @Override
    public List<Customer> getAllCustomer() {
        return this.customerList;
    }

    @Override
    public Customer findCustomerById(Long id) {
        for(int i = 0; i < customerList.size(); i++){
            if(customerList.get(i).getId() == id){
                return customerList.get(i);
            }
        }
        return null;
    }

    @Override
    public void editCustomer(Customer customer) {
        for(int i = 0; i < customerList.size(); i++){
            if(customerList.get(i).getId() == customer.getId()){
                customerList.get(i).setAddress(customer.getAddress());
                customerList.get(i).setBirthday(customer.getBirthday());
                customerList.get(i).setImg(customer.getImg());
                customerList.get(i).setName(customer.getName());
            }
        }
    }

    @Override
    public void deleteCustomerById(Long id) {
        for(int i = 0; i < customerList.size(); i++){
            if(customerList.get(i).getId() == id){
                customerList.remove(i);
            }
        }
    }

    @Override
    public void createCustomer(Customer customer) {

    }

    @Override
    public boolean checkImageExists(String fileName) {
        return false;
    }

    @Override
    public List<Customer> searchCustomerAndPaggingNoSort(String keyword, long idCustomerType, int offset, int limit) {
        return null;
    }

    @Override
    public List<Customer> searchCustomerAndPagging(String sortName, String sortOption, String keyword, long idCustomerType, int offset, int limit) {
        return null;
    }


    @Override
    public int getNoOfRecords() {
        return 0;
    }

    @Override
    public void setNoOfRecords(int noOfRecords) {

    }

    @Override
    public List<Customer> sortByNameASC() {
        return null;
    }

    public void add(Customer customer) {
        customerList.add(customer);
    }
}
