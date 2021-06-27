<%-- 
    Document   : home
    Created on : May 27, 2020, 11:51:32 p.m.
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
        </div>
        <div class="content">
            <form action="login.htm" method="POST">
                <input type="hidden" value="signCustomer" name="action"/>
                <p id="submission"><input type="submit" value="Customer Login"></p>
            </form>
            <form action="login.htm" method="POST">
                <input type="hidden" value="signVendor" name="action"/>
                <p id="submission"><input type="submit" value="Vendor Login"></p>
            </form>
            <form action="index.htm" method="POST">
                <input type="hidden" value="register" name="action"/>
                <p id="submission"><input type="submit" value="Register"></p>
            </form>
        </div>
    </body>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</html>