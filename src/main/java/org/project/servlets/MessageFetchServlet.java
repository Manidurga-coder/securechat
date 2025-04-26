package org.project.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.project.Handler.MyClientHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/getMessages")
public class MessageFetchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<String> messages = MyClientHandler.incomingMessages;

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        out.print("[");
        for (int i = 0; i < messages.size(); i++) {
            out.print("\"" + messages.get(i).replace("\"", "\\\"") + "\"");
            if (i < messages.size() - 1) out.print(",");
        }
        out.print("]");
        out.flush();
        out.close();
    }

}
