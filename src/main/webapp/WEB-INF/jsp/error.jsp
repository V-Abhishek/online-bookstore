<%-- 
    Document   : error
    Created on : Feb 21, 2020, 7:05:28 PM
    Author     : divinity
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/error.css">
        <title>Error</title>
    </head>
    <body>
        <div>
            <h1>We're sorry, but something went wrong.</h1>
            <c:choose>
                <c:when test="${empty message}">
                    <h2>Please check your inputs again.</h2>
                </c:when>
                <c:otherwise>
                    <h2>${message}</h2>
                </c:otherwise>
            </c:choose>
            <a class="home" href="${pageContext.request.contextPath}">Go back to Home</a>
        </div>
    </body>
</html>
