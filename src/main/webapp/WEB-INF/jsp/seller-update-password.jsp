<%-- 
    Document   : seller-update-password
    Created on : 3-Jun-2020, 12:13:42 AM
    Author     : abhishek
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Seller</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="css/form.css">
    </head>
    <body>
        <div class="header">
            <h1>Update Password</h1>
        </div>
        <c:choose>
            <c:when test="${empty sessionScope.seller}">
                <h2 align="center">Something Went wrong!!</h2>
            </c:when>
            <c:otherwise>
                <div class="content">
                    <form id="form_login" action="sellermanagement.htm" method="post">
                        <label>Password*</label>
                        <input id="field_pwd2" placeholder="Confirm password for your account" title="Please enter the same Password as above." type="password" required pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*]).{8,16}$" name="pwd2">
                        <small>(Password must have at least 8 characters and </small><small>include UPPER,lowercase,numbers and special character.)</small><br>
                        <br>
                        <input name="action" value="updatePassword" type="hidden">
                        <p id="submission"><input type="submit" value="Change Password"></p>
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