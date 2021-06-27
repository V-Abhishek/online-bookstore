<%-- 
    Document   : cart-add-book
    Created on : 14-Jun-2020, 9:54:56 PM
    Author     : abhishek
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Add To Cart</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="css/book-register.css">
        <link rel="stylesheet" type="text/css" href="css/book-images.css">
    </head>
    <body>
        <div class="header">
            <h1>ADD BOOK TO CART</h1>
        </div>
        <div class="content">
            <c:choose>
                <c:when test="${empty book}">
                    <h2 align="center">Something Went wrong!!</h2>
                </c:when>
                <c:otherwise>
                    <form action="cartmanagement.htm" method="post">
                        <label>ISBN</label>
                        <input name="isbn" placeholder="Enter ISBN" required type="text" pattern="^(97(8|9))?\d{9}(\d|X)$" title="Numbers only" value="${book.isbn}" disabled><br>
                        <label>Title</label>
                        <input name="title" placeholder="Enter title" required type="text" pattern="[a-zA-Z]+([\s][a-zA-Z]+)*" title="Alphabets only" value="${book.title}" disabled><br>
                        <label>Authors</label>
                        <input name="authors" placeholder="Enter authors" required type="text" pattern="[a-zA-Z\s+]+(,[a-zA-Z\s+]+)*" title="Invalid format" value="${book.authors}" disabled><br>
                        <label>Price</label>
                        <input name="price" placeholder="Enter price" required type="number" min="0.01" max="9999.99" step="0.01" title="Whole digits or Decimal numbers only" value="${book.price}" disabled><br>
                        <label>Publishing date</label>
                        <input type="date" id ="datefield" name="publishDate" max="2020-06-06" required value="${book.publicationDate}" disabled><br>
                        <label>Quantity Available</label>
                        <input name="quantity" required type="text" value="${book.quantity}" disabled><br>
                        <label>Quantity</label>
                        <input name="requiredQuantity" required type="number" min="1" max="${book.quantity}"><br>
                        <input name="id" value="${book.bookId}" type="hidden">
                        <input name="action" value="addToCart" type="hidden">
                        <p id="submission"><input type="submit" value="Add To Cart"></p>
                    </form>
                    <form id="form_login" action="cartmanagement.htm" method="post">
                        <input type="hidden" name="action" value="myCart">
                        <p id="submission"><input type="submit" value="VIEW My Cart"></p>
                    </form>
                    <form id="form_login" action="cartmanagement.htm" method="post">
                        <input type="hidden" name="action" value="viewBooks">
                        <p id="submission"><input type="submit" value="Back"></p>
                    </form>
                    <div class="row">
                        <c:forEach items="${requestScope.imageList}" var="imageurl">
                            <div class="column">
                                <img src="${imageurl}" alt="book" style="width:100%">
                            </div>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </body>
    <script type="text/javascript" src="js/image-formation.js"></script>
</html>
