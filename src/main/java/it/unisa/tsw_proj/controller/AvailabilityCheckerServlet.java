package it.unisa.tsw_proj.controller;

import java.io.IOException;

import it.unisa.tsw_proj.model.dao.UserDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

@WebServlet(urlPatterns = {"/check-username", "/check-email"})
public class AvailabilityCheckerServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean result = false;

        if (request.getServletPath().equals("/check-username")) {
            if (!containsBadWord(request.getParameter("username")))
                result = UserDAO.doCheckUsernameAvailability(request.getParameter("username"));
        }
        else if (request.getServletPath().equals("/check-email"))
            result = UserDAO.doCheckEmailAvailability(request.getParameter("email"));

        response.setContentType("text/plain");
        response.getWriter().write(String.valueOf(result));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private boolean containsBadWord(String username) {
        String normalized = username.toLowerCase()
                .replace("3", "e")
                .replace("0", "o")
                .replace("1", "i")
                .replace("!", "i")
                .replace("$", "s")
                .replace("@", "a");

        String[] badWords = {
                "frocio", "negro", "gay"        // Da aggiungere altro in un eventuale caso
        };

        for (String word : badWords)
            if (normalized.contains(word))
                return true;

        return false;
    }

}