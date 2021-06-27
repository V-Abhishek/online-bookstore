<%-- 
    Document   : register
    Created on : May 27, 2020, 5:40:13 p.m.
    Author     : abhishek
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>User Registration</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="css/form.css">
    </head>
    <body>
        <div class="header">
            <h1>Welcome, register with us</h1>
            <h6>You can both purchase and sells books with us</h6>
        </div>
        <div class="content">
            <form id="form_login" action="index.htm" method="post" onsubmit="return validateForm();">
                <label>First Name*</label>
                <input name="firstname" placeholder="Enter first name" required="" type="text" pattern="[a-zA-Z]+([\s][a-zA-Z]+)*" title="Alphabets only"><br>
                <label>Last Name*</label>
                <input name="lastname" placeholder="Enter last name" required="" type="text" pattern="[a-zA-Z]+([\s][a-zA-Z]+)*" title="Alphabets only"><br>
                <label>Username*</label>
                <input name="email" placeholder="Enter email address" pattern="[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{1,63}$" required="" type="email" title="xxx@xxx.xxx"><br>
                <label>Password*</label>
                <input id="field_pwd1" placeholder="Enter password for your account" title="Weak password" type="password" required pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*]).{8,16}$" name="pwd1">
                <small>(Password must have at least 8 characters and </small><small>include UPPER,lowercase,numbers and special character.)</small><br>
                <label>Confirm Password*</label>
                <input id="field_pwd2" placeholder="Confirm password for your account" title="Please enter the same Password as above." type="password" required pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*]).{8,16}$" name="pwd2"><br>
                <input name="action" value="registerWithBoth" type="hidden">
                <p id="submission"><input type="submit" value="Register"></p>
            </form>
            <form action="index.htm">
                <p id="submission"><input type="submit" value="Home"></p>
            </form>
        </div>
    </body>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/validate-form.js"></script>
    <script type="text/javascript" src="js/validate-user.js"></script>
</html>

