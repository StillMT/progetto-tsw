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
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = { "/myrenovatech/cart/cartServlet", "/myrenovatech/cart/" })
public class CartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        switch (request.getRequestURI()) {
            case "/myrenovatech/cart/cartServlet":
                String action = request.getParameter("action");
                if (action != null) {
                    int cartId = parseInt(request.getParameter("cartId"));
                    if (cartId > 0) {
                        boolean result = false;
                        Writer w = response.getWriter();

                        HttpSession s = request.getSession();
                        CartBean cart = (CartBean) s.getAttribute("cart");

                        switch (action) {
                            case "updateQty":
                                int qty = parseInt(request.getParameter("qty"));
                                if (qty < 0) {
                                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                                    return;
                                }

                                for (CartedProduct cp : cart.getProductList())
                                    if (cp.getId() == cartId) {
                                        cp.setQty(qty);
                                        result = true;
                                        break;
                                    }
                                break;

                            case "remove":
                                List<CartedProduct> cpList = cart.getProductList();
                                for (CartedProduct cp : cpList)
                                    if (cp.getId() == cartId) {
                                        result = cpList.remove(cp);
                                        break;
                                    }
                                break;

                            case "toggleSelection":
                                boolean sel = Boolean.parseBoolean(request.getParameter("toggleSelection"));

                                for (CartedProduct cp : cart.getProductList())
                                    if (cp.getId() == cartId) {
                                        cp.setSelected(sel);
                                        result = true;
                                        break;
                                    }
                                break;
                        }

                        if (SessionSetter.isLogged(s) && result)
                            result = CartDAO.createOrUpdateCart(cart, cart.getIdUser(), true);

                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        w.write("{\"result\":" + result + "}");
                    }
                    else
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }
                break;

            case "/myrenovatech/cart/":
                CartBean cart = (CartBean) request.getSession().getAttribute("cart");
                if (cart != null) {
                    List<ProductBean> pList = new ArrayList<>();

                    for (CartedProduct cp : cart.getProductList())
                        pList.add(ProductDAO.doGetProduct(cp.getIdProd(), cp.getIdVar()));
                    request.setAttribute("prod_list", pList);
                }

                request.getRequestDispatcher("/myrenovatech/cart/view-cart.jsp").forward(request, response);
                break;

            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!request.getRequestURI().equals("/myrenovatech/cart/cartServlet")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String action = request.getParameter("action");
        String itemId = request.getParameter("prodId");
        String itemVariantId = request.getParameter("pVariantId");
        String qtyStr = request.getParameter("qty");

        int qty;
        int pId;
        int pVarId;

        if ((action != null && (action.equals("addToCart") || action.equals("buyNow"))) && itemId != null && itemVariantId != null && qtyStr != null)
            try {
                qty = Integer.parseInt(qtyStr);
                pId = Integer.parseInt(itemId);
                pVarId = Integer.parseInt(itemVariantId);
            } catch (NumberFormatException _) {
                sendError(response);
                return;
            }
        else {
            sendError(response);
            return;
        }

        if (!ProductDAO.doCheckProductForCart(pId, pVarId, qty)) {
            sendError(response);
            return;
        }

        HttpSession s = request.getSession();
        CartBean cart = (CartBean) s.getAttribute("cart");
        if (cart == null)
            cart = new CartBean();

        cart.addProduct(-1 ,pId, pVarId, qty, true);

        if (SessionSetter.isLogged(s)) {
            UserBean u = (UserBean) s.getAttribute("user");
            CartDAO.createOrUpdateCart(cart, u.getId(), true);
        }

        s.setAttribute("cart", cart);

        if (action.equals("buyNow"))
            response.sendRedirect("/myrenovatech/cart/checkout/?pVarId=" + pVarId);
        else
            response.sendRedirect("/myrenovatech/cart/?result=adding_succesfully");
    }

    private void sendError(HttpServletResponse response) throws IOException {
        response.sendRedirect("/myrenovatech/cart/?result=add_to_cart_failed");
    }

    private int parseInt(String str) {
        int num = -1;

        try {
            num = Integer.parseInt(str);
        } catch (NumberFormatException _) { }

        return num;
    }
}
