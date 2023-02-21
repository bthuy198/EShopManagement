package com.example.customers.controller;

import com.example.customers.Service.IProductService;
import com.example.customers.Service.mysql.MSProductService;
import com.example.customers.config.Resource;
import com.example.customers.model.Product;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "HomeServlet", urlPatterns = "")
public class HomeServlet extends HttpServlet {
    private IProductService iProductService;

    @Override
    public void init() throws ServletException {
        iProductService = new MSProductService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = iProductService.getAllProducts();
        List<Product> shirts = iProductService.getAllProductsByCategoryId(1);
        List<Product> pants = iProductService.getAllProductsByCategoryId(2);
        List<Product> accessories = iProductService.getAllProductsByCategoryId(3);

        request.setAttribute("products", products);
        request.setAttribute("shirts", shirts);
        request.setAttribute("pants", pants);
        request.setAttribute("accessories", accessories);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Resource.folderFrontEnd + "index.jsp");
        requestDispatcher.forward(request,response);
    }

}
