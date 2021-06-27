<%-- 
    Document   : customer-success
    Created on : Apr 15, 2020, 5:14:15 PM
    Author     : Abhishek
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/form.css">
        <link rel="stylesheet" href="css/error.css">
        <title>Customer Thank You</title>
    </head>
    <body>
        <div class="header">
            <h3>${message}</h3>
            <a class="home" href="${pageContext.request.contextPath}/useraction.htm">Go back to Home</a>
        </div>
    </body>
</html>