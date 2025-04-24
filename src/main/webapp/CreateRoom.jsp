<%--
  Created by IntelliJ IDEA.
  User: rajas
  Date: 4/18/2025
  Time: 2:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <title>Create Room</title>
    <link rel="stylesheet" type="text/css" href="CreateRoom.css">
</head>
<body>
<canvas id="matrixCanvas"></canvas>
    <h2>Create a Chat Room</h2>
    <form action="/createroom" method="post">
        <label>Enter Username:</label>
        <input type="text" name="username" />
        <input type="submit" />
    </form>
<script>
    const canvas = document.getElementById('matrixCanvas');
    const ctx = canvas.getContext('2d');

    function resizeCanvas() {
        canvas.width = window.innerWidth;
        canvas.height = Math.max(document.body.scrollHeight, window.innerHeight);
    }

    resizeCanvas(); // Initial sizing

    const letters = "アァイィウヴエェオカキクケコサシスセソタチツテトナニヌネノハヒフヘホABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    const fontSize = 16;
    let columns, drops;

    function initMatrix() {
        columns = Math.floor(canvas.width / fontSize);
        drops = Array(columns).fill(1);
    }

    function drawMatrix() {
        ctx.fillStyle = "rgba(0, 0, 0, 0.05)";
        ctx.fillRect(0, 0, canvas.width, canvas.height);

        ctx.fillStyle = "#0F0";
        ctx.font = `${fontSize}px monospace`;

        for (let i = 0; i < drops.length; i++) {
            const text = letters.charAt(Math.floor(Math.random() * letters.length));
            ctx.fillText(text, i * fontSize, drops[i] * fontSize);

            if (drops[i] * fontSize > canvas.height && Math.random() > 0.975) {
                drops[i] = 0;
            }

            drops[i]++;
        }
    }

    initMatrix();
    setInterval(drawMatrix, 33);

    window.addEventListener('resize', () => {
        resizeCanvas();
        initMatrix();
    });
</script>
</body>
</html>
