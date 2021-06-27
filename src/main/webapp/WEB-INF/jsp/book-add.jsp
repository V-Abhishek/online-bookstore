<%-- 
    Document   : add-book
    Created on : 1-Jun-2020, 7:33:29 PM
    Author     : abhishek
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Book Registration</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="css/book-register.css">
    </head>
    <body>
        <div class="header">
            <h1>Book Registration</h1>
        </div>
        <div class="content">
            <form action="book.htm" method="post" enctype="multipart/form-data">
                <label>ISBN*</label>
                <input name="isbn" placeholder="Enter ISBN" required type="text" pattern="^(97(8|9))?\d{9}(\d|X)$" title="Numbers only">
                <small>10 digit ISBN</small><br>
                <label>Title*</label>
                <input name="title" placeholder="Enter title" required type="text" pattern="[a-zA-Z]+([\s][a-zA-Z]+)*" title="Alphabets only"><br>
                <label>Authors*</label>
                <input name="authors" placeholder="Enter authors" required type="text" pattern="[a-zA-Z\s+]+(,[a-zA-Z\s+]+)*" title="Invalid format">
                <small>(Format: ABC,ABC CD)</small><br>
                <label>Price*</label>
                <input name="price" placeholder="Enter price" required type="number" min="0.01" max="9999.99" step="0.01" title="Whole digits or Decimal numbers only">
                <small>(In dollars)(Format: xxxx or xxx.x)</small><br>
                <label>Quantity</label>
                <input name="quanitity" required type="number" min="0" max="999"><br>
                <label>Publishing date*</label>
                <input type="date" id ="datefield" name="publishDate" max="2020-06-06" required><br>
                <label>Upload Image*</label>
                <input type="file" name="images" accept="image/*" required>
                <input name="action" value="registerBook" type="hidden">
                <p id="submission"><input type="submit" value="Add Book"></p>
            </form>
            <form action="sellermanagement.htm">
                <p id="submission"><input type="submit" value="Home"></p>
            </form>
        </div>
    </body>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
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

