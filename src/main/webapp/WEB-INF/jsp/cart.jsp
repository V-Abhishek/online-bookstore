<%-- 
    Document   : cart
    Created on : 4-Jun-2020, 1:10:18 AM
    Author     : abhishek
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <style>
            body {
                background-color: #C4CBC8;
            }

            #customers {
                font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
                border-collapse: collapse;
                width: 100%;
                align-content: center;
            }

            #customers td, #customers th {
                border: 1px solid #ddd;
                padding: 8px;
            }

            #customers tr:nth-child(even){background-color: #f2f2f2;}

            #customers tr:hover {background-color: #ddd;}

            #customers th {
                padding-top: 12px;
                padding-bottom: 12px;
                text-align: left;
                background-color: #4CAF50;
                color: white;
            }

            #submission {
                display: block;
                position: relative;
                left: 50%;
                top: 10px;
            }

            input[type="submit"] {
                color: #fff !important;
                text-transform: uppercase;
                text-decoration: none;
                background: #4CAF50;
                padding: 20px 20px;
                border-radius: 50px;
                border: none;
                transition: all 0.4s ease 0s;
            }

            input[type="submit"]:hover {
                text-shadow: 0px 0px 6px rgba(255, 255, 255, 1);
                -webkit-box-shadow: 0px 5px 40px -10px rgba(0,0,0,0.57);
                -moz-box-shadow: 0px 5px 40px -10px rgba(0,0,0,0.57);
                transition: all 0.4s ease 0s;
            }

            a:link {
                background-color: #9aa19e;
                color: white;
                padding: 5px 5px;
                text-align: center;
                text-decoration: none;
                display: inline-block;
                margin: 2px;
                width: 90px;
                height: 30px;
            }

            a:hover, a:active {
                background-color: #c2eddb;
            }
            input[type="number"] {
                width: 90px;
                height: 20px;
            }
        </style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart</title>
    </head>
    <body>
        <h1 align="center">Your Cart</h1>
        <h3>${requestScope.changedCartMsg}</h3>
        <c:choose>
            <c:when test="${empty requestList}">
                <h2 align="center">You have an empty cart</h2>
            </c:when>
            <c:otherwise>
                <form action="" method="">
                    <table id="customers">
                        <tr>
                            <th>Title</th>
                            <th>Quantity</th>
                            <th>Price</th>
                            <th>Vendor</th>
                        </tr>
                        <c:forEach var="cartBook" items="${requestList}">
                            <tr id="${cartBook.bookId}">
                                <td><c:out value="${cartBook.title}" /></td>
                                <td><c:out value="${cartBook.quantity}" /></td>
                                <td><c:out value="${cartBook.price}" /></td>
                                <td><c:out value="${cartBook.vendorMail}" /></td>
                            </tr>
                        </c:forEach>
                    </table>
                </form>
            </c:otherwise>
        </c:choose>
        <form action="useraction.htm">
            <p id="submission"><input type="submit" value="Home"></p>
        </form>
        <form id="form_login" action="cartmanagement.htm" method="post">
            <input type="hidden" name="action" value="viewBooks">
            <p id="submission"><input type="submit" value="View Books"></p>
        </form>
    </body>
</html>