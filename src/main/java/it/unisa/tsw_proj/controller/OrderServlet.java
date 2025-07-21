package it.unisa.tsw_proj.controller;

import it.unisa.tsw_proj.model.bean.*;
import it.unisa.tsw_proj.model.dao.AddressDAO;
import it.unisa.tsw_proj.model.dao.OrderDAO;
import it.unisa.tsw_proj.model.dao.ProductDAO;
import it.unisa.tsw_proj.model.dao.ProductVariantDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Random;

@WebServlet(urlPatterns = { "/myrenovatech/orders/view/", "/myrenovatech/orders/orderCheckoutServlet" })
public class OrderServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!request.getRequestURI().equals("/myrenovatech/orders/view/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String nr = request.getParameter("nr");

        if (nr != null) {
            UserBean u = (UserBean) request.getSession().getAttribute("user");

            Integer id = null;
            if (!u.isAdmin())
                id = u.getId();

            request.setAttribute("order", OrderDAO.doGetOrderByNr(nr, id));
        }

        request.getRequestDispatcher("/myrenovatech/orders/view/view-order.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!request.getRequestURI().equals("/myrenovatech/orders/orderCheckoutServlet")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        HttpSession s = request.getSession();

        String addressId = request.getParameter("addressId");
        String[] pVIds = request.getParameterValues("pVIds[]");
        CartBean cart = (CartBean) s.getAttribute("cart");
        UserBean user = (UserBean) s.getAttribute("user");

        if (pVIds != null && pVIds.length > 0 && cart != null && user != null && addressId != null && AddressDAO.doCheckAddress(addressId, user.getId())) {
            AddressBean add = new AddressBean();
            add.setId(Integer.parseInt(addressId));

            OrderBean order = new OrderBean();
            order.setIdUser(user.getId());
            order.setOrderNr(generateOrderNumber());
            order.setShippingCost(3.99); // Spese di spedizione fisse
            order.setAddress(add);

            for (String pVId : pVIds)
                for (CartedProduct cp : cart.getProductList()) {
                    double cpPrice = ProductVariantDAO.doGetProductPrice(cp.getIdProd(), cp.getIdVar());

                    if (cpPrice >= 0 && String.valueOf(cp.getIdVar()).equals(pVId) && ProductDAO.doCheckProductForCart(cp.getIdProd(), cp.getIdVar(), cp.getQty()))
                        order.addItem(new OrderItemBean(cp.getIdProd(), cp.getIdVar(), cpPrice, cp.getQty()));
                }

            final String redirectUrl = "/myrenovatech/orders/";
            if (OrderDAO.doInsertOrder(order))
                response.sendRedirect(redirectUrl);
            else
                response.sendRedirect(redirectUrl + "?error=no_added_order");
        }
    }

    private String generateOrderNumber() {
        Random rand = new Random();

        int part1 = rand.nextInt(900) + 100;
        int part2 = rand.nextInt(900) + 100;
        int part3 = rand.nextInt(9000) + 1000;

        return part1 + "-" + part2 + "-" + part3;
    }
}
