<%-- 
    Document   : login-customer
    Created on : 3-Jun-2020, 1:38:56 AM
    Author     : abhishek
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Home</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="css/form.css">
    </head>
    <body>
        <div class="header">
            <h1>Welcome to Online Book Store</h1>
            <h3>Customer Login</h3>
        </div>
        <div class="content">
            <form id="form_login" action="login.htm" method="post"">
                <label>Username*</label>
                <input name="email" placeholder="Enter Email ID" required="" type="email"><br>
                <label>Password*</label>
                <input name="psw" placeholder="Enter Password" required="" type="password"><br>
                <input type="hidden" value="customerLogin" name="action"/>
                <p id="submission"><input type="submit" value="Login"></p>
            </form>
        </div>
<!--        <div class="content">
            <form id="form_login" action="login.htm" method="post">
                <input type="hidden" value="customerForgot" name="action"/>
                <p id="submission"><input type="submit" value="Forgot Password"></p>
            </form>
        </div>-->
    </body>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</html>