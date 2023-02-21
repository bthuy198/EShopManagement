package com.example.customers.Service;

import com.example.customers.model.Category;

import java.util.List;

public interface ICategory {
    List<Category> getAllCategories();
    Category getCategoryById(int id);
}
