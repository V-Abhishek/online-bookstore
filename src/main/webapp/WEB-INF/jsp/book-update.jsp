<%-- 
    Document   : update-book
    Created on : 2-Jun-2020, 10:54:15 AM
    Author     : abhishek
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Book Registration</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="css/book-register.css">
        <link rel="stylesheet" type="text/css" href="css/book-images.css">
    </head>
    <body>
        <div class="header">
            <h1>Update Book</h1>
        </div>
        <div class="content">
            <c:choose>
                <c:when test="${empty book}">
                    <h2 align="center">Something Went wrong!!</h2>
                </c:when>
                <c:otherwise>
                    <form action="sellerinventory.htm" method="post">
                        <label>ISBN*</label>
                        <input name="isbn" placeholder="Enter ISBN" required type="text" pattern="^(97(8|9))?\d{9}(\d|X)$" title="Numbers only" value="${book.isbn}"><br>
                        <label>Title*</label>
                        <input name="title" placeholder="Enter title" required type="text" pattern="[a-zA-Z]+([\s][a-zA-Z]+)*" title="Alphabets only" value="${book.title}"><br>
                        <label>Authors*</label>
                        <input name="authors" placeholder="Enter authors" required type="text" pattern="[a-zA-Z\s+]+(,[a-zA-Z\s+]+)*" title="Invalid format" value="${book.authors}"><br>
                        <label>Price*</label>
                        <input name="price" placeholder="Enter price" required type="number" min="0.01" max="9999.99" step="0.01" title="Whole digits or Decimal numbers only" value="${book.price}">
                        <small>(In dollars)(Format: xxxx or xxx.x)</small><br>
                        <label>Quantity</label>
                        <input name="quanitity" required type="number" min="0" max="999" value="${book.quantity}"><br>
                        <label>Publishing date*</label>
                        <input type="date" id ="datefield" name="publishDate" max="2020-06-06" required value="${book.publicationDate}"><br>
                        <input name="id" value="${book.bookId}" type="hidden">
                        <input name="action" value="updateBook" type="hidden">
                        <p id="submission"><input type="submit" value="Update Book"></p>
                    </form>

                    <form action="book.htm" method="post" enctype="multipart/form-data">
                        <label>Upload Image*</label>
                        <input type="file" name="images" accept="image/*" required>
                        <input name="bookId" value="${book.bookId}" type="hidden">
                        <input name="isbn" value="${book.isbn}" type="hidden">
                        <input name="action" value="addMoreImages" type="hidden">
                        <p id="submission"><input type="submit" value="Add Image"></p>
                    </form>

                    <div class="row">
                        <c:forEach items="${requestScope.imagesMap}" var="entry">
                            <div class="column">
                                <img src="<c:out value="${entry.value}"/>" alt="book" style="width:100%">
                                <center><a href="sellerinventory.htm?action=deleteImage&bookId=${book.bookId}&imageKey=<c:out value="${entry.key}"/>">Delete</a></center>
                            </div>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </body>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/image-formation.js"></script>
    <script>
        var today = new Date();
        var dd = today.getDate();
        var mm = today.getMonth() + 1;
        var yyyy = today.getFullYear();
        if (dd < 10) {
            dd = '0' + dd
        }
        if (mm < 10) {
            mm = '0' + mm
        }

        today = yyyy + '-' + mm + '-' + dd;
        document.getElementById("datefield").setAttribute("max", today);

    </script>
</html>