<%--
  Created by IntelliJ IDEA.
  User: josephkwagilwa
  Date: 12/02/2022
  Time: 15:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Admin Learners</title>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.4/css/jquery.dataTables.min.css">

    <script src="https://code.jquery.com/jquery-3.5.1.js" type="text/javascript"></script>

    <script src="https://cdn.datatables.net/1.11.4/js/jquery.dataTables.min.js" type="text/javascript"></script>

    <script>
        $(document).ready(function () {
            $('#datatable').DataTable();
        });
    </script>

    <!-- Bootstrap core CSS -->
    <link href="${pageContext.request.contextPath}/resources/assets/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        .bd-placeholder-img {
            font-size: 1.125rem;
            text-anchor: middle;
            -webkit-user-select: none;
            -moz-user-select: none;
            user-select: none;
        }

        @media (min-width: 768px) {
            .bd-placeholder-img-lg {
                font-size: 3.5rem;
            }
        }
    </style>

    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/resources/dashboard.css" rel="stylesheet">
</head>
<body>

<header class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0 shadow">
    <a class="navbar-brand col-md-3 col-lg-2 me-0 px-3" href="#">Admin Learners Academy</a>
    <button class="navbar-toggler position-absolute d-md-none collapsed" type="button" data-bs-toggle="collapse"
            data-bs-target="#sidebarMenu" aria-controls="sidebarMenu" aria-expanded="false"
            aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="navbar-nav">
        <div class="nav-item text-nowrap">
            <a class="nav-link px-3" href="${pageContext.request.contextPath}/logout">Sign out</a>
        </div>
    </div>
</header>

<div class="container-fluid">
    <div class="row">
        <nav id="sidebarMenu" class="col-md-3 col-lg-2 d-md-block bg-light sidebar collapse">
            <div class="position-sticky pt-3">

                <ul class="nav flex-column mb-2">

                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/">
                            <span data-feather="home"></span>
                            Dashboard
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/subject/">
                            <span data-feather="file-plus"></span>
                            Add Subject
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/teacher/">
                            <span data-feather="user-plus"></span>
                            Add Teacher
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" href="">
                            <span data-feather="file-plus"></span>
                            Add Class
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/student/">
                            <span data-feather="user-plus"></span>
                            Add Student
                        </a>
                    </li>

                </ul>
            </div>
        </nav>

        <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
            <br>
            <ul class="nav nav-tabs" id="myTab">
                <li class="nav-item">
                    <a href="#class" class="nav-link active" data-bs-toggle="tab">Add Class</a>
                </li>
                <li class="nav-item">
                    <a href="#assign" class="nav-link" data-bs-toggle="tab">Assign a Teacher and Subject to a Class</a>
                </li>

            </ul>

            <div class="tab-content">
                <div class="tab-pane fade show active" id="class">
                    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">

                        <div class="col-md-6 grid-margin stretch-card">

                            <div class="card">
                                <div class="card-body">
                                    <h6 class="card-title">Add Class</h6>
                                    <form:form modelAttribute="class" action="addClass" cssClass="needs-validation"
                                               novalidate="true">

                                        <div>
                                            <label for="cname" class="form-label">Name</label>
                                            <form:input type="text" cssClass="form-control" id="cname" path="name"
                                                        required="true"/>
                                            <form:errors cssClass="invalid-feedback" path="name"/>
                                        </div>

                                        <br>
                                        <input type="submit" value="submit" class="btn btn-primary">
                                    </form:form>
                                </div>

                            </div>
                        </div>

                    </div>
                </div>

                <div class="tab-pane fade show" id="assign">

                    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">

                        <div class="col-md-6 grid-margin stretch-card">

                            <div class="card">
                                <div class="card-body">
                                    <h6 class="card-title">Assign</h6>
                                    <form action="assign" method="post">
                                        <label for="cname" class="form-label">Class</label>
                                        <select id="cname" class="form-select" name="cid">
                                            <c:forEach items="${classes}" var="mClass">
                                                <option value="${mClass.getId()}">${mClass.getName()}</option>
                                            </c:forEach>
                                        </select>
                                        <br>
                                        <label for="sname" class="form-label">Subject</label>
                                        <select id="sname" class="form-select" name="sid">
                                            <c:forEach items="${subjects}" var="subject">
                                                <option value="${subject.getId()}">${subject.getName()}</option>
                                            </c:forEach>
                                        </select>
                                        <br>
                                        <label for="tname" class="form-label">Teacher</label>
                                        <select id="tname" class="form-select" name="tid">
                                            <c:forEach items="${teachers}" var="teacher">
                                                <option value="${teacher.getId()}">${teacher.getName()}</option>
                                            </c:forEach>
                                        </select>
                                        <br>
                                        <input type="submit" value="Assign" class="btn btn-primary">
                                    </form>
                                </div>
                            </div>
                        </div>

                    </div>

                </div>
            </div>


        </main>

        <div class="col-md-12 ms-sm-auto col-lg-10 px-md-4">
            <div class="col-md-12 grid-margin stretch-card">
                <div class="card">
                    <div class="card-body">
                        <h6 class="card-title">Classes Table</h6>

                        <table id="datatable" class="display" style="width:100%">
                            <thead>
                            <tr>
                                <th>Class Name</th>
                                <th>Subject Name</th>
                                <th>Teacher Nmae</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>

                            <c:forEach items="${classes}" var="mClass">
                                <tr>
                                    <td><c:out value="${mClass.getName()}"/></td>
                                    <td><c:out value="${mClass.getSubject().getName()}"/></td>
                                    <td><c:out value="${mClass.getTeacher().getName()}"/></td>
                                    <td><a href="${pageContext.request.contextPath}/class/delete?id=${mClass.getId()}">
                                        <i data-feather="trash-2"></i> </a></td>
                                </tr>
                            </c:forEach>

                            </tbody>
                            <tfoot>
                            <tr>
                                <th>Class Name</th>
                                <th>Subject Name</th>
                                <th>Teacher Nmae</th>
                                <th></th>
                            </tr>
                            </tfoot>
                        </table>

                    </div>
                </div>
            </div>
        </div>

    </div>

</div>


<script src="${pageContext.request.contextPath}/resources/assets/dist/js/bootstrap.bundle.min.js"></script>

<script src="https://cdn.jsdelivr.net/npm/feather-icons@4.28.0/dist/feather.min.js"
        integrity="sha384-uO3SXW5IuS1ZpFPKugNNWqTZRRglnUJK6UAZ/gxOX80nxEkN9NcGZTftn6RzhGWE"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js@2.9.4/dist/Chart.min.js"
        integrity="sha384-zNy6FEbO50N+Cg5wap8IKA4M/ZnLJgzc6w2NqACZaK0u0FXfOWRRJOnQtpZun8ha"
        crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/resources/dashboard.js"></script>
</body>
</html>
