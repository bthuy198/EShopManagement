<%@ page import="com.example.customers.model.Product" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Minton - Responsive Admin Dashboard Template</title>
    <jsp:include page="/WEB-INF/dashboard/layout/meta-head.jsp"></jsp:include>
</head>

<body>

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
                                    <li class="breadcrumb-item"><a href="javascript: void(0);">Minton</a></li>
                                    <li class="breadcrumb-item"><a href="javascript: void(0);">Forms</a></li>
                                    <li class="breadcrumb-item active">Create</li>
                                </ol>
                            </div>
                            <h4 class="page-title">Edit product</h4>
                        </div>
                    </div>
                </div>
                <!-- end page title -->

                <div class="row">
                    <div class="col-12">
                        <div class="card-box">
                            <c:if test="${requestScope.message != null}">
                                <div class="alert alert-success alert-dismissible fade show" role="alert">
                                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                        ${requestScope.message}
                                </div>
                            </c:if>
                            <c:if test="${requestScope.errors!=null && requestScope.errors.size()!=0}">
                                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                    <c:forEach var="error" items="${requestScope.errors}">
                                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                        <ul>
                                            <li>${error}</li>
                                        </ul>
                                    </c:forEach>
                                </div>
                            </c:if>
                            <h4 class="header-title">Edit product's information</h4>

                            <div class="row">
                                <div class="col-12">
                                    <div class="p-2">
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
                                                    <input type="file" class="form-control" id="imgInput" name="image"
                                                           value="${requestScope.product.getImg()}">
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
                                                    <input type="text" class="form-control" id="quantity"
                                                           name="quantity"
                                                           value="${requestScope.product.getQuantity()}">
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-sm-2 col-form-label">Description</label>
                                                <div class="col-sm-10">
                                                    <input type="text" class="form-control" id="description"
                                                           name="description"
                                                           value="${requestScope.product.getDescription()}">
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-sm-2 col-form-label">Create At</label>
                                                <div class="col-sm-10">
                                                    <input type="date" id="createAt" class="form-control"
                                                           name="createAt" value="<fmt:formatDate pattern = "yyyy-MM-dd"
                                            value = "${requestScope.product.getCreateAt()}" />">
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-sm-2 col-form-label">Category</label>
                                                <div class="col-sm-10">
                                                    <select class="form-select custom-select" aria-label="Default select example"
                                                            name="idCategory" id="idCategory">
                                                        <c:forEach var="pCategory" items="${requestScope.categories}">
                                                            <c:choose>
                                                                <c:when test="${requestScope.product.getCategoryId() == pCategory.getId()}">
                                                                    <option value="${pCategory.getId()}" selected><c:out
                                                                            value="${pCategory.getName()}"></c:out></option>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <option value="${pCategory.getId()}"><c:out
                                                                            value="${pCategory.getName()}"></c:out></option>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <div class="offset-4">
                                                    <button type="submit" class="btn btn-primary"
                                                            style="padding: 10px 40px">Save
                                                    </button>
                                                    <button class="btn btn-primary"
                                                            style="padding: 10px 40px"><a href="/customer" style="text-decoration: none; color: white">Cancel</a>
                                                    </button>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>

                            </div>
                            <!-- end row -->

                        </div> <!-- end card-box -->
                    </div><!-- end col -->
                </div>
                <!-- end row -->
            </div> <!-- container -->

        </div> <!-- content -->

        <!-- Footer Start -->
        <jsp:include page="/WEB-INF/dashboard/layout/footer.jsp"></jsp:include>
        <!-- end Footer -->

    </div>

</div>
<!-- END wrapper -->

<!-- Vendor js -->
<script src="/dashboard/assets\js\vendor.min.js"></script>
<!-- Tost-->
<script src="/dashboard/assets\libs\jquery-toast\jquery.toast.min.js"></script>

<!-- toastr init js-->
<script src="/dashboard/assets\js\pages\toastr.init.js"></script>

<!-- App js -->
<script src="/dashboard/assets\js\app.min.js"></script>
<script>
    let imgInput = document.getElementById("imgInput");
    let idImage = document.getElementById("idImage");
    imgInput.onchange = evt => {
        const [file] = imgInput.files
        if (file) {
            idImage.src = URL.createObjectURL(file)
        }
    }
    window.onload = () => {
        <% String s = ((Product) request.getAttribute("product")).getImg();
            System.out.println(s);
        %>
        let fileName = "<%= s %>";
        console.log(typeof fileName)

        if (fileName != 'null') {
            console.log("File name: " + fileName)
            let url = "http://localhost:8080/images/" + fileName;
            loadURLToInputFiled(url, fileName);
        }


        // document.getElementById("idImage").src = url;
    }

    function loadURLToInputFiled(url, fileName) {
        getImgURL(url, (imgBlob) => {
            // Load img blob to input
            // WIP: UTF8 character error
            let file = new File([imgBlob], fileName, {type: "image/jpeg", lastModified: new Date().getTime()}, 'utf-8');
            let container = new DataTransfer();
            container.items.add(file);
            document.querySelector('#imgInput').files = container.files;

        })
    }

    // xmlHTTP return blob respond
    function getImgURL(url, callback) {
        var xhr = new XMLHttpRequest();
        xhr.onload = function () {
            callback(xhr.response);
        };
        xhr.open('GET', url);
        xhr.responseType = 'blob';
        xhr.send();
    }
</script>
</body>
</html>