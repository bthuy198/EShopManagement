package com.example.customers.controller;

import com.example.customers.Service.ICustomerService;
import com.example.customers.Service.ICustomerTypeService;
import com.example.customers.Service.IProductService;
import com.example.customers.Service.mysql.MSCustomerService;
import com.example.customers.Service.mysql.MSProductService;
import com.example.customers.config.Resource;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AdminServlet", urlPatterns = "/dashboard")
public class AdminServlet extends HttpServlet {
    private IProductService iProductService;
    private ICustomerService iCustomerService;

    @Override
    public void init() throws ServletException {
        iProductService = new MSProductService();
        iCustomerService = new MSCustomerService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int count_product = iProductService.getAllProducts().size();
        int count_customer = iCustomerService.getAllCustomer().size();
        request.setAttribute("count_product", count_product);
        request.setAttribute("count_customer", count_customer);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Resource.folderDashboard + "dashboard.jsp");
        requestDispatcher.forward(request,response);
    }

}
