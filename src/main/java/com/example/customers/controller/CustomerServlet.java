package com.example.customers.controller;

import com.example.customers.Service.inmemory.CustomerService;
import com.example.customers.Service.inmemory.CustomerTypeService;
import com.example.customers.Service.ICustomerService;
import com.example.customers.Service.ICustomerTypeService;
import com.example.customers.Service.mysql.MSCustomerService;
import com.example.customers.Service.mysql.MSCustomerTypeService;
import com.example.customers.config.Resource;
import com.example.customers.model.Customer;
import com.example.customers.model.CustomerType;
import com.example.customers.utils.DateUtils;
import com.example.customers.utils.ValidateUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "CustomerServlet", urlPatterns = {"/customer", "/khachhang"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 50, // 50MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class CustomerServlet extends HttpServlet {
    private ICustomerService iCustomerService;
    private ICustomerTypeService iCustomerTypeService;

    @Override
    public void init() throws ServletException {
        iCustomerService = new MSCustomerService();
        iCustomerTypeService = new MSCustomerTypeService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    showCreateForm(request, response);
                    break;
                case "delete":
                    showDeleteForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                default:
                    showCustomers(request, response);
                    break;
            }
        } catch (ServletException s) {
            s.printStackTrace();
        }

    }
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) {
        List<CustomerType> customerTypes = iCustomerTypeService.getAllCustomerType();
        request.setAttribute("customerTypes", customerTypes);
        long id = Long.parseLong(request.getParameter("id"));
        Customer customer = iCustomerService.findCustomerById(id);
        RequestDispatcher requestDispatcher;

        request.setAttribute("customer", customer);
        requestDispatcher = request.getRequestDispatcher(Resource.folderDashboard + "customer/edit.jsp");
        try {
            requestDispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showDeleteForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        Customer customer = iCustomerService.findCustomerById(id);

        request.setAttribute("customer", customer);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/customer/delete.jsp");
        requestDispatcher.forward(request, response);
    }

    private void showCustomers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String kw = "";
        int limit = 5;
        int page = 1;
        int idCustomerType = -1;
        String sortName = "name";
        String sortOption = "asc";

        if (request.getParameter("kw") != null) {
            kw = request.getParameter("kw");
        }
        if (request.getParameter("ct") != null && !request.getParameter("ct").equals("")) {
            idCustomerType = Integer.parseInt(request.getParameter("ct"));
        }
        if (request.getParameter("page") != null && !request.getParameter("page").equals("")) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        if (request.getParameter("limit") != null && !request.getParameter("limit").equals("")) {
            limit = Integer.parseInt(request.getParameter("limit"));
        }
        if (request.getParameter("sort.name") != null && !request.getParameter("sort.name").equals("")) {
            sortName = request.getParameter("sort.name");
        }
        if (request.getParameter("sort.option") != null && !request.getParameter("sort.option").equals("")) {
            sortOption = request.getParameter("sort.option");
        }
        List<Customer> customers = iCustomerService.searchCustomerAndPagging(sortName, sortOption, kw, idCustomerType, (page - 1) * limit, limit);

//        List<Customer> customers = iCustomerService.searchCustomerAndPaggingNoSort(kw, idCustomerType, (page - 1) * limit, limit);
        List<CustomerType> customerTypes = iCustomerTypeService.getAllCustomerType();

        int noOfRecords = iCustomerService.getNoOfRecords(); // so luong ket qua search
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / limit);


        request.setAttribute("customers", customers);
        request.setAttribute("customerTypes", customerTypes);
        request.setAttribute("kw", kw);
        request.setAttribute("ct", idCustomerType);
        request.setAttribute("sortName", sortName);
        request.setAttribute("sortOption", sortOption);

        request.setAttribute("currentPage", page);
        request.setAttribute("noOfPages", noOfPages);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Resource.folderDashboard + "customer/customers.jsp");
        requestDispatcher.forward(request, response);
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("customerTypes", iCustomerTypeService.getAllCustomerType());
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Resource.folderDashboard + "customer/create.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                createCustomer(request, response);
                break;
            case "delete":
                deleteCustomer(request, response);
                break;
            case "edit":
                editCustomer(request, response);
                break;
            default:
                showCustomers(request, response);
                break;
        }
    }



    private void sortByNameASC(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Customer> customers = iCustomerService.sortByNameASC();
        request.setAttribute("customers", customers);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Resource.folderDashboard + "customers.jsp");
        requestDispatcher.forward(request, response);
    }

    private void editCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<String> errors = new ArrayList<>();
        List<CustomerType> customerTypes = iCustomerTypeService.getAllCustomerType();
        request.setAttribute("customerTypes", customerTypes);
        Customer customer = new Customer();

//        try {
        isValidateName(request, customer, errors);
        isValidateAddress(request, customer, errors);
        isValidateType(request, customer, errors);
        isValidDate(request,customer,errors);

        Part part = isValidImage(request, customer, errors);

//        String sBirthday = request.getParameter("birthday");
//        Date birthday = DateUtils.formatDate(sBirthday);
//        customer.setBirthday(birthday);

        if (errors.isEmpty()) {
            long id = Long.parseLong(request.getParameter("id"));
            customer.setId(id);
            Customer customerDB = iCustomerService.findCustomerById(customer.getId());
            if (customer.getImg() != null) {
                if (customerDB.getImg() != null && !customerDB.getImg().equals(customer.getImg()) && part != null) {
                    handleEditImageUploadAdvanced(part);
                }
            }
            iCustomerService.editCustomer(customer);
            response.sendRedirect("/customer");
        } else {
            request.setAttribute("errors", errors);
            request.setAttribute("customer", customer);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(Resource.folderDashboard + "customer/edit.jsp");
            requestDispatcher.forward(request, response);
        }
