package it.unisa.tsw_proj.model.dao;

import it.unisa.tsw_proj.utils.DriverManagerConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public static boolean doCheckUsernameAvailability(String username) {
        final String sql = "SELECT username FROM user WHERE username = ?";

        try {
            Connection con = DriverManagerConnectionPool.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            DriverManagerConnectionPool.releaseConnection(con);
            boolean res = !rs.next();
            rs.close();
            ps.close();

            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}