<%@ page import="com.example.customers.model.Customer" %><%--
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
    <title>Title</title>
</head>
<body>
<div class="container">
    <h3>Edit customer</h3>
    <c:if test="${requestScope.errors!=null && requestScope.errors.size()!=0}">
    <div>
        <c:forEach var="error" items="${requestScope.errors}">
        <p>${error}</p>
        </c:forEach>
    </div>
    </c:if>
    <form  method="post" enctype="multipart/form-data">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Name</label>
            <div class="col-sm-10">
                <input type="text" id="name" class="form-control" name="name"
                       value="${requestScope.customer.getName()}">
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Birthday</label>
            <div class="col-sm-10">
                <input type="date" id="birthday" class="form-control" name="birthday" value="<fmt:formatDate pattern = "yyyy-MM-dd"
                                            value = "${requestScope.customer.getBirthday()}" />">
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Image</label>
            <div class="col-sm-10">
                <input type="file" class="form-control" id="imgInput" name="image" value="${requestScope.customer.getImg()}">
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Address</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="address" name="address"
                       value="${requestScope.customer.getAddress()}">
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Type</label>
            <div class="col-sm-10">
                <select class="form-select" aria-label="Default select example" name="idType" id="idType">
                    <c:forEach var="cType" items="${requestScope.customerTypes}">
                        <c:choose>
                            <c:when test="${requestScope.customer.getIdType() == cType.getId()}">
                                <option value="${cType.getId()}" selected><c:out
                                        value="${cType.getType()}"></c:out></option>
                            </c:when>
                            <c:otherwise>
                                <option value="${cType.getId()}"><c:out value="${cType.getType()}"></c:out></option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group row" style="width: 30%; margin: 20px">
            <button type="submit" class="btn btn-primary mb-3">Save</button>
            <button class="btn btn-primary mb-3" > <a href="/customer" style="color: white; text-decoration: none">Cancel</a></button>
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
        <% String s = ((Customer) request.getAttribute("customer")).getImg();
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
