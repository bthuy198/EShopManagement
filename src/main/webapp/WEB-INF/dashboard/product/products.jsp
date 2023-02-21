<%@ page import="com.example.customers.model.Customer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Minton - Responsive Admin Dashboard Template</title>
    <jsp:include page="/WEB-INF/dashboard/layout/meta-head.jsp"></jsp:include>

</head>

<body>
<c:if test="${requestScope.message == 'delete'}">
    <script>
        window.onload = () => {
            Swal.fire("Deleted!", "Your file has been deleted.", "success")
        }
    </script>
</c:if>

<!-- Begin page -->
<div id="wrapper">

    <jsp:include page="/WEB-INF/dashboard/layout/product-top-bar.jsp"></jsp:include>
    <jsp:include page="/WEB-INF/dashboard/layout/left-bar.jsp"></jsp:include>


    <div class="content-page">
        <div class="content">

            <!-- Start Content-->
            <div class="container-fluid">

                <!-- start page title -->
                <div class="row">
                    <div class="col-12">
                        <div class="page-title-box">
                            <div class="page-title-right">
                                <ol class="breadcrumb m-0">
                                    <li class="breadcrumb-item"><a href="/dashboard">Dashboard</a></li>
                                    <li class="breadcrumb-item">Product</li>
                                </ol>
                            </div>
                            <h4 class="page-title">Products List</h4>
                        </div>
                    </div>
                </div>
                <!-- end page title -->

                <div class="row">
                    <div class="col-lg-12">
                        <div class="card-box">
                            <div class="form-group mr-2 col-3">
                                <button id="demo-btn-addrow" class="btn btn-primary"><a
                                        href="/product?action=create"
                                        style="text-decoration: none; color: white"><i
                                        class="mdi mdi-plus-circle mr-2"></i> Add New Product</a></button>
                            </div>
                            <div class="table-responsive text-center">
                                <table class="table mb-0">
                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Image</th>
                                        <th>Product Name</th>
                                        <th>Price</th>
                                        <th>Quantity</th>
                                        <th>Description</th>
                                        <th>Create At</th>
                                        <th>Category</th>
                                        <th>Action</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="p" items="${requestScope.products}">
                                        <tr>
                                            <th scope="row">${p.getProductId()}</th>
                                            <td><img src="/images/${p.getImg()}" style="width: 70px; height: 70px"></td>
                                            <td>${p.getProductName()}</td>
                                            <td>${p.getPrice()}</td>
                                            <td>${p.getQuantity()}</td>
                                            <td>${p.getDescription()}</td>
                                            <td><fmt:formatDate pattern="dd-MM-yyyy" value="${p.getCreateAt()}"/></td>
                                            <td>
                                                <c:forEach var="pCategory" items="${requestScope.categories}">
                                                    <c:if test="${pCategory.getId() == p.getCategoryId()}">${pCategory.getName()}</c:if>
                                                </c:forEach>
                                            </td>
                                            <td>
                                                <a href="/product?action=edit&productId=${p.getProductId()}"><i class="fa-solid fa-pen-to-square"></i></a>
                                                <a onclick="handleClick(${p.getProductId()})" style="cursor: pointer; color: #7266ba"><i class="fa-solid fa-trash"></i></a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                                <form action="/product?action=delete" method="post" id="deleteForm">
                                    <input type="hidden" name="idDelete" id="idDelete" value="">
                                </form>
                            </div>
                        </div> <!-- end card-box -->
                    </div> <!-- end col -->
                </div>
            </div> <!-- container -->
            <jsp:include page="/WEB-INF/dashboard/layout/footer.jsp"></jsp:include>
        </div> <!-- content -->

    </div>
</div>
<!-- /Right-bar -->

<!-- Right bar overlay-->
<div class="rightbar-overlay"></div>

<!-- Vendor js -->
<script src="/dashboard/assets\js\vendor.min.js"></script>

<!-- Sweet Alerts js -->
<script src="/dashboard/assets\libs\sweetalert2\sweetalert2.min.js"></script>

<!-- Sweet alert init js-->
<script src="/dashboard/assets\js\pages\sweet-alerts.init.js"></script>

<!-- App js -->
<script src="/dashboard/assets\js\app.min.js"></script>
<script src="/dashboard/assets/js/sweetalert2.min.js"></script>
<%--<script>--%>
<%--    let fileName = "";--%>
<%--    window.onload = () =>{--%>
<%--        <% String s = ((Customer) request.getAttribute("customer")).getName();--%>
<%--        System.out.println(s);--%>
<%--        %>--%>
<%--        fileName ="<%= s %>";--%>
<%--    }--%>
<%--</script>--%>
<script>
    function handleClick(idCustomer) {
        document.getElementById("idDelete").value = idCustomer;
        Swal.fire({
            title: "Are you sure?",
            text: "You won't be able to revert this!",
            type: "warning",
            showCancelButton: !0,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Yes, delete it!"
        }).then(function (t) {
            document.getElementById("deleteForm").submit();
            t.value
        })
    }

</script>
</body>
</html>