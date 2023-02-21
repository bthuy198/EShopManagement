<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 07/02/2023
  Time: 10:23 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css"
      integrity="sha512-SzlrxWUlpfuzQ+pcUCosxcglQRNAq/DZjVsC0lE40xsADsfeQoEypE+enwcOiGjk/bSuGGKHEyjSoQ1zVisanQ=="
      crossorigin="anonymous" referrerpolicy="no-referrer"/>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
      integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<html>
<head>
    <title>Create new customer</title>
    <style>
        .message {
            color: green;
        }
    </style>
</head>
<body>
<div class="container" style="width: 50%">


    <h3>Create new customer</h3>
    <p>
        <a href="/customer">Back to customer list</a>
    </p>

    <form action="/customer?action=create" method="post" enctype="multipart/form-data">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Name</label>
            <div class="col-sm-10">
                <input type="text" id="name" class="form-control" name="name" value = "${requestScope.customers.getName()}">
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Birthday</label>
            <div class="col-sm-10">
                <input type="date" id="birthday" class="form-control" name="birthday" value="<fmt:formatDate pattern = "yyyy-MM-dd"
                                            value = "${requestScope.customers.getBirthday()}" />">
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Image</label>
            <div class="col-sm-10">
                <input type="file" class="form-control" id="image" name="image" value="${requestScope.customer.getImg()}">
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Address</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="address" name="address" value = "${requestScope.customers.getAddress()}">
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Type</label>
            <div class="col-sm-10">
                <select class="form-select" aria-label="Default select example" name="idType" id="idType">
                    <c:forEach var="cType" items="${requestScope.customerTypes}">
                        <option value="${cType.getId()}"><c:out value="${cType.getType()}" ></c:out></option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group row" style="width: 30%; margin: 20px">
            <button type="submit" class="btn btn-primary mb-3">Create</button>
        </div>
    </form>
</div>
</body>
</html>
