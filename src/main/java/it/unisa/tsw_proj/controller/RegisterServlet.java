package it.unisa.tsw_proj.controller;

import java.io.IOException;
import java.util.regex.Pattern;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullName = request.getParameter("fullName");
        String username = request.getParameter("username");
        String password = request.getParameter("pass");
        String repPassword = request.getParameter("rep-pass");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String country = request.getParameter("country");

        if (!checkParams(fullName, username, password, repPassword, email, phone, country))
            response.sendRedirect("/myrenovatech/login/?error=1");
    }

    private boolean checkParams(String fullName, String username, String password, String repPassword, String email, String cellphone, String country) {
        // Validate repeat password field
        if (!repPassword.equals(password))
            return false;

        // Validate full name
        if (!Pattern.compile("^(?=.{1,50}$)\\S+(?:\\s+\\S+)+$").matcher(fullName).matches())
            return false;

        // Validate username
        if (!Pattern.compile("^[a-zA-Z0-9_-]{3,20}$").matcher(username).matches())
            return false;

        // Validate password
        if (!Pattern.compile("^(?=.*[!@#$%^&*(),.?\":{}|<>_])[a-zA-Z0-9!@#$%^&*(),.?\":{}|<>_]{8,16}$").matcher(password).matches())
            return false;

        // Validate email
        if (!Pattern.compile("^[a-zA-Z0-9](?!.*?[.]{2})[a-zA-Z0-9._%+-]{0,63}@[a-zA-Z0-9](?!.*--)[a-zA-Z0-9.-]{0,253}\\.[a-zA-Z]{2,}$").matcher(email).matches())
            return false;

        // Validate cellphone
        if (!Pattern.compile("^\\+?[0-9]{9,15}$").matcher(cellphone).matches())
            return false;

        // Validate country
        return Pattern.compile("^[a-z]{2}$").matcher(country).matches();
    }
}