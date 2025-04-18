package org.project;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.http.*;

public class JoinRoomServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String roomId = req.getParameter("roomId");

        Object creator = getServletContext().getAttribute(roomId);

        if (creator != null) {
            HttpSession session = req.getSession();
            session.setAttribute("username", username);
            session.setAttribute("roomId", roomId);
            resp.sendRedirect("chat.jsp?room=" + roomId);
        } else {
            req.setAttribute("error", "Invalid or expired room code.");
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.println("Join Room");
    }
}

