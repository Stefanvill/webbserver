package se.iths.stefan.webbserver.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import se.iths.stefan.webbserver.TemplateEngineUtil;

import java.io.IOException;

@WebServlet("/")
public class RootServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Sätt variabler som blir tillgängliga i templatet som ${title}, ${message}
        req.setAttribute("title", "Min Jetty + Thymeleaf App");
        req.setAttribute("message", "Välkommen till startsidan! Renderad med Thymeleaf.");

        try {
            // Renderar templates/index.html
            TemplateEngineUtil.process("index", req, resp, getServletContext());
        } catch (Exception e) {
            throw new ServletException("Kunde inte rendera template", e);
        }
    }
}
