package com.example.customers.controller;

import com.example.customers.Service.IOrderService;
import com.example.customers.Service.IProductService;
import com.example.customers.Service.mysql.MSOrderService;
import com.example.customers.Service.mysql.MSProductService;
import com.example.customers.config.Resource;
import com.example.customers.model.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@WebServlet(name = "CartServlet", value = "/cart")
public class CartServlet extends HttpServlet {
    private IProductService iProductService;
    private IOrderService iOrderService;

    @Override
    public void init() throws ServletException {
        iProductService = new MSProductService();
        iOrderService = new MSOrderService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "add":
                handleAddToCart(request, response);
                break;
            case "edit":
                showEditCart(request, response);
                break;
            case "checkout":
                showCheckOutCart(request, response);
                break;
            case "delete":
                showDelete(request, response);
                break;
            default:
                showCart(request, response);

        }
    }

    private void showDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Resource.folderFrontEnd + "cart.jsp");
        requestDispatcher.forward(request, response);
    }

    private void showCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Order order = (Order) session.getAttribute("order");
        if (order != null) {
            request.setAttribute("orderDTO", convertToOrderDTO(order));
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Resource.folderFrontEnd + "cart.jsp");
        requestDispatcher.forward(request, response);
    }

    private OrderDTO convertToOrderDTO(Order order) {
        OrderDTO orderDTO = order.toDTO();
        List<OrderItemDTO> orderItemDTOS = order.getOrderItems().stream().map(new Function<OrderItem, OrderItemDTO>() {
            @Override
            public OrderItemDTO apply(OrderItem orderItem) {
                OrderItemDTO orderItemDTO = new OrderItemDTO();
                orderItemDTO.setQuantity(orderItem.getQuantity());
                orderItemDTO.setId(orderItem.getId());
                Product product = iProductService.findProductById(orderItem.getIdProduct());
                orderItemDTO.setProduct(product);

                return orderItemDTO;
            }
        }).collect(Collectors.toList());
        orderDTO.setTotal(order.getTotal());
        orderDTO.setOrderItemDTOS(orderItemDTOS);
        return orderDTO;
    }

    private void showCheckOutCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Order order = (Order) session.getAttribute("order");
        if (order != null) {
            request.setAttribute("orderDTO", convertToOrderDTO(order));
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Resource.folderFrontEnd + "checkout.jsp");
        requestDispatcher.forward(request, response);
    }

    private void showEditCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Resource.folderFrontEnd + "cart.jsp");
        requestDispatcher.forward(request, response);
    }

    private void handleAddToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Order order = (Order) session.getAttribute("order");
        RequestDispatcher requestDispatcher;
        if(order == null){
            order = new Order();
        }
        long productId = Long.parseLong(request.getParameter("id"));
        // kiêểm tra sản phẩm tồn tại trong giỏ hàng
        if(checkValidateProductId(productId)){
            if(order.getOrderItems() == null){
                OrderItem orderItem = new OrderItem();
                orderItem.setIdProduct(productId);
                orderItem.setQuantity(1);

                List<OrderItem> orderItems = new ArrayList<>();
                orderItems.add(orderItem);

                order.setOrderItems(orderItems);
            }else {
                if(checkExistProductInOrder(order.getOrderItems(), productId)){
                    increaseQuantityInOrder(order.getOrderItems(), productId);
                }else {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setIdProduct(productId);
                    orderItem.setQuantity(1);

                    order.getOrderItems().add(orderItem);
                }
            }
        } else {
            requestDispatcher = request.getRequestDispatcher(Resource.folderFrontEnd + "index.jsp");
            requestDispatcher.forward(request, response);
        }
        updateTotalInOrder(order);
        session.setAttribute("order", order);
        request.setAttribute("orderDTO", convertToOrderDTO(order));

        requestDispatcher = request.getRequestDispatcher(Resource.folderFrontEnd + "cart.jsp");
        requestDispatcher.forward(request, response);
    }

    private void updateTotalInOrder(Order order) {
        double total = 0;
        List<OrderItem> orderItems = order.getOrderItems();
        if(order != null && orderItems != null){
            for(int i = 0;i < orderItems.size(); i++){
                double priceProduct = iProductService.findProductById(orderItems.get(i).getIdProduct()).getPrice();
                total += priceProduct * orderItems.get(i).getQuantity();
            }

        }
        order.setTotal(total);
    }

    private void increaseQuantityInOrder(List<OrderItem> orderItems, long productId) {
        for(int i = 0; i < orderItems.size(); i++){
            if(orderItems.get(i).getIdProduct() == productId){
                int quantityNow = orderItems.get(i).getQuantity();
                orderItems.get(i).setQuantity(quantityNow + 1);
            }
        }
    }

    private boolean checkValidateProductId(long productId) {
        List<Product> products = iProductService.getAllProducts();
        for(int i = 0; i < products.size(); i++){
            if(products.get(i).getProductId() == productId){
                return true;
            }
        }
        return false;
    }

    private boolean checkExistProductInOrder(List<OrderItem> orderItems, long id) {
        for(int i = 0; i < orderItems.size(); i++){
            if(orderItems.get(i).getIdProduct() == id){
                return true;
            }
        }
        return false;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "edit":
                handleEditCart(request, response);
                break;
            case "checkout":
                handleCheckout(request, response);
                break;
            case "delete":
                delete(request,response);
                break;
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long idDelete = Long.parseLong(request.getParameter("idDelete"));
        Product product = iProductService.findProductById(idDelete);

        HttpSession session = request.getSession();
        Order order = (Order) session.getAttribute("order");
        if(order != null && checkIdProductExistInOrder(order, idDelete)){
            List<OrderItem> orderItems = removeItemInOrder(order, idDelete);


            order.setOrderItems(orderItems);

            updateTotalInOrder(order);
            session.setAttribute("order", order);
            request.setAttribute("orderDTO", convertToOrderDTO(order));
        } else{
            request.setAttribute("message", "Sản phẩm không tồn tại");
        }
        response.sendRedirect("cart?message=delete");
//        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Resource.folderFrontEnd + "cart.jsp");
//        requestDispatcher.forward(request, response);
    }

    private List<OrderItem> removeItemInOrder(Order order, long idDelete) {
        List<OrderItem> orderItems = order.getOrderItems();
        for(int i = 0; i < orderItems.size(); i++){
            if(orderItems.get(i).getIdProduct() == idDelete){
                orderItems.remove(i);
            }
        }
        return orderItems;
    }

    private void handleCheckout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        HttpSession session = request.getSession();
        Order order = (Order) session.getAttribute("order");

        if (order != null) {
            order.setAddress(address);
            order.setPhone(phone);
            order.setName(name);
            updateTotalInOrder(order);

            iOrderService.saveOrder(order);
            session.removeAttribute("order");
            response.sendRedirect("/");
        }
    }

    private void handleEditCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long idProduct = Long.parseLong(request.getParameter("idproduct"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        HttpSession session = request.getSession();
        Order order = (Order) session.getAttribute("order");


        if (checkIdProductExistInOrder(order, idProduct)) {
            setQuantityInOrder(order, idProduct, quantity);
            updateTotalInOrder(order);
            session.setAttribute("order", order);

            request.setAttribute("orderDTO", convertToOrderDTO(order));
        }else{
            // báo lỗi không có idproduct
            request.setAttribute("message", "Sản phẩm không tồn tại");
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Resource.folderFrontEnd + "cart.jsp");
        requestDispatcher.forward(request, response);
    }

    private void setQuantityInOrder(Order order, long idProduct, int quantity) {
        for (int i = 0; i < order.getOrderItems().size(); i++) {
            if (order.getOrderItems().get(i).getIdProduct() == idProduct) {
                if (quantity > 0) {
                    order.getOrderItems().get(i).setQuantity(quantity);
                }else{
                    order.getOrderItems().remove(i);
                    break;
                }
            }
        }
    }

    private boolean checkIdProductExistInOrder(Order order, long idProduct) {
        for (int i = 0; i < order.getOrderItems().size(); i++) {
            if (order.getOrderItems().get(i).getIdProduct() == idProduct) {
                return true;
            }
        }
        return false;
    }
}
