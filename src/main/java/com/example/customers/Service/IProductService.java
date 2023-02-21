package com.example.customers.Service;

import com.example.customers.model.Product;

import java.util.List;

public interface IProductService {
    List<Product> getAllProducts();
    Product findProductById(Long id);
    void editProduct(Product product);
    void deleteProductById(Long id);
    void createProduct(Product product);
    boolean checkImageExists(String fileName);

    List<Product> getAllProductsByCategoryId(int categoryId);
    void updateProductQuantity();
}
