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
            sendToLogin(response, "0");
            return;
        }

        UserBean us = UserDAO.doLoginUser(username, password);
        if (us == null) {
            sendToLogin(response, "0");
            return;
        }

        SessionSetter.setSessionToLogin(request.getSession(), us);
        response.sendRedirect("/");
    }

    // Metodi privati
    private static void sendToLogin(HttpServletResponse response, String error) throws IOException {
        response.sendRedirect("/myrenovatech/login" + (error != null ? "?error=" + error : ""));
    }

    private static boolean checkParams(String username, String password) {
        // Validate username
        if (!FieldValidator.usernameValidate(username))
            return false;

        // Validate password
        return FieldValidator.passwordValidate(password);
    }
}