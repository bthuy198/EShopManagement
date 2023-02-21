<%@ page import="com.example.customers.model.Customer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- Topbar Start -->
<div class="navbar-custom">
    <div class="float-right">
        <form >
            <div style="display: flex; align-items: center; height: 70px">
                <div style="width: 100px;margin-right: 10px" >
                    <select id="demo-foo-filter-status" class=" custom-select custom-select-sm form-control" name="ct"
                            style="height: 38px;background-color: rgba(255, 255, 255, 0.1);border-radius: 30px; color: #98a6ad;border: none">
                        <option value="-1">ALL</option>
                        <c:forEach items="${requestScope.customerTypes}" var="cType">
                            <option value="${cType.getId()}">${cType.getType()}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="">
                    <div class="input-group">
                        <input type="text" class="form-control" placeholder="Search customer..." id="kw" name="kw" style="height: 38px;background-color: rgba(255, 255, 255, 0.1);border-radius: 30px; color: white; border: none">
                        <span style="position: absolute;left:150px"><button class="btn" type="submit"><i class="fe-search"></i></button></span>
<%--                        <div class="input-group-append">--%>
<%--                            <button class="btn" type="submit">--%>
<%--                                <i class="fe-search"></i>--%>
<%--                            </button>--%>
<%--                        </div>--%>
                    </div>
                </div>
            </div>
        </form>
    </div>

    <!-- LOGO -->
    <div class="logo-box">
        <a href="index.html" class="logo text-center">
                        <span class="logo-lg">
                            <img src="/dashboard/assets\images\logo-light.png" alt="" height="20">
                            <!-- <span class="logo-lg-text-light">Xeria</span> -->
                        </span>
            <span class="logo-sm">
                            <!-- <span class="logo-sm-text-dark">X</span> -->
                            <img src="/dashboard/assets\images\logo-sm.png" alt="" height="24">
                        </span>
        </a>
    </div>
</div>
<!-- end Topbar -->
