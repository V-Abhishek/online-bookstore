<%-- 
    Document   : customer-thank-you
    Created on : Apr 2, 2020, 6:51:07 PM
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
            <h3>Thank you ${name}. You have successfully registered</h3>
            <a class="home" href="${pageContext.request.contextPath}">Go back to Home</a>
        </div>
    </body>
</html>
