<%-- 
    Document   : user-home
    Created on : May 27, 2020, 11:18:03 p.m.
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
        <%
            response.setHeader("pragma", "no-cache");
            response.setHeader("Cache-control", "no-cache, no-store, must-revalidate");
            response.setHeader("Expires", "0");
        %>
        <div class="header">
            <h1>Welcome</h1>
            <small>Shop Books</small>
        </div>
        <div class="content">
            <form id="form_login" action="cartmanagement.htm" method="post">
                <input type="hidden" name="action" value="viewBooks">
                <p id="submission"><input type="submit" value="VIEW Books"></p>
            </form>
            <form id="form_login" action="cartmanagement.htm" method="post">
                <input type="hidden" name="action" value="myCart">
                <p id="submission"><input type="submit" value="VIEW My Cart"></p>
            </form>
            <form id="form_login" action="useraction.htm" method="post">
                <input type="hidden" name="action" value="viewProfile">
                <p id="submission"><input type="submit" value="VIEW MY PROFILE"></p>
            </form>
<!--            <form id="form_login" action="useraction.htm" method="post">
                <input type="hidden" name="action" value="changePassword">
                <p id="submission"><input type="submit" value="UPDATE MY PASSWORD"></p>
            </form>-->
            <form id="form_login" action="useraction.htm" method="post">
                <input type="hidden" name="action" value="changeProfile">
                <p id="submission"><input type="submit" value="UPDATE MY PROFILE"></p>
            </form>
            <form action="index.htm" method="post">
                <input type="hidden" name="action" value="logout"/>
                <p id="submission"><input type="submit" value="LogOut"></p>
            </form>
        </div>
    </body>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</html>