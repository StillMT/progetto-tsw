package it.unisa.tsw_proj.controller;

import java.io.IOException;

import it.unisa.tsw_proj.model.UserBean;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

@WebServlet("/myrenovatech")
public class ReservedAreaServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserBean us = (UserBean) request.getSession().getAttribute("user");
        if (us != null)
            response.sendRedirect("/myrenovatech/");
        else
            response.sendRedirect("/myrenovatech/login/");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}