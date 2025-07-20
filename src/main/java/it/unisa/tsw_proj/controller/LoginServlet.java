package it.unisa.tsw_proj.controller;

import java.io.IOException;

import it.unisa.tsw_proj.model.bean.UserBean;
import it.unisa.tsw_proj.model.dao.UserDAO;
import it.unisa.tsw_proj.utils.FieldValidator;
import it.unisa.tsw_proj.utils.SessionSetter;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (SessionSetter.isLogged(request)) {
            response.sendRedirect("/");
            return;
        }

        String username = request.getParameter("username");
        String password = request.getParameter("pass");

        if (!checkParams(username, password)) {
            sendToLoginWithError(response);
            return;
        }

        UserBean us = UserDAO.doLoginUser(username, password);
        if (us == null) {
            sendToLoginWithError(response);
            return;
        }

        SessionSetter.setSessionToLogin(request.getSession(), us, false);
        response.sendRedirect("/");
    }

    // Metodi privati
    private static void sendToLoginWithError(HttpServletResponse response) throws IOException {
        response.sendRedirect("/myrenovatech/login?error=wrong_data");
    }

    private static boolean checkParams(String username, String password) {
        // Validate username
        if (!FieldValidator.usernameValidate(username))
            return false;

        // Validate password
        return FieldValidator.passwordValidate(password);
    }
}