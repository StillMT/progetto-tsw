package it.unisa.tsw_proj.controller;

import it.unisa.tsw_proj.model.bean.CartBean;
import it.unisa.tsw_proj.model.bean.CartedProduct;
import it.unisa.tsw_proj.model.bean.ProductBean;
import it.unisa.tsw_proj.model.bean.UserBean;
import it.unisa.tsw_proj.model.dao.CartDAO;
import it.unisa.tsw_proj.model.dao.ProductDAO;
import it.unisa.tsw_proj.utils.SessionSetter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/myrenovatech/cart/cartServlet", "/myrenovatech/cart/"})
public class CartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        HttpSession session = request.getSession();

        if ("/myrenovatech/cart/cartServlet".equals(uri)) {
            handleCartAction(request, response, session);
        } else if ("/myrenovatech/cart/".equals(uri)) {
            CartBean cart = (CartBean) session.getAttribute("cart");
            if (cart != null) {
                List<ProductBean> productList = new ArrayList<>();
                for (CartedProduct cp : cart.getProductList()) {
                    productList.add(ProductDAO.doGetProduct(cp.getIdProd(), cp.getIdVar()));
                }
                request.setAttribute("prod_list", productList);
            }
            request.getRequestDispatcher("/myrenovatech/cart/view-cart.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void handleCartAction(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        String action = request.getParameter("action");
        int cartId = parseInt(request.getParameter("cartId"));
        if (action == null || cartId <= 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        CartBean cart = (CartBean) session.getAttribute("cart");
        if (cart == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        boolean result = false;

        switch (action) {
            case "updateQty":
                int qty = parseInt(request.getParameter("qty"));
                if (qty < 0) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                result = updateQty(cart, cartId, qty);
                break;

            case "remove":
                result = removeFromCart(cart, cartId);
                break;

            case "toggleSelection":
                boolean selected = Boolean.parseBoolean(request.getParameter("toggleSelection"));
                result = toggleSelection(cart, cartId, selected);
                break;
        }

        if (result && SessionSetter.isLogged(session))
            result = CartDAO.createOrUpdateCart(cart, cart.getIdUser(), true);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Writer writer = response.getWriter();
        writer.write("{\"result\":" + result + "}");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!"/myrenovatech/cart/cartServlet".equals(request.getRequestURI())) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String action = request.getParameter("action");
        String itemId = request.getParameter("prodId");
        String itemVariantId = request.getParameter("pVariantId");
        String qtyStr = request.getParameter("qty");

        if (action == null || (!action.equals("addToCart") && !action.equals("buyNow")) ||
                itemId == null || itemVariantId == null || qtyStr == null) {
            sendError(response);
            return;
        }

        int qty, pId, pVarId;
        try {
            qty = Integer.parseInt(qtyStr);
            pId = Integer.parseInt(itemId);
            pVarId = Integer.parseInt(itemVariantId);
        } catch (NumberFormatException e) {
            sendError(response);
            return;
        }

        if (!ProductDAO.doCheckProductForCart(pId, pVarId, qty)) {
            sendError(response);
            return;
        }

        HttpSession session = request.getSession();
        CartBean cart = (CartBean) session.getAttribute("cart");
        if (cart == null)
            cart = new CartBean();

        cart.addProduct(-1, pId, pVarId, qty, true);

        if (SessionSetter.isLogged(session)) {
            UserBean user = (UserBean) session.getAttribute("user");
            CartDAO.createOrUpdateCart(cart, user.getId(), true);
        }

        session.setAttribute("cart", cart);

        if ("buyNow".equals(action))
            response.sendRedirect("/myrenovatech/cart/checkout/?pVarId=" + pVarId);
        else
            response.sendRedirect("/myrenovatech/cart/?result=adding_succesfully");
    }

    private boolean updateQty(CartBean cart, int cartId, int qty) {
        for (CartedProduct cp : cart.getProductList())
            if (cp.getId() == cartId) {
                cp.setQty(qty);
                return true;
            }
        return false;
    }

    private boolean removeFromCart(CartBean cart, int cartId) {
        List<CartedProduct> cpList = cart.getProductList();
        for (CartedProduct cp : cpList)
            if (cp.getId() == cartId)
                return cpList.remove(cp);

        return false;
    }

    private boolean toggleSelection(CartBean cart, int cartId, boolean selected) {
        for (CartedProduct cp : cart.getProductList())
            if (cp.getId() == cartId) {
                cp.setSelected(selected);
                return true;
            }

        return false;
    }

    private void sendError(HttpServletResponse response) throws IOException {
        response.sendRedirect("/myrenovatech/cart/?result=add_to_cart_failed");
    }

    private int parseInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}