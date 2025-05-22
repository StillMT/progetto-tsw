package it.unisa.tsw_proj.controller;

import java.io.IOException;

import it.unisa.tsw_proj.model.bean.UserBean;
import it.unisa.tsw_proj.model.dao.UserDAO;
import it.unisa.tsw_proj.utils.FieldValidator;
import it.unisa.tsw_proj.utils.SessionSetter;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionSetter.isLogged(request))
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        else
            response.sendRedirect("/");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession s = request.getSession();

        if (SessionSetter.isLogged(s)) {
            response.sendRedirect("/");
            return;
        }

        String fullName = request.getParameter("fullName");
        String username = request.getParameter("username");
        String password = request.getParameter("pass");
        String repPassword = request.getParameter("rep-pass");
        String email = request.getParameter("email");
        String phone = request.getParameter("cell");
        String country = request.getParameter("country");

        if (!checkParams(fullName, username, password, repPassword, email, phone, country)) {
            response.sendRedirect("/myrenovatech/login/?error=1");
            return;
        }

        UserBean user = new UserBean(fullName, username, BCrypt.hashpw(password, BCrypt.gensalt()), email, phone, country);
        int userId = UserDAO.doRegisterUser(user);
        if (userId > 0) {
            user.setId(userId);
            SessionSetter.setSessionToLogin(request.getSession(), user);
            response.sendRedirect("/");
        }
        else
            response.sendRedirect("/myrenovatech/login/?error=2");
    }

    private boolean checkParams(String fullName, String username, String password, String repPassword, String email, String cellphone, String country) {
        // Validate repeat password field
        if (!FieldValidator.repPswValidate(password, repPassword))
            return false;

        // Validate full name
        if (!FieldValidator.fullNameValidate(fullName))
            return false;

        // Validate username
        if (!FieldValidator.usernameValidate(username) || !UserDAO.doCheckUsernameAvailability(username))
            return false;

        // Validate password
        if (!FieldValidator.passwordValidate(password))
            return false;

        // Validate email
        if (!FieldValidator.emailValidate(email) || !UserDAO.doCheckEmailAvailability(email))
            return false;

        // Validate cellphone
        if (!FieldValidator.phoneValidate(cellphone))
            return false;

        // Validate country
        return FieldValidator.countryValidate(country);
    }
}