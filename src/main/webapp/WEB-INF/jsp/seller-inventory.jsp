<%-- 
    Document   : seller-inventory
    Created on : 1-Jun-2020, 7:00:16 PM
    Author     : abhishek
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
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
    </style>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inventory</title>
    </head>
    <body>
        <h1 align="center">Inventory</h1>
        <c:choose>
            <c:when test="${empty requestList}">
                <h2 align="center">Empty Inventory</h2>
            </c:when>
            <c:otherwise>
                <form method="post" name="form">
                    <table id="customers">
                        <thead>
                            <tr>
                                <th>ISBN</th>
                                <th>Title</th>
                                <th>Authors</th>
                                <th>Publication Date</th>
                                <th>Quantity</th>
                                <th>Price</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${requestList}" var = "book">
                                <tr>
                                    <td><c:out value="${book.isbn}"/></td>
                                    <td><c:out value="${book.title}"/></td>
                                    <td><c:out value="${book.authors}"/></td>
                                    <td><c:out value="${book.publicationDate}"/></td>
                                    <td><c:out value="${book.quantity}"/></td>
                                    <td><c:out value="${book.price}"/></td>
                                    <td><a href="sellerinventory.htm?action=update&id=${book.bookId}">Update</a>
                                        <input type="button" name="delete" value="Delete" style="background-color:red;font-weight:bold;color:#ffffff;" onclick="deleteRecord(${book.bookId});" >
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </form>      
            </c:otherwise>
        </c:choose>
        <form action="sellermanagement.htm">
            <p id="submission"><input type="submit" value="Home"></p>
        </form>
    </body>
    <script language="javascript">
        function deleteRecord(id) {
            var del = confirm('Do you want to delete the book?');
            if (del) {
                var f = document.form;
                f.method = "post";
                f.action = 'sellerinventory.htm?action=delete&id=' + id;
                f.submit();
            } else {

            }
        }
    </script>
</html>

