<%-- 
    Document   : seller-update
    Created on : 3-Jun-2020, 12:09:41 AM
    Author     : abhishek
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Customer Sign Up</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="css/form.css">
    </head>
    <body>
        <div class="header">
            <h1>Update Profile</h1>
        </div>
        <c:choose>
            <c:when test="${empty sessionScope.seller}">
                <h2 align="center">Something Went wrong!!</h2>
            </c:when>
            <c:otherwise>
                <div class="content">
                    <form id="form_login" action="sellermanagement.htm" method="post">
                        <label>First Name*</label>
                        <input name="firstName" value="${sessionScope.seller.firstName}" placeholder="Enter your first name" required="" type="text" pattern="[a-zA-Z]+([\s][a-zA-Z]+)*" title="Alphabets only"><br>
                        <label>Last Name*</label>
                        <input name="lastName" value="${sessionScope.seller.lastName}" placeholder="Enter your last name" required="" type="text" pattern="[a-zA-Z]+([\s][a-zA-Z]+)*" title="Alphabets only"><br>
                        <label>Email Id*</label>
                        <input name="email" value="${sessionScope.seller.email}" readonly placeholder="Enter email address" pattern="[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{1,63}$" required="" type="email" title="xxx@xxx.xxx"><br>
                        <input name="action" value="updateProfile" type="hidden">
                        <p id="submission"><input type="submit" value="Update"></p>
                    </form>
                </div>
            </c:otherwise>
        </c:choose>
        <form action="sellermanagement.htm">
            <p id="submission"><input type="submit" value="Home"></p>
        </form>
    </body>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</html>