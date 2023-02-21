<%@ page import="com.example.customers.model.Customer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Minton - Responsive Admin Dashboard Template</title>
    <link href="/dashboard/assets\libs\tablesaw\tablesaw.css" rel="stylesheet" type="text/css">
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

    <jsp:include page="/WEB-INF/dashboard/layout/top-bar.jsp"></jsp:include>
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
                                    <li class="breadcrumb-item">Customer</li>
                                </ol>
                            </div>
                            <h4 class="page-title">Customers List</h4>
                        </div>
                    </div>
                </div>
                <!-- end page title -->

                <div class="row">
                    <div class="col-lg-12">
                        <div class="card-box">
                            <div class="mb-2">
                                <div class="row">
                                    <div class="form-group mr-2 col-3">
                                        <button id="demo-btn-addrow" class="btn btn-primary"><a
                                                href="/customer?action=create"
                                                style="text-decoration: none; color: white"><i
                                                class="mdi mdi-plus-circle mr-2"></i> Add New Customer</a></button>
                                    </div>
                                    <div class="form-group col-9 row justify-content-end">
                                        <div>
                                            <form action="/customer" method="get">
                                                <input type="hidden" name="page" value="${requestScope.currentPage}">
                                                <input type="hidden" name="kw" value="${requestScope.kw}">
                                                <input type="hidden" name="ct" value="${requestScope.ct}">
                                                <select  style="width: 150px; display: inline-block" id="foo-filter-name" class="custom-select custom-select-sm form-control" name="sort.name">
                                                    <option value="name"
                                                            <c:if test="${requestScope.sortName == 'name'}">selected</c:if>
                                                    >Name</option>
                                                    <option value="birthday"
                                                            <c:if test="${requestScope.sortName == 'birthday'}">selected</c:if>
                                                    >Birthday</option>
                                                    <option value="address"
                                                            <c:if test="${requestScope.sortName == 'address'}">selected</c:if>
                                                    >Address</option>
                                                    <option value="idType"
                                                            <c:if test="${requestScope.sortName == 'idType'}">selected</c:if>
                                                    >Type Customer</option>
                                                </select>
                                                <select style="width: 80px; display: inline-block" id="foo-filter-option" class="custom-select custom-select-sm form-control" name="sort.option">
                                                    <option value="asc"
                                                            <c:if test="${requestScope.sortOption == 'asc'}">selected</c:if>
                                                    >ASC</option>
                                                    <option value="desc"
                                                            <c:if test="${requestScope.sortOption == 'desc'}">selected</c:if>
                                                    >DESC</option>
                                                </select>
                                                <button style="width: 100px; display: inline-block" class="btn btn-primary" type="submit">Sort</button>
                                            </form>
                                        </div>

                                    <%--                                    <div class="form-group col-6 row justify-content-end">--%>
                                    <%--                                        <div>--%>
                                    <%--                                            <select id="demo-foo-filter-status" class="custom-select custom-select-sm">--%>
                                    <%--                                                <option value="-1">ALL</option>--%>
                                    <%--                                                <c:forEach items="${requestScope.customerTypes}" var="cType">--%>
                                    <%--                                                    <option value="${cType.getId()}">${cType.getType()}</option>--%>
                                    <%--                                                </c:forEach>--%>
                                    <%--                                            </select>--%>
                                    <%--                                        </div>--%>
                                    <%--                                    </div>--%>
                                </div>
                            </div>

                            <div class="table-responsive text-center">
                                <table class="table mb-0">
                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Image</th>
                                        <th>Name</th>
                                        <th>Birthday</th>
                                        <th>Address</th>
                                        <th>Type</th>
                                        <th>Action</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="c" items="${requestScope.customers}">
                                        <tr>
                                            <th scope="row">${c.getId()}</th>
                                            <td><img src="/images/${c.getImg()}"
                                                     style="width: 70px; height: 70px; border-radius: 50%">
                                            </td>
                                            <td>${c.getName()}</td>
                                            <td><fmt:formatDate pattern="dd-MM-yyyy" value="${c.getBirthday()}"/></td>
                                            <td>${c.getAddress()}</td>
                                            <td>
                                                <c:forEach var="cType" items="${requestScope.customerTypes}">
                                                    <c:if test="${cType.getId() == c.getIdType()}">${cType.getType()}</c:if>
                                                </c:forEach>
                                            </td>
                                            <td>
                                                <a href="/customer?action=edit&id=${c.getId()}"
                                                   style="margin-right: 5px"><i
                                                        class="fa-solid fa-pen-to-square"></i></a>
                                                <a onclick="handleClick(${c.getId()},'${c.getName()}')"
                                                   style="cursor: pointer; color: #7266ba"><i
                                                        class="fa-solid fa-trash"></i></a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>

                                <form action="/customer?action=delete" method="post" id="deleteForm">
                                    <input type="hidden" name="idDelete" id="idDelete" value="">
                                </form>
                            </div>

                            <div class="text-right">
                                <%--                                <ul class="pagination pagination-rounded justify-content-end footable-pagination m-t-10 mb-0">--%>
                                <%--                                    <c:if test="${currentPage != 1}">--%>
                                <%--                                        <li class="footable-page-arrow disabled"><a data-page="prev" href="/customer?kw=${requestScope.kw}&ct=${requestScope.ct}&page=${currentPage - 1}">‹</a>--%>
                                <%--                                        </li>--%>
                                <%--                                    </c:if>--%>
                                <%--                                    <c:forEach begin="1" items="${pages}" end="${pages}" var="i">--%>
                                <%--                                        <c:when test="${currentPage eq i}">--%>
                                <%--                                            <li class="footable-page active"><a data-page="0"--%>
                                <%--                                                                                href="/customer?kw=${requestScope.kw}&ct=${requestScope.ct}&page=${i}">${i}</a>--%>
                                <%--                                            </li>--%>
                                <%--                                        </c:when>--%>
                                <%--                                        <c:otherwise>--%>
                                <%--                                            <li class="footable-page"><a data-page="1" href="/customer?kw=${requestScope.kw}&ct=${requestScope.ct}&page=${i}">${i}</a></li>--%>
                                <%--                                        </c:otherwise>--%>
                                <%--                                    </c:forEach>--%>
                                <%--                                    <c:if test="${currentPage lt pages}">--%>
                                <%--                                        <li class="footable-page-arrow"><a data-page="next" href="/customer?kw=${requestScope.kw}&ct=${requestScope.ct}&page=${currentPage + 1}">›</a></li>--%>
                                <%--                                    </c:if>--%>

                                <%--                                </ul>--%>
                            </div>
                            <div class="row justify-content-end">
                                <ul class="pagination pagination-split float-right mb-0">
                                    <c:if test="${currentPage != 1}">
                                        <li class="page-item">
                                            <a href="/customer?kw=${requestScope.kw}&ct=${requestScope.ct}&page=${currentPage-1}&sort.name=${requestScope.sortName}&sort.option=${requestScope.sortOption}"
                                               class="page-link"><i class="fa fa-angle-left"></i></a>
                                        </li>
                                    </c:if>
                                    <c:forEach begin="1" end="${noOfPages}" var="i">
                                        <c:choose>
                                            <c:when test="${currentPage eq i}">
                                                <li class="page-item active">
                                                    <a href="/customer?kw=${requestScope.kw}&ct=${requestScope.ct}&page=${i}&sort.name=${requestScope.sortName}&sort.option=${requestScope.sortOption}"
                                                       class="page-link">${i}</a>
                                                </li>
                                            </c:when>
                                            <c:otherwise>
                                                <li class="page-item">
                                                    <a href="/customer?kw=${requestScope.kw}&ct=${requestScope.ct}&page=${i}&sort.name=${requestScope.sortName}&sort.option=${requestScope.sortOption}"
                                                       class="page-link">${i}</a>
                                                </li>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                    <c:if test="${currentPage lt noOfPages}">
                                        <li class="page-item">
                                            <a href="/customers?kw=${requestScope.kw}&ct=${requestScope.ct}&page=${currentPage+1}&sort.name=${requestScope.sortName}&sort.option=${requestScope.sortOption}"
                                               class="page-link"><i class="fa fa-angle-right"></i></a>
                                        </li>
                                    </c:if>
                                </ul>
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

    t("#sa-params").click(function () {
        Swal.fire({
            title: "Are you sure?",
            text: "You won't be able to revert this!",
            type: "warning",
            showCancelButton: !0,
            confirmButtonText: "Yes, delete it!",
            cancelButtonText: "No, cancel!",
            confirmButtonClass: "btn btn-success mt-2",
            cancelButtonClass: "btn btn-danger ml-2 mt-2",
            buttonsStyling: !1
        }).then(function (t) {
            t.value ? Swal.fire({

                title: "Deleted!",
                text: "Your file has been deleted.",
                type: "success"
            }) : t.dismiss === Swal.DismissReason.cancel && Swal.fire({
                title: "Cancelled",
                text: "Your imaginary file is safe :)",
                type: "error"
            })
        })
    })

</script>
</body>
</html>