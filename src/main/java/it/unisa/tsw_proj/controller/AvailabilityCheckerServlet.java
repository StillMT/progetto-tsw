package it.unisa.tsw_proj.controller;

import java.io.IOException;

import it.unisa.tsw_proj.model.dao.UserDAO;
import it.unisa.tsw_proj.utils.FieldValidator;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(urlPatterns = {"/check-username", "/check-email", "/check-phone"})
public class AvailabilityCheckerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String servletPath = request.getServletPath();
        boolean result = false;

        switch (servletPath) {
            case "/check-username":
                String username = request.getParameter("username");
                if (FieldValidator.usernameValidate(username) && !FieldValidator.containsBadWord(username))
                    result = UserDAO.doCheckUsernameAvailability(username);
                break;

            case "/check-email":
                String email = request.getParameter("email");
                if (FieldValidator.emailValidate(email))
                    result = UserDAO.doCheckEmailAvailability(email);
                break;

            case "/check-phone":
                String phone = request.getParameter("phone");
                if (FieldValidator.phoneValidate(phone))
                    result = UserDAO.doCheckPhoneAvailability(phone);
                break;
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"result\": " + result + "}");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}