package com.example.customers.controller;

import com.example.customers.Service.ICategory;
import com.example.customers.Service.IProductService;
import com.example.customers.Service.mysql.MSCategoryService;
import com.example.customers.Service.mysql.MSCustomerService;
import com.example.customers.Service.mysql.MSCustomerTypeService;
import com.example.customers.Service.mysql.MSProductService;
import com.example.customers.config.Resource;
import com.example.customers.model.Category;
import com.example.customers.model.Customer;
import com.example.customers.model.CustomerType;
import com.example.customers.model.Product;
import com.example.customers.utils.DateUtils;
import com.example.customers.utils.ValidateUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "ProductServlet", urlPatterns = "/product")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 50, // 50MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class ProductServlet extends HttpServlet {
    private IProductService iProductService;
    private ICategory iCategory;

    @Override
    public void init() throws ServletException {
        iProductService = new MSProductService();
        iCategory = new MSCategoryService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
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
                showProducts(request, response);
                break;
        }
    }

    private void showProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = iProductService.getAllProducts();
        List<Category> categories = iCategory.getAllCategories();

        request.setAttribute("products", products);
        request.setAttribute("categories", categories);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Resource.folderDashboard + "product/products.jsp");
        requestDispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) {
        List<Category> categories = iCategory.getAllCategories();
        request.setAttribute("categories", categories);
        long id = Long.parseLong(request.getParameter("productId"));
        Product product = iProductService.findProductById(id);
        RequestDispatcher requestDispatcher;

        request.setAttribute("product", product);
        requestDispatcher = request.getRequestDispatcher(Resource.folderDashboard + "product/edit.jsp");
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
        Product product = iProductService.findProductById(id);

        request.setAttribute("product", product);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Resource.folderDashboard + "product/delete.jsp");
        requestDispatcher.forward(request, response);
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("categories", iCategory.getAllCategories());
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Resource.folderDashboard + "product/create.jsp");
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
                createProduct(request, response);
                break;
            case "delete":
                deleteProduct(request, response);
                break;
            case "edit":
                editProduct(request, response);
                break;
            default:
                showProducts(request, response);
                break;
        }
    }

    private void editProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> errors = new ArrayList<>();
        List<Category> categories = iCategory.getAllCategories();
        request.setAttribute("categories", categories);
        Product product = new Product();

        isValidateName(request, product, errors);
        isValidatePrice(request, product, errors);
        isValidateQuantity(request, product, errors);
        isValidateCategoryId(request, product, errors);

        Part part = isValidImage(request, product, errors);

        String sCreateAt = request.getParameter("createAt");
        Date createAt = DateUtils.formatDate(sCreateAt);
        String description = request.getParameter("description");

        product.setDescription(description);
        product.setCreateAt(createAt);

        if (errors.size() == 0) {
            long id = Long.parseLong(request.getParameter("productId"));
            product.setProductId(id);
            Product productDB = iProductService.findProductById(product.getProductId());
            if (product.getImg() != null) {
                if (productDB.getImg() != null && !productDB.getImg().equals(product.getImg()) && part != null) {
                    handleEditImageUploadAdvanced(part);
                }
            }
            iProductService.editProduct(product);
            response.sendRedirect("/product");
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(Resource.folderDashboard + "product/edit.jsp");
            request.setAttribute("errors", errors);
            request.setAttribute("product", product);
            requestDispatcher.forward(request, response);
        }
    }

    private void isValidateCategoryId(HttpServletRequest request, Product product, List<String> errors) {
        try {
            int idCategory = Integer.parseInt(request.getParameter("idCategory"));
            if (iCategory.getCategoryById(idCategory) != null) {
                product.setCategoryId(idCategory);
            } else {
                errors.add("Định dạng khách hàng không hợp lệ");
            }
        } catch (NumberFormatException numberFormatException) {
            errors.add("Định dạng loại khách hàng chưa đúng");
        }
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

    private Part isValidImage(HttpServletRequest request, Product product, List<String> errors) throws ServletException, IOException {
        for (Part part : request.getParts()) {
            String fileName = extractFileName(part);
            if(!fileName.equals("")){
                product.setImg(fileName);
                boolean check = iProductService.checkImageExists(fileName);
                if (check == true) {
                    return null;
                }else {
                    return part;
                }
            }
        }
        product.setImg(null);
        return null;
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

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long id = Long.parseLong(request.getParameter("idDelete"));
        iProductService.deleteProductById(id);

        response.sendRedirect("product?message=delete");
    }

    private void createProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = iCategory.getAllCategories();
        request.setAttribute("categories", categories);
        List<String> errors = new ArrayList<>();
        Product product = new Product();

        //String productName, double price, int quantity, String description, Date createAt, int categoryId
       String name = request.getParameter("name");
        if(name.trim().equals("")){
            errors.add("Tên sản phẩm không được để trống");
        }
//        isValidateName(request, product, errors);
        checkProductExist(request, product, errors);
        isValidatePrice(request, product, errors);
        isValidateQuantity(request, product, errors);

        long id = System.currentTimeMillis() / 10000;
        String img = request.getParameter("image");
        String sCreateAt = request.getParameter("createAt");
        Date createAt = DateUtils.formatDate(sCreateAt);
        int idCategory = Integer.parseInt(request.getParameter("idCategory"));
        String description = request.getParameter("description");

        product.setDescription(description);
        product.setProductId(id);
        product.setCreateAt(createAt);
        product.setCategoryId(idCategory);
        product.setImg(img);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Resource.folderDashboard + "/product/create.jsp");

        if (errors.size() == 0) {
            handleImageUpload(request, product, errors);
            request.setAttribute("message", "Created successfully!");
            iProductService.createProduct(product);
            requestDispatcher.forward(request, response);
        } else {
            request.setAttribute("errors", errors);
            request.setAttribute("product", product);
            requestDispatcher.forward(request, response);
        }
    }
    //tồn tại: true; chưa: false
    private void checkProductExist(HttpServletRequest request,Product product, List<String> errors){
        String name = request.getParameter("name");
        List<Product> products = iProductService.getAllProducts();
        for(int i = 0; i < products.size(); i++){
            if(products.get(i).getProductName().equalsIgnoreCase(name)){
                errors.add("Sản phẩm đã tồn tại");
                return;
            }
        }
        product.setProductName(name);
    }
    private void handleImageUpload(HttpServletRequest req, Product product, List<String> errors) throws ServletException, IOException {
        for (Part part : req.getParts()) {
            String fileName = extractFileName(part);
            // refines the fileName in case it is an absolute path
            if(!fileName.equals("")){
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

                product.setImg(fileName);
            }

        }

    }


    private void isValidateName(HttpServletRequest request, Product product, List<String> errors) {
        String name = request.getParameter("name");
        if (!ValidateUtil.isNameValid(name)) {
            errors.add("Tên sản phẩm không hợp lệ");
        } else {
            product.setProductName(name);
        }
    }

    private void isValidatePrice(HttpServletRequest request, Product product, List<String> errors) {
        double price = Double.parseDouble(request.getParameter("price"));
        if (price <= 0) {
            errors.add("Giá tiền không hợp lệ");
        } else {
            product.setPrice(price);
        }

    }

    private void isValidateQuantity(HttpServletRequest request, Product product, List<String> errors) {
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        if (quantity < 0) {
            errors.add("Số lượng không hợp lệ");
        } else {
            product.setQuantity(quantity);
        }

    }
}
