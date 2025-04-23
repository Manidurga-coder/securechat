<%--
  Created by IntelliJ IDEA.
  User: rajas
  Date: 4/18/2025
  Time: 3:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.project.ClientSocket" %>
<%@ page import="org.project.util.ChatUtil" %>
<%
    ArrayList<String> messages = (ArrayList<String>) session.getAttribute("messages");
    if (messages == null) {
        messages = new ArrayList<>();
        session.setAttribute("messages", messages);
    }

    String user = (String) session.getAttribute("username");
    if (user == null) {
        user = request.getParameter("username");
        if (user != null) {
            session.setAttribute("username", user);
        }
        else {
            response.sendError(404);
        }
    }



    String newMessage = request.getParameter("message");
    ClientSocket clientSocket = new ClientSocket();
    clientSocket.connectToServer();
    if (newMessage != null && !newMessage.trim().isEmpty()) {
        clientSocket.sendMessage(session.getAttribute("roomId") + ChatUtil.ROOM + newMessage + ChatUtil.USER +user);
        messages.add(user + ": " + newMessage);
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Chat</title>
    <style>
        body { font-family: Arial; margin: 20px; }
        .chat-box { width: 400px; height: 300px; border: 1px solid #ccc; padding: 10px; overflow-y: scroll; }
        .chat-input { margin-top: 10px; }
    </style>
</head>
<body>

<h2>Welcome to the Chat Page</h2>

<h3>Chat Id: <%= session.getAttribute("roomId")%></h3>


<div class="chat-box" id="chatBox">
    <% for (String msg : messages) { %>
    <div><%= msg %></div>
    <% } %>
</div>

<form method="post" class="chat-input">
    <input type="text" name="message" placeholder="Type a message..." size="30" autofocus required />
    <button type="submit">Send</button>
</form>

<script>
    // Auto-scroll chat box to bottom
    var chatBox = document.getElementById('chatBox');
    chatBox.scrollTop = chatBox.scrollHeight;
</script>

<script>
    function fetchMessages() {
        fetch('getMessages')
            .then(response => response.json())
            .then(messages => {
                const chatBox = document.getElementById('chatBox');
                chatBox.innerHTML = '';
                messages.forEach(msg => {
                    const div = document.createElement('div');
                    div.textContent = msg;
                    chatBox.appendChild(div);
                });
                chatBox.scrollTop = chatBox.scrollHeight;
            })
            .catch(error => console.error("Error fetching messages:", error));
    }

    setInterval(fetchMessages, 2000); // poll every 2 seconds
    fetchMessages(); // fetch immediately
</script>



</body>
</html>

