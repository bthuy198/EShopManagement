<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Minton - Responsive Admin Dashboard Template</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta content="A fully featured admin theme which can be used to build CRM, CMS, etc." name="description">
        <meta content="Coderthemes" name="author">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
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
                                            <li class="breadcrumb-item">Dashboard</li>
                                            <li class="breadcrumb-item">Main Page</li>
                                        </ol>
                                    </div>
                                    <h4 class="page-title">Dashboard</h4>
                                </div>
                            </div>
                        </div>     
                        <!-- end page title --> 
                        <div class="row">
                            <div class="col-xl-6">
                                <div class="card-box" onClick="location.href='/customer'" style="cursor: pointer">
                                    <i class="fa fa-info-circle text-muted float-right" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="More Info"></i>
                                    <h4 class="mt-0 font-16">Manage Customer</h4>
                                    <h2 class="text-primary my-3 text-center">${requestScope.count_customer} </h2>
                                    <p class="mb-auto text-center my-3">customers</p>
                                </div>
                            </div>
                            <div class="col-xl-6">
                                <div class="card-box" onClick="location.href='/product'" style="cursor: pointer">
                                    <i class="fa fa-info-circle text-muted float-right" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="More Info"></i>
                                    <h4 class="mt-0 font-16">Manage Products</h4>
                                    <h2 class="text-primary my-3 text-center">${requestScope.count_product} </h2>
                                    <p class="mb-auto text-center my-3">products</p>
                                </div>
                            </div>
                        </div>
                    </div> <!-- container -->

                </div> <!-- content -->



            </div>
        </div>
        <!-- END wrapper -->

        <jsp:include page="/WEB-INF/dashboard/layout/footer.jsp"></jsp:include>

        <!-- Vendor js -->
        <script src="/dashboard/assets\js\vendor.min.js"></script>

        <!-- App js -->
        <script src="/dashboard/assets\js\app.min.js"></script>
        
    </body>
</html>