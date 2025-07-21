package it.unisa.tsw_proj.controller;

import it.unisa.tsw_proj.model.dao.CategoryDAO;
import it.unisa.tsw_proj.model.dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/catalogue/")
public class PublicCatalogueServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("pList", ProductDAO.doGetAllProducts());
        request.setAttribute("catList", CategoryDAO.doGetAllCategories());

        request.getRequestDispatcher("/catalogue/view-catalogue.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
