package it.unisa.tsw_proj.controller;

import it.unisa.tsw_proj.model.bean.ProductBean;
import it.unisa.tsw_proj.model.dao.CategoryDAO;
import it.unisa.tsw_proj.model.dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/products/")
public class ProductViewServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getParameter("prodId");
        String pVariantId = request.getParameter("pvId");

        ProductBean p = null;
        if (productId != null)
            try {
                p = ProductDAO.doGetProduct(Integer.parseInt(productId), null);

                if (pVariantId != null)
                    request.setAttribute("pVariantId", Integer.parseInt(pVariantId));
            } catch (NumberFormatException _) { }

        String forwardUrl = p != null ? "/products/imgs/getFileList?&productId=" + productId : "/products/view-product.jsp";
        if (p != null) {
            request.setAttribute("product", p);
            request.setAttribute("productView", Boolean.TRUE);
            request.setAttribute("catName", CategoryDAO.doGetCategoryById(p.getIdCategory()).getName());
        }

        request.getRequestDispatcher(forwardUrl).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}