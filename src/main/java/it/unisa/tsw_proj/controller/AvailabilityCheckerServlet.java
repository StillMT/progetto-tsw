package it.unisa.tsw_proj.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import it.unisa.tsw_proj.model.dao.UserDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(urlPatterns = {"/check-username", "/check-email", "/check-phone"})
public class AvailabilityCheckerServlet extends HttpServlet {

    private static final Set<String> BAD_WORDS = new HashSet<>(Arrays.asList(
            "frocio", "negro", "gay"
    ));

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String servletPath = request.getServletPath();
        boolean result = false;

        switch (servletPath) {
            case "/check-username":
                String username = request.getParameter("username");
                if (username != null && !containsBadWord(username))
                    result = UserDAO.doCheckUsernameAvailability(username);
                break;

            case "/check-email":
                String email = request.getParameter("email");
                if (email != null)
                    result = UserDAO.doCheckEmailAvailability(email);
                break;

            case "/check-phone":
                String phone = request.getParameter("phone");
                if (phone != null)
                    result = UserDAO.doCheckPhoneAvailability(phone);
                break;
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{result: " + result + "}");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private boolean containsBadWord(String input) {
        String normalized = input.toLowerCase()
                .replace("3", "e")
                .replace("0", "o")
                .replace("1", "i")
                .replace("!", "i")
                .replace("$", "s")
                .replace("@", "a");

        for (String word : BAD_WORDS) {
            if (normalized.contains(word)) return true;
        }
        return false;
    }
}