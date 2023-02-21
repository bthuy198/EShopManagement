<%@ page import="com.example.customers.model.Customer" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 06/02/2023
  Time: 5:24 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/11.7.1/sweetalert2.css"
      integrity="sha512-JzSVRb7c802/njMbV97pjo1wuJAE/6v9CvthGTDxiaZij/TFpPQmQPTcdXyUVucsvLtJBT6YwRb5LhVxX3pQHQ=="
      crossorigin="anonymous" referrerpolicy="no-referrer"/>
<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/11.7.1/sweetalert2.min.js"
        integrity="sha512-vCI1Ba/Ob39YYPiWruLs4uHSA3QzxgHBcJNfFMRMJr832nT/2FBrwmMGQMwlD6Z/rAIIwZFX8vJJWDj7odXMaw=="
        crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/11.7.1/sweetalert2.css"
      integrity="sha512-JzSVRb7c802/njMbV97pjo1wuJAE/6v9CvthGTDxiaZij/TFpPQmQPTcdXyUVucsvLtJBT6YwRb5LhVxX3pQHQ=="
      crossorigin="anonymous" referrerpolicy="no-referrer"/>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css"
      integrity="sha512-SzlrxWUlpfuzQ+pcUCosxcglQRNAq/DZjVsC0lE40xsADsfeQoEypE+enwcOiGjk/bSuGGKHEyjSoQ1zVisanQ=="
      crossorigin="anonymous" referrerpolicy="no-referrer"/>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
      integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<!-- Custom box css -->
<link href="/dashboard/assets\libs\custombox\custombox.min.css" rel="stylesheet">
<html>
<head>
    <title>Danh sách khách hàng</title>
    <style>
        a{
            color: black;
            cursor: pointer;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Danh sách khách hàng</h1>
    <p><a href="/customer?action=create" style="text-decoration: none; color: red">Create new customer</a></p>
    <table class="table table-striped">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Name</th>
            <th scope="col">Birthday</th>
            <th scope="col">Image</th>
            <th scope="col">Address</th>
            <th scope="col">Type</th>
            <th scope="col">Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="c" items="${requestScope.customers}">
            <tr>
                <th scope="row">${c.getId()}</th>
                <td>${c.getName()}</td>
                <td><fmt:formatDate pattern="dd-MM-yyyy" value="${c.getBirthday()}"/></td>
                <td><img src="/images/${c.getImg()}" style="width: 100px; height: auto"></td>
                <td>${c.getAddress()}</td>
                <td>
                    <c:forEach var="cType" items="${requestScope.customerTypes}">
                        <c:if test="${cType.getId() == c.getIdType()}">${cType.getType()}</c:if>
                    </c:forEach>
                </td>
                <td>
                    <a href="/customer?action=edit&id=${c.getId()}"><i class="fa-solid fa-pen-to-square"></i></a>
                    <a onclick="handleClick(${c.getId()})"><i class="fa-solid fa-trash"></i></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <form action="/customer?action=delete" method="post" id="deleteForm">
        <input type="hidden" name="idDelete" id="idDelete" value="">
    </form>
</div>
<script>
    function handleClick(idCustomer){
        document.getElementById("idDelete").value = idCustomer;
        Swal.fire({
            title: 'Are you sure?',
            text: "You won't be able to revert this!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                document.getElementById("deleteForm").submit();
                Swal.fire(
                    'Deleted!',
                    'Your file has been deleted.',
                    'success'
                )
            }
        })
    }
</script>
</body>
</html>
