package it.unisa.tsw_proj.model.dao;

import it.unisa.tsw_proj.model.OrderState;
import it.unisa.tsw_proj.model.bean.AddressBean;
import it.unisa.tsw_proj.model.bean.OrderBean;
import it.unisa.tsw_proj.utils.DriverManagerConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class OrderDAO {

    public static List<OrderBean> doGetOrdersFromIdUser(int id) {
        final String sql = "SELECT o.*, a.street, a.city, a.state, a.zip, a.country, SUM(oi.price * oi.qt) AS total_price FROM `order` o JOIN address a ON o.id_address = a.id JOIN order_item oi ON o.id = oi.id_order WHERE o.id_user = ? GROUP BY o.id, o.order_nr, o.date, a.street, a.city, a.state, a.zip, a.country";
        List<OrderBean> orders = new ArrayList<>();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                orders.add(new OrderBean(rs.getInt("id"), rs.getInt("id_user"), rs.getString("order_nr"),
                        new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("date")),
                        rs.getDouble("total_price"), new AddressBean(rs.getString("street"),
                        rs.getString("city"), rs.getString("a.state"), rs.getString("zip"),
                        rs.getString("country").toUpperCase()), OrderState.valueOf(rs.getString("o.state").toUpperCase()),
                        rs.getString("tracking")));
            }
        } catch (SQLException e) {
            DriverManagerConnectionPool.logSqlError(e, logger);
        } finally {
            DriverManagerConnectionPool.closeSqlParams(con, ps, rs);
        }

        return orders;
    }

    public static boolean doCancelOrder(String nr, int idUser) {
        final String sql = "UPDATE `order` SET state = 'canceled' WHERE order_nr = ? AND state != 'canceled' AND state != 'delivered'" + (idUser != -1 ? " AND id_user = ?" : "");
        boolean result = false;

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, nr);

            if (ps.executeUpdate() > 0)
                result = true;
        } catch (SQLException e) {
            DriverManagerConnectionPool.logSqlError(e, logger);
        } finally {
            DriverManagerConnectionPool.closeSqlParams(con, ps);
        }

        return result;
    }

    // Attributi
    private static final Logger logger = Logger.getLogger(OrderDAO.class.getName());
}
