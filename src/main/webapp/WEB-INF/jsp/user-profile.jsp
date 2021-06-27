<%-- 
    Document   : user-profile
    Created on : May 28, 2020, 3:10:06 PM
    Author     : Abhishek
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
            <h1>My Profile</h1>
        </div>
        <div class="content">
            <form id="form_login" action="index.htm" method="post">
                <label>First Name</label>
                <input name="firstName" value="${sessionScope.user.firstName}" placeholder="Enter your first name" required="" type="text" pattern="[a-zA-Z]+([\s][a-zA-Z]+)*" title="Alphabets only" readonly><br>
                <label>Last Name</label>
                <input name="lastName" value="${sessionScope.user.lastName}" placeholder="Enter your last name" required="" type="text" pattern="[a-zA-Z]+([\s][a-zA-Z]+)*" title="Alphabets only" readonly><br>
                <label>Email Id</label>
                <input name="email" value="${sessionScope.user.email}" readonly placeholder="Enter email address" pattern="[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{1,63}$" required="" type="email" title="xxx@xxx.xxx"><br>
            </form>
            <form action="useraction.htm">
                <p id="submission"><input type="submit" value="Home"></p>
            </form>
        </div>
    </body>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</html>
