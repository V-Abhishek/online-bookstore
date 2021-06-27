<%-- 
    Document   : book-view-all
    Created on : 3-Jun-2020, 6:14:16 PM
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
                background-color: #46BBD6;
                color: white;
                padding: 5px 5px;
                text-align: center;
                text-decoration: none;
                display: inline-block;
                margin: 2px;
            }

            a:hover, a:active {
                background-color: #58C9E3;
            }
            input[type="number"] {
                width: 90px;
                height: 20px;
            }
        </style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Results</title>
    </head>
    <body>
        <h1 align="center">ALL BOOKS</h1>
        <c:choose>
            <c:when test="${empty requestList}">
                <h2 align="center">No Books available.</h2>
            </c:when>
            <c:otherwise>
                <table id="customers">
                    <tr>
                        <th>ISBN</th>
                        <th>Title</th>
                        <th>Authors</th>
                        <th>Published Date</th>
                        <th>Quantity Available</th>
                        <th>Vendor</th>
                        <th>Price</th>
                        <th>Action</th>
                    </tr>
                    <c:forEach var="book" items="${requestList}">
                        <c:if test="${book.quantity>0}">
                            <tr>
                                <td><c:out value="${book.isbn}"/></td>
                                <td><c:out value="${book.title}"/></td>
                                <td><c:out value="${book.authors}"/></td>
                                <td><c:out value="${book.publicationDate}"/></td>
                                <td><c:out value="${book.quantity}"/></td>
                                <td><c:out value="${book.seller.firstName}"/></td>
                                <td><c:out value="${book.price}"/></td>
                                <td><a href="cartmanagement.htm?action=decide&id=${book.bookId}">MORE INFO</a>
                            </tr>
                        </c:if>
                    </c:forEach>
                </table>
                <br>
                <form id="form_login" action="cartmanagement.htm" method="post">
                    <input type="hidden" name="action" value="myCart">
                    <p id="submission"><input type="submit" value="VIEW My Cart"></p>
                </form>
            </c:otherwise>
        </c:choose>
        <form action="useraction.htm">
            <p id="submission"><input type="submit" value="Home"></p>
        </form>
    </body>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</html>