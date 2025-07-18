package it.unisa.tsw_proj.controller;

import it.unisa.tsw_proj.model.LimitUserReturnStatus;
import it.unisa.tsw_proj.model.bean.OrderBean;
import it.unisa.tsw_proj.model.bean.UserBean;
import it.unisa.tsw_proj.model.dao.OrderDAO;
import it.unisa.tsw_proj.model.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Writer;

@WebServlet(urlPatterns = {"/myrenovatech/admin/customers/customerServlet", "/myrenovatech/orders/orderServlet"})
public class CustomerOrderServlet extends HttpServlet {

    final String RESULT_FALSE = "{\"result\":false}";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Writer w = response.getWriter();

        String action = request.getParameter("action");
        if (action == null)
            action = "";

        if (request.getRequestURI().equals("/myrenovatech/admin/customers/customerServlet"))
            switch (action) {
                case "getCustomers":
                    JSONArray us = new JSONArray();
                    for (UserBean u : UserDAO.doGetAllUsers())
                        us.put(new JSONObject().put("id", u.getId())
                                .put("fullName", u.getFullName())
                                .put("username", u.getUsername())
                                .put("email", u.getEmail())
                                .put("role", u.getRole())
                                .put("isLimited", u.getIsLimited()));
                    w.write(us.toString());
                    break;

                case "getOrders":
                    getOrders(request.getParameter("userId"), w, true, -1);
                    break;

                case "toggleLimitation":
                    int idUs;
                    try {
                        idUs = Integer.parseInt(request.getParameter("userId"));
                    } catch (NumberFormatException _) {
                        w.write(RESULT_FALSE);
                        return;
                    }

                    LimitUserReturnStatus res = UserDAO.doToggleLimitation(idUs);

                    boolean result = res == LimitUserReturnStatus.OK_LIMITED || res == LimitUserReturnStatus.OK_UNLOCKED;
                    JSONObject o = new JSONObject().put("result", result);
                    if (result)
                        o.put("unlockedNow", res == LimitUserReturnStatus.OK_UNLOCKED);

                    w.write(o.toString());
                    break;

                case "cancelOrder":
                    String nr = request.getParameter("orderNr");

                    if (nr != null)
                        w.write("{\"result\":" + OrderDAO.doCancelOrder(nr, -1) + "}");
                    else
                        w.write(RESULT_FALSE);
                    break;

                default:
                    w.write(RESULT_FALSE);
                    break;
            }
        else {
            UserBean u = (UserBean) request.getSession().getAttribute("user");
            String redirectUrl = "/myrenovatech/orders/view/";

            switch (action) {
                case "getOrders":
                    getOrders(null, w, false, u.getId());
                    break;

                case "cancelOrder":
                    String nr = request.getParameter("orderNr");
                    if (nr == null) {
                        response.sendRedirect(redirectUrl);
                        return;
                    }

                    redirectUrl += "?nr=" + nr;
                    if (!OrderDAO.doCancelOrder(nr, (!u.isAdmin() ? u.getId() : -1)))
                        redirectUrl += "&error=cancel_failed";

                    response.sendRedirect(redirectUrl);
                    break;

                default:
                    response.sendRedirect(redirectUrl);
                    break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void getOrders(String idUser, Writer w, boolean isAdmin, int intUserId) throws IOException {
        int id;

        if (isAdmin)
            try {
                id = Integer.parseInt(idUser);
            } catch (NumberFormatException _) {
                w.write(RESULT_FALSE);
                return;
            }
        else
            id = intUserId;

        JSONArray or = new JSONArray();
        for (OrderBean o : OrderDAO.doGetOrdersFromIdUser(id)) {
            JSONObject obj = new JSONObject().put("nr", o.getOrderNr()).put("date", o.getOrderDate())
                    .put("totalPrice", o.getTotalPrice()).put("address", o.getAddress().toString())
                    .put("order_state", o.getState()).put("tracking", o.getTracking());

            or.put(obj);
        }
        w.write(or.toString());
    }
}
