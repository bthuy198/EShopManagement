<%@ page import="com.example.customers.model.Product" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 09/02/2023
  Time: 9:05 CH
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
    <title>Edit Product</title>
</head>
<body>
<div class="container">
    <h3>Edit product</h3>
    <c:if test="${requestScope.errors!=null && requestScope.errors.size()!=0}">
    <div>
        <c:forEach var="error" items="${requestScope.errors}">
        <p>${error}</p>
        </c:forEach>
    </div>
    </c:if>
    <form method="post" enctype="multipart/form-data">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Product Name</label>
            <div class="col-sm-10">
                <input type="text" id="name" class="form-control" name="name"
                       value="${requestScope.product.getProductName()}">
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Image</label>
            <div class="col-sm-10">
                <input type="file" class="form-control" id="imgInput" name="image" value="${requestScope.product.getImg()}">
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Price</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="price" name="price"
                       value="${requestScope.product.getPrice()}">
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Quantity</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="quantity" name="quantity"
                       value="${requestScope.product.getQuantity()}">
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Description</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="description" name="description"
                       value="${requestScope.product.getDescription()}">
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Create At</label>
            <div class="col-sm-10">
                <input type="date" id="createAt" class="form-control" name="createAt" value="<fmt:formatDate pattern = "yyyy-MM-dd"
                                            value = "${requestScope.product.getCreateAt()}" />">
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Category</label>
            <div class="col-sm-10">
                <select class="form-select" aria-label="Default select example" name="idCategory" id="idCategory">
                    <c:forEach var="pCategory" items="${requestScope.categories}">
                        <c:choose>
                            <c:when test="${requestScope.product.getCategoryId() == pCategory.getId()}">
                                <option value="${pCategory.getId()}" selected><c:out
                                        value="${pCategory.getName()}"></c:out></option>
                            </c:when>
                            <c:otherwise>
                                <option value="${pCategory.getId()}"><c:out value="${pCategory.getName()}"></c:out></option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group row" style="width: 30%; margin: 20px">
            <button type="submit" class="btn btn-primary mb-3">Save</button>
            <button class="btn btn-primary mb-3" > <a href="/product" style="color: white; text-decoration: none">Cancel</a></button>
        </div>
    </form>
</div>
<script>
    let imgInput = document.getElementById("imgInput");
    let idImage = document.getElementById("idImage");
    imgInput.onchange = evt => {
        const [file] = imgInput.files
        if (file) {
            idImage.src = URL.createObjectURL(file)
        }
    }
    window.onload = ()=>{
        <% String s = ((Product) request.getAttribute("product")).getImg();
            System.out.println(s);
        %>
        let fileName ="<%= s %>";
        console.log(typeof  fileName)

        if(fileName!='null'){
            console.log("File name: " + fileName)
            let url = "http://localhost:8080/images/" + fileName;
            loadURLToInputFiled(url, fileName);
        }


        // document.getElementById("idImage").src = url;
    }
    function loadURLToInputFiled(url, fileName){
        getImgURL(url, (imgBlob)=>{
            // Load img blob to input
            // WIP: UTF8 character error
            let file = new File([imgBlob], fileName,{type:"image/jpeg", lastModified:new Date().getTime()}, 'utf-8');
            let container = new DataTransfer();
            container.items.add(file);
            document.querySelector('#imgInput').files = container.files;

        })
    }
    // xmlHTTP return blob respond
    function getImgURL(url, callback){
        var xhr = new XMLHttpRequest();
        xhr.onload = function() {
            callback(xhr.response);
        };
        xhr.open('GET', url);
        xhr.responseType = 'blob';
        xhr.send();
    }
</script>
</body>
</html>
