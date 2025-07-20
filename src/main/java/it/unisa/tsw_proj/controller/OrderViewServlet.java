package it.unisa.tsw_proj.controller;

import it.unisa.tsw_proj.model.bean.UserBean;
import it.unisa.tsw_proj.model.dao.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/myrenovatech/orders/view/")
public class OrderViewServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);/////////////////// DA METTERE QUI LOGICA PER FARE IL PAGAMENTO
    }
}