//        } catch (Exception e) {
//            errors.add("Định dạng các trường dữ liệu không hợp lệ");
//        }
    }
    private void isValidDate(HttpServletRequest req, Customer customer, List<String> errors) {
        String sBirthday = req.getParameter("birthday");
        if(isEmpty(sBirthday)){
            errors.add("Không được bỏ trống ngày tháng năm");
            return;
        }
        try {
            Date birthday = DateUtils.formatDate(sBirthday);
            customer.setBirthday(birthday);
        } catch (Exception e) {
            e.printStackTrace();
            errors.add("Không được bỏ trống ngày tháng năm");
        }
    }
    private boolean isEmpty(String string){
        if (string == null) {
            return true;
        }else if (string.isEmpty()){
            return true;
        }else
            return false;
    }

    private Part isValidImage(HttpServletRequest request, Customer customer, List<String> errors) throws ServletException, IOException {
        for (Part part : request.getParts()) {
            String fileName = extractFileName(part);
            if (!fileName.equals("")) {
                customer.setImg(fileName);
                boolean check = iCustomerService.checkImageExists(fileName);
                if (check == true) {
                    return null;
                } else {
                    return part;
                }
            }
        }
        customer.setImg(null);
        return null;
    }

    private void handleEditImageUploadAdvanced(Part part) throws IOException {

        String fileName = extractFileName(part);
        String appRealPath = getServletContext().getRealPath("/") + "images";
        File file = new File(appRealPath);
        if (!file.exists()) {
            file.mkdir();
        }
        String nameFileServer = appRealPath + File.separator + fileName;
        System.out.println("Name file server: " + nameFileServer);
        part.write(nameFileServer);
        String nameFileProject = "D:\\CodeGym\\Module-3\\webapplication\\customers\\src\\main\\webapp\\images" + File.separator + fileName;
        System.out.println("Name file project: " + nameFileProject);
        part.write(nameFileProject);

    }

    private void deleteCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long id = Long.parseLong(request.getParameter("idDelete"));
        iCustomerService.deleteCustomerById(id);

        response.sendRedirect("customer?message=delete");

    }

    private void createCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<CustomerType> customerTypes = iCustomerTypeService.getAllCustomerType();
        request.setAttribute("customerTypes", customerTypes);
        List<String> errors = new ArrayList<>();
        Customer customer = new Customer();

        //String name, Date birthday, String address, String img, long idType
        isValidateName(request, customer, errors);
        isValidateAddress(request, customer, errors);
        isValidateType(request, customer, errors);
        isValidDate(request, customer, errors);

        long id = System.currentTimeMillis() / 10000;
//        String sBirthday = request.getParameter("birthday");
//        Date birthday = DateUtils.formatDate(sBirthday);
        String img = request.getParameter("image");
        if(img == null || img.equals("")){
            img = "null_avatar.png";
        }

        customer.setId(id);
        customer.setImg(img);
//        customer.setBirthday(birthday);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Resource.folderDashboard + "customer/create.jsp");
        if (errors.size() == 0) {
            handleImageUpload(request, customer, errors);
            request.setAttribute("message", "Created successfully!");
            iCustomerService.createCustomer(customer);
            requestDispatcher.forward(request, response);
        } else {
            request.setAttribute("errors", errors);
            request.setAttribute("customer", customer);
            requestDispatcher.forward(request, response);
        }
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }

    private void handleImageUpload(HttpServletRequest req, Customer customer, List<String> errors) throws ServletException, IOException {
        for (Part part : req.getParts()) {
            String fileName = extractFileName(part);
            // refines the fileName in case it is an absolute path
            if (!fileName.equals("")) {
                String appRealPath = getServletContext().getRealPath("/") + "images";
                File file = new File(appRealPath);
                if (!file.exists()) {
                    file.mkdir();
                }
                String nameFileServer = appRealPath + File.separator + fileName;
                System.out.println("Name file server: " + nameFileServer);
                part.write(nameFileServer);


                String nameFileProject = "D:\\CodeGym\\Module-3\\webapplication\\customers\\src\\main\\webapp\\images" + File.separator + fileName;
                System.out.println("Name file project: " + nameFileProject);
                part.write(nameFileProject);

                customer.setImg(fileName);
            }

        }

    }
    private void isValidateType(HttpServletRequest request, Customer customer, List<String> errors) {
        try {
            long idType = Long.parseLong(request.getParameter("idType"));
            if (iCustomerTypeService.getCustomerTypeById(idType) != null) {
                customer.setIdType(idType);
            } else {
                errors.add("Định dạng khách hàng không hợp lệ");
            }
        } catch (NumberFormatException numberFormatException) {
            errors.add("Định dạng loại khách hàng chưa đúng");
        }
    }

    private void isValidateAddress(HttpServletRequest request, Customer customer, List<String> errors) {
        String address = request.getParameter("address");
        if (!ValidateUtil.isAddressValid(address)) {
            errors.add("Địa chỉ người dùng không hợp lệ");
        } else {
            customer.setAddress(address);
        }
    }

    private void isValidateName(HttpServletRequest request, Customer customer, List<String> errors) {
        String name = request.getParameter("name");
        if (!ValidateUtil.isNameValid(name)) {
            errors.add("Tên người dùng không hợp lệ");
        } else {
            customer.setName(name);
        }
    }
}
