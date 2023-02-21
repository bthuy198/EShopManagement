<%@ page import="com.example.customers.model.Customer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zxx">
<head>
    <!-- Meta Tag -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name='copyright' content=''>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Title Tag  -->
    <title>Eshop - eCommerce HTML5 Template.</title>
    <jsp:include page="/WEB-INF/frontend/layout/meta-head.jsp"></jsp:include>

</head>
<body class="js">
<c:if test="${requestScope.message == 'delete'}">
    <script>
        window.onload = () => {
            Swal.fire("Deleted!", "Your file has been deleted.", "success")
        }
    </script>
</c:if>
<!-- Header -->
<header class="header shop">
    <jsp:include page="/WEB-INF/frontend/layout/top-bar.jsp"></jsp:include>
    <!-- Header Inner -->
    <div class="header-inner">
        <div class="container">
            <div class="cat-nav-head">
                <div class="row">
                    <div class="offset-3 col-12">
                        <div class="menu-area">
                            <!-- Main Menu -->
                            <nav class="navbar navbar-expand-lg">
                                <div class="navbar-collapse">
                                    <div class="nav-inner">
                                        <ul class="nav main-menu menu navbar-nav">
                                            <li class="active"><a href="/">Home</a></li>
                                            <li><a href="#">Product</a></li>
                                            <li><a href="#">Service</a></li>
                                            <li><a href="#">Shop<i class="ti-angle-down"></i><span
                                                    class="new">New</span></a>
                                                <ul class="dropdown">
                                                    <li><a href="shop-grid.html">Shop Grid</a></li>
                                                    <li><a href="cart.jsp">Cart</a></li>
                                                    <li><a href="checkout.jsp">Checkout</a></li>
                                                </ul>
                                            </li>
                                            <li><a href="#">Pages</a></li>
                                            <li><a href="#">Blog<i class="ti-angle-down"></i></a>
                                                <ul class="dropdown">
                                                    <li><a href="blog-single-sidebar.html">Blog Single Sidebar</a></li>
                                                </ul>
                                            </li>
                                            <li><a href="contact.html">Contact Us</a></li>
                                        </ul>
                                    </div>
                                </div>
                            </nav>
                            <!--/ End Main Menu -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--/ End Header Inner -->
</header>
<!--/ End Header -->

<!-- Breadcrumbs -->
<div class="breadcrumbs">
    <div class="container">
        <div class="row">
            <div class="col-12">
                <div class="bread-inner">
                    <ul class="bread-list">
                        <li><a href="/">Home<i class="ti-arrow-right"></i></a></li>
                        <li class="active"><a href="blog-single.html">Cart</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- End Breadcrumbs -->

