<!DOCTYPE html>
<head>
    <title>Welcome</title>
    <link rel="stylesheet" type="text/css" href="index.css">
</head>
<body>
<canvas id="matrixCanvas"></canvas>
<h2>Select an option</h2><br/>
<a href="JoinRoom.jsp"> <button>Join Room</button></a>
<a href="CreateRoom.jsp"> <button>Create Room</button></a>
</body>
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



</html>

