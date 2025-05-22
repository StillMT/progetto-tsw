package it.unisa.tsw_proj.utils;

import it.unisa.tsw_proj.model.bean.CartBean;
import it.unisa.tsw_proj.model.bean.UserBean;
import it.unisa.tsw_proj.model.dao.CartDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.net.http.HttpRequest;

public final class SessionSetter {

    // Costruttore
    private SessionSetter() {}

    // Metodi
    public static void setSessionToLogin(HttpSession s, UserBean us) {
        s.setAttribute("user", us);
        s.setAttribute("cart", CartDAO.getCartByUser(us));
    }

    public static boolean isLogged(HttpServletRequest req) {
        return req.getSession().getAttribute("user") != null;
    }

    public static boolean isLogged(HttpSession s) {
        return s.getAttribute("user") != null;
    }
}
