package it.unisa.tsw_proj.model.dao;

import it.unisa.tsw_proj.model.bean.CartBean;
import it.unisa.tsw_proj.model.bean.UserBean;
import it.unisa.tsw_proj.utils.DriverManagerConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CartDAO {

    public static CartBean getCartByUser(UserBean us) {
        final String sql = "SELECT cart.id_product, cart.qty FROM cart WHERE cart.id_user = ?";
        CartBean cart = new CartBean();

        try {
            Connection con = DriverManagerConnectionPool.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, us.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                cart.addProduct(rs.getInt("id_product"), rs.getInt("qty"));

            rs.close();
            ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cart;
    }
}