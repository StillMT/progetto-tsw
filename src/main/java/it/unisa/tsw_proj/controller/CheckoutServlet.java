package it.unisa.tsw_proj.controller;

import it.unisa.tsw_proj.model.bean.*;
import it.unisa.tsw_proj.model.dao.AddressDAO;
import it.unisa.tsw_proj.model.dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/myrenovatech/cart/checkout/")
public class CheckoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserBean user = (UserBean) request.getSession().getAttribute("user");
        CartBean cart = (CartBean) request.getSession().getAttribute("cart");
        if (user == null || cart == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        List<CartedProduct> cartList = cart.getProductList();
        if (cartList.isEmpty()) {
            response.sendRedirect("/myrenovatech/cart/");
            return;
        }

        List<AddressBean> addresses = AddressDAO.doGetAddressesByIdUser(user.getId());
        if (addresses.isEmpty()) {
            response.sendRedirect("/myrenovatech/profile/?error=no_address");
            return;
        }

        String pvIdStr = request.getParameter("pVarId");
        int pvId = -1;
        try { pvId = Integer.parseInt(pvIdStr); } catch (NumberFormatException _) { }

        List<CartedProduct> selCartProd = new ArrayList<>();
        List<ProductBean> selProd = new ArrayList<>();
        for (CartedProduct cp : cartList)
            if (pvId > 0) {
                if (pvId == cp.getIdVar()) {
                    selCartProd.add(cp);
                    selProd.add(ProductDAO.doGetProduct(cp.getIdProd(), cp.getIdVar()));
                    break;
                }
            } else if (cp.getSelected()) {
                selCartProd.add(cp);
                selProd.add(ProductDAO.doGetProduct(cp.getIdProd(), cp.getIdVar()));
            }

        request.setAttribute("selCartProd", selCartProd);
        request.setAttribute("selProd", selProd);
        request.setAttribute("addresses", addresses);

        request.getRequestDispatcher("/myrenovatech/cart/checkout/view-checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }
}
