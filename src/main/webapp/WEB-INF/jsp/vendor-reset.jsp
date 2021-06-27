<%-- 
    Document   : vendor-reset
    Created on : 22-Jul-2020, 5:34:12 PM
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
            <h1>Vendor Reset Password</h1>
        </div>

        <div class="content">
            <form id="form_login" action="login.htm" method="post">
                <label>Email ID*</label>
                <input name="email" placeholder="Enter Email ID" required="" type="email">
                <br>
                <input name="action" value="VSendLink" type="hidden">
                <p id="submission"><input type="submit" value="Send Link"></p>
            </form>
        </div>

        <a href="${pageContext.request.contextPath}">Go back to Home</a>
    </body>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</html>
