package it.unisa.tsw_proj.model.dao;

import it.unisa.tsw_proj.model.OrderState;
import it.unisa.tsw_proj.model.bean.*;
import it.unisa.tsw_proj.utils.DriverManagerConnectionPool;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class  OrderDAO {

    public static List<OrderBean> doGetOrdersFromIdUser(int id) {
        final String sql = "SELECT o.id, o.id_user, o.order_nr, o.date, o.shipping_costs, o.state AS order_state, o.tracking, a.street, a.city, a.state AS address_state, a.zip, a.country, SUM(oi.price * oi.qt) AS total_price FROM `order` o JOIN address a ON o.id_address = a.id JOIN order_item oi ON o.id = oi.id_order WHERE o.id_user = ? GROUP BY o.id, o.id_user, o.order_nr, o.date, o.shipping_costs, o.state, o.tracking, a.street, a.city, a.state, a.zip, a.country";
        List<OrderBean> orders = new ArrayList<>();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, id);
            rs = ps.executeQuery();

            while (rs.next())
                orders.add(getOBfromRS(rs));
        } catch (SQLException e) {
            DriverManagerConnectionPool.logSqlError(e, logger);
        } finally {
            DriverManagerConnectionPool.closeSqlParams(con, ps, rs);
        }

        return orders;
    }

    public static boolean doCancelOrder(String nr, int idUser) {
        final String sql = "UPDATE `order` SET state = 'canceled' WHERE order_nr = ? AND state != 'canceled' AND state != 'delivered'" + (idUser != -1 ? " AND id_user = ? AND state != 'shipped'" : "");
        boolean result = false;

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, nr);
            if (idUser != -1)
                ps.setInt(2, idUser);

            if (ps.executeUpdate() > 0)
                result = true;
        } catch (SQLException e) {
            DriverManagerConnectionPool.logSqlError(e, logger);
        } finally {
            DriverManagerConnectionPool.closeSqlParams(con, ps);
        }

        return result;
    }

    public static OrderBean doGetOrderByNr(String nr, Integer idUser) {
        String sql = "SELECT o.id, o.id_user, o.order_nr, o.date, o.shipping_costs, o.id_address, o.state AS order_state, o.tracking, a.street, a.city, a.state AS address_state, a.zip, a.country, u.username, SUM(oi.price * oi.qt) AS total_price FROM `order` o JOIN address a ON o.id_address = a.id JOIN user u ON o.id_user = u.id JOIN order_item oi ON o.id = oi.id_order WHERE o.order_nr = ? " + (idUser != null ? "AND o.id_user = ? " : "") + "GROUP BY o.id, o.order_nr, o.date, o.id_user, o.id_address, o.shipping_costs, o.state, o.tracking, a.street, a.city, a.state, a.zip, a.country, u.username";

        OrderBean o = null;

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, nr);
            if (idUser != null)
                ps.setInt(2, idUser);

            rs = ps.executeQuery();
            if (rs.next()) {
                o = getOBfromRS(rs);

                sql = "SELECT oi.id_product, oi.id_product_variant, p.brand, p.model, pv.storage, pv.color, oi.price AS price_at_order_time, oi.qt AS quantity FROM order_item oi JOIN product p ON oi.id_product = p.id JOIN product_variant pv ON oi.id_product_variant = pv.id WHERE oi.id_order = ?";

                ps = con.prepareStatement(sql);
                ps.setInt(1, o.getId());

                rs.close();
                rs = ps.executeQuery();

                while (rs.next())
                    o.addItem(new OrderItemBean(new ProductBean(rs.getInt("id_product"), rs.getString("brand"),
                            rs.getString("model"), new ProductVariantBean(rs.getInt("id_product_variant"),
                                rs.getString("color"),rs.getInt("storage"))),
                            rs.getDouble("price_at_order_time"), rs.getInt("quantity")));
            }
        } catch (SQLException e) {
            DriverManagerConnectionPool.logSqlError(e, logger);
        } finally {
            DriverManagerConnectionPool.closeSqlParams(con, ps, rs);
        }

        return o;
    }

    private static OrderBean getOBfromRS(ResultSet rs) throws SQLException {
        String username = null;
        try {
            username = rs.getString("username");
        } catch (SQLException _) {}

        return new OrderBean(rs.getInt("id"), rs.getInt("id_user"), rs.getString("order_nr"),
                new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("date")),
                rs.getDouble("total_price"), rs.getDouble("shipping_costs"),
                new AddressBean(rs.getString("street"), rs.getString("city"),
                        rs.getString("address_state"), rs.getString("zip"),
                        rs.getString("country")), OrderState.valueOf(rs.getString("order_state").toUpperCase()),
                rs.getString("tracking"), username);
    }

    // Attributi
    private static final Logger logger = Logger.getLogger(OrderDAO.class.getName());
}