<!-- Shopping Cart -->
<div class="shopping-cart section">
    <div class="container">
        <div class="row">
            <div class="col-12">
                <!-- Shopping Summery -->
                <table class="table shopping-summery">
                    <thead>
                    <tr class="main-hading">
                        <th>PRODUCT</th>
                        <th>NAME</th>
                        <th class="text-center">UNIT PRICE</th>
                        <th class="text-center">QUANTITY</th>
                        <th class="text-center">TOTAL</th>
                        <th class="text-center"><i class="ti-trash remove-icon"></i></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.orderDTO.getOrderItemDTOS()}" var="orderItemDTO">
                        <tr>
                            <td class="image" data-title="No"><img src="/images/${orderItemDTO.getProduct().getImg()}"
                                                                   alt="#"></td>
                            <td class="product-des" data-title="Description">
                                <p class="product-name"><a href="#">${orderItemDTO.getProduct().getProductName()}</a>
                                </p>
                                <p class="product-des">${orderItemDTO.getProduct().getDescription()}</p>
                            </td>
                            <td class="price" data-title="Price"><span>$${orderItemDTO.getProduct().getPrice()} </span>
                            </td>
                            <td class="qty " data-title="Qty"><!-- Input Order -->
                                <div style="display: flex; justify-content: center;align-content: center;align-items: center;margin-top: 25px">
                                    <div>
                                        <input min="0" max="20" value="${orderItemDTO.getQuantity()}" type="number"
                                               onchange="handleInputchange(${orderItemDTO.getProduct().getProductId()}, this)">
                                    </div>
                                </div>

                                <div class="input-group">
                                        <%--										<div class="button minus">--%>
                                        <%--											<button type="button" class="btn btn-primary btn-number" disabled="disabled" data-type="minus" data-field="quant[1]">--%>
                                        <%--												<i class="ti-minus"></i>--%>
                                        <%--											</button>--%>
                                        <%--										</div>--%>
                                        <%--										<input type="number" name="quant[1]" class="input-number" data-min="1" data-max="100" value="1">--%>
                                        <%--										<div class="button plus">--%>
                                        <%--											<button type="button" class="btn btn-primary btn-number" data-type="plus" data-field="quant[1]">--%>
                                        <%--												<i class="ti-plus"></i>--%>
                                        <%--											</button>--%>
                                        <%--										</div>--%>
                                </div>
                                <!--/ End Input Order -->
                            </td>
                            <td class="total-amount" data-title="Total">
                                <span>$${orderItemDTO.getProduct().getPrice() * orderItemDTO.getQuantity()}</span></td>
                            <td class="action" data-title="Remove"><a onclick="handleClick(${orderItemDTO.getProduct().getProductId()})"><i class="ti-trash remove-icon"></i></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <form action="/cart?action=delete" method="post" id="deleteForm">
                    <input type="hidden" name="idDelete" id="idDelete" value="">
                </form>
                <form id="frmCartEdit" action="/cart?action=edit" method="post">
                    <input id="txtIdProduct" name="idproduct" value="" type="hidden">
                    <input id="txtQuantityProduct" name="quantity" value="" type="hidden">
                </form>
                <!--/ End Shopping Summery -->
            </div>
        </div>
        <div class="row">
            <div class="col-12">
                <!-- Total Amount -->
                <div class="total-amount">
                    <div class="row">
                        <div class="col-lg-8 col-md-5 col-12">
                        </div>
                        <div class="col-lg-4 col-md-7 col-12">
                            <div class="right justify-content-end">
<%--                                <div style="display: flex; justify-content: flex-end">--%>
<%--                                    <div class="button5 " style="width: 50%; margin-bottom: 20px">--%>
<%--                                        <a href="#" class="btn">Update</a>--%>
<%--                                    </div>--%>
<%--                                </div>--%>

                                <ul>
                                    <li>Cart Subtotal<span>$${requestScope.orderDTO.getTotal()}</span></li>
                                    <li>Shipping<span>Free</span></li>
                                    <li class="last">You Pay<span>$${requestScope.orderDTO.getTotal()}</span></li>
                                </ul>
                                <div class="button5">
                                    <a href="/cart?action=checkout" class="btn">Checkout</a>
                                    <a href="/" class="btn">Continue shopping</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!--/ End Total Amount -->
            </div>
        </div>
    </div>
</div>
<!--/ End Shopping Cart -->
<%-- delete product --%>
<script>
    function handleClick(idProduct) {
        document.getElementById("idDelete").value = idProduct;
        Swal.fire({
            title: "Are you sure?",
            text: "You won't be able to revert this!",
            type: "warning",
            showCancelButton: !0,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Yes, delete it!"
        }).then(function (t) {
            console.log(t)
            if(t.value == true){
                document.getElementById("deleteForm").submit();
            }

        })
    }
        function handleInputchange(id, element) {
        console.log(id);
        let value = element.value;
        console.log(value)
        document.getElementById("txtIdProduct").value = id;
        document.getElementById("txtQuantityProduct").value = value;

        document.getElementById("frmCartEdit").submit();
    }

</script>
<script>
    function handleInputchange(id, element) {
        console.log(id);
        let value = element.value;
        console.log(value)
        document.getElementById("txtIdProduct").value = id;
        document.getElementById("txtQuantityProduct").value = value;

        document.getElementById("frmCartEdit").submit();
    }
</script>
<jsp:include page="/WEB-INF/frontend/layout/footer.jsp"></jsp:include>

<jsp:include page="/WEB-INF/frontend/layout/jquery.jsp"></jsp:include>

</body>
</html>