package it.unisa.tsw_proj.utils;

import it.unisa.tsw_proj.model.bean.CartBean;
import it.unisa.tsw_proj.model.bean.UserBean;
import it.unisa.tsw_proj.model.dao.CartDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public final class SessionSetter {

    // Costruttore
    private SessionSetter() {}

    // Metodi
    public static void setSessionToLogin(HttpSession s, UserBean us, boolean register) {
        s.setAttribute("user", us);

        CartBean cart;
        if (register) {
            cart = (CartBean) s.getAttribute("cart");
            if (cart == null) {
                cart = new CartBean();
                cart.setIdUser(us.getId());

                CartDAO.createOrUpdateCart(cart, us.getId(), false);
            }
            else {
                cart.setIdUser(us.getId());
                CartDAO.createOrUpdateCart(cart, us.getId(), true);
            }
        }
        else
            cart = CartDAO.getCartByUser(us);

        s.setAttribute("cart", cart);
    }

    public static boolean isLogged(HttpServletRequest req) {
        return isLogged(req.getSession());
    }

    public static boolean isLogged(HttpSession s) {
        return s.getAttribute("user") != null;
    }
}
