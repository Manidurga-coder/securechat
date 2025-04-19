<%--
  Created by IntelliJ IDEA.
  User: rajas
  Date: 4/18/2025
  Time: 2:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Join Room</title>
</head>
<body>
<h2>Join a Chat Room</h2>
<form action="/joinroom" method="post">
    <label for="username">Your Name:</label><br>
    <input type="text" id="username" name="username" required><br><br>

    <label for="room">Room ID:</label><br>
    <input type="text" id="room" name="room" required><br><br>

    <button type="submit">Join Chat</button>
</form>
</body>
</html>
