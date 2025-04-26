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
<head>
    <title>Chat</title>
    <link rel="stylesheet" type="text/css" href="Chat.css">
    <style>
        body { font-family: Arial; margin: 20px; }
        /*.chat-box { width: 180%; height: 300px; border: 1px solid #ccc; padding: 10px; overflow-y: scroll; }*/
        .chat-input { margin-top: 10px; }
         .chat-container {
             display: flex;
             justify-content: center;
             align-items: center;
             gap: 20px;
             margin-bottom: 20px;
         }
        .empty{
            width:180px;
        }
        .chat-box {
            width: 500px;
            height: 400px;
            background-color: black;
            border: 2px solid #FFA500;
            padding: 15px;
            overflow-y: scroll;
            color: #FFA500;
            font-size: 1em;
            border-radius: 10px;
            align-self: center;
        }

        .side-panel {
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 15px;
            border-radius: 10px;
            min-width: 200px;
            height: fit-content;
        }

        .chat-id {
            margin-bottom: 20px;
            font-size: 1.1em;
            color: #FFA500;
        }

        .delete-button {
            background-color: black;
            color: #FFA500;
            border: 2px solid #FFA500;
            padding: 10px 20px;
            font-size: 1em;
            cursor: pointer;
            border-radius: 8px;
            transition: background-color 0.3s;
        }

        .delete-button:hover {
            background-color: #FFA500;
            color: black;
        }
    </style>


</head>
<body>
<canvas id="matrixCanvas"></canvas>
<div class="content">
    <h2>Welcome to the Chat Page</h2>

    <div class="chat-container">
        <div class="empty"></div>
        <div class="chat-box" id="chatBox">
            <% for (String msg : messages) { %>
            <div><%= msg %></div>
            <% } %>
        </div>

        <div class="side-panel" id="sidePanel">
            <div class="chat-id">
                <strong>Chat ID:</strong> <%= session.getAttribute("roomId") %>
            </div>
            <form action="deleteRoom" method="post" class="delete-room-form">
                <button type="submit" class="delete-button">Delete Room</button>
            </form>
        </div>
    </div>
    <form method="post" class="chat-input">
        <input type="text" name="message" placeholder="Type a message..." required />
        <button type="submit">Send</button>
    </form>

</div>
<script>
    // Auto-scroll chat box to bottom
    var chatBox = document.getElementById('chatBox');
    chatBox.scrollTop = chatBox.scrollHeight;
</script>
<script>
    const canvas = document.getElementById('matrixCanvas');
    const ctx = canvas.getContext('2d');
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;

    const letters = "アァイィウヴエェオカキクケコサシスセソタチツテトABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    const fontSize = 16;
    const columns = canvas.width / fontSize;
    const drops = Array(Math.floor(columns)).fill(1);

    function drawMatrix() {
        ctx.fillStyle = "rgba(0, 0, 0, 0.05)";
        ctx.fillRect(0, 0, canvas.width, canvas.height);
        ctx.fillStyle = "#0F0";
        ctx.font = `${fontSize}px monospace`;

        for (let i = 0; i < drops.length; i++) {
            const text = letters[Math.floor(Math.random() * letters.length)];
            ctx.fillText(text, i * fontSize, drops[i] * fontSize);
            if (drops[i] * fontSize > canvas.height && Math.random() > 0.975) {
                drops[i] = 0;
            }
            drops[i]++;
        }
    }

    setInterval(drawMatrix, 33);
    window.addEventListener('resize', () => {
        resizeCanvas();
        initMatrix();
    });
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
<script>
    window.addEventListener('load', () => {
        document.body.classList.add('loaded');
    });
</script>



</body>
</html>

