package it.unisa.tsw_proj.model.dao;

import it.unisa.tsw_proj.model.bean.CartBean;
import it.unisa.tsw_proj.model.bean.UserBean;
import it.unisa.tsw_proj.utils.DriverManagerConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class CartDAO {

    public static CartBean getCartByUser(UserBean us) {
        final String sql = "SELECT cart.id_product, cart.qty FROM cart WHERE cart.id_user = ?";
        CartBean cart = new CartBean();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, us.getId());
            rs = ps.executeQuery();
            while (rs.next())
                cart.addProduct(rs.getInt("id_product"), rs.getInt("qty"));
        } catch (SQLException e) {
            DriverManagerConnectionPool.logSqlError(e, logger);
        } finally {
            DriverManagerConnectionPool.closeSqlParams(con, ps, rs);
        }

        return cart;
    }

    // Attributi
    private static final Logger logger = Logger.getLogger(CartDAO.class.getName());
}