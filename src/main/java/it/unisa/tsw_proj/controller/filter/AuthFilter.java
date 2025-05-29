package it.unisa.tsw_proj.controller.filter;

import it.unisa.tsw_proj.model.bean.UserBean;
import it.unisa.tsw_proj.utils.SessionSetter;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/myrenovatech/*", "/register", "/login"})
public class AuthFilter implements Filter {

    private static final String LOGIN = "/login";
    private static final String REGISTER = "/register";
    private static final String RESERVED_AREA = "/myrenovatech";
    private static final String CART = "/myrenovatech/cart";
    private static final String LOGIN_PAGE = "/myrenovatech/login";
    private static final String ADMIN_ROOT = "/myrenovatech/admin";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String path = req.getRequestURI();
        String context = req.getContextPath();
        boolean logged = SessionSetter.isLogged(req);

        if (path.startsWith(context + ADMIN_ROOT)) {
            if (UserBean.isAdmin(req))
                chain.doFilter(request, response);
            else if (logged)
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            else
                res.sendError(HttpServletResponse.SC_FORBIDDEN);

            return;
        }

        if (path.startsWith(context + LOGIN) || path.startsWith(context + REGISTER)) {
            if (logged)
                res.sendRedirect(RESERVED_AREA);
            else
                chain.doFilter(request, response);

            return;
        }

        if (path.startsWith(context + LOGIN_PAGE)) {
            if (!logged)
                chain.doFilter(request, response);
            else
                res.sendRedirect("/");

            return;
        }

        if (path.startsWith(context + CART)) {
            chain.doFilter(request, response);
            return;
        }

        if (path.startsWith(context + RESERVED_AREA)) {
            if (logged)
                chain.doFilter(request, response);
            else
                res.sendRedirect(LOGIN_PAGE);

            return;
        }

        // Default: blocca se non loggato
        if (logged)
            chain.doFilter(request, response);
        else {
            res.sendRedirect(LOGIN_PAGE + "?error=auth");
            return;
        }
    }
}