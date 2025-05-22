package it.unisa.tsw_proj.model.dao;

import it.unisa.tsw_proj.model.UserRole;
import it.unisa.tsw_proj.model.bean.UserBean;
import it.unisa.tsw_proj.utils.DriverManagerConnectionPool;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    // Metodi pubblici
    public static int doRegisterUser(UserBean user) {
        final String sql = "INSERT INTO user VALUES (NULL, ?, ?, ?, ?, ?, ?, ?);";
        int result = 0;

        try {
            Connection con = DriverManagerConnectionPool.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPhone());
            ps.setString(6, user.getNationality());
            ps.setString(7, UserRole.USER.toDb());

            if (ps.executeUpdate() == 1)
                result = doFindIdByUser(user);

            ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static UserBean doLoginUser(String username, String password) {
        final String sql = "SELECT * FROM user WHERE username = ?";
        UserBean user = null;

        try {
            Connection con = DriverManagerConnectionPool.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String pwd = rs.getString("password_hash");

                if (BCrypt.checkpw(password, pwd))
                    user = new UserBean(rs.getInt("id"), rs.getString("full_name"), rs.getString("username"),
                            pwd, rs.getString("email"), rs.getString("cellphone"),
                            rs.getString("nationality"), UserRole.fromDb(rs.getString("role")));
            }

            rs.close();
            ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static boolean doCheckUsernameAvailability(String username) {
        final String sql = "SELECT username FROM user WHERE username = ?";

        return checkAvailability(username, sql);
    }

    public static boolean doCheckEmailAvailability(String email) {
        final String sql = "SELECT email FROM user WHERE email = ?";

        return checkAvailability(email, sql);
    }

    // Metodi privati
    private static boolean checkAvailability(String email, String sql) {
        try {
            Connection con = DriverManagerConnectionPool.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            boolean res = !rs.next();
            rs.close();
            ps.close();
            DriverManagerConnectionPool.releaseConnection(con);

            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private static int doFindIdByUser(UserBean user) {
        final String sql = "SELECT id FROM user WHERE username = ? AND email = ?";
        int id = -1;

        try {
            Connection con = DriverManagerConnectionPool.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());

            ResultSet rs = ps.executeQuery();
            if (rs.next())
                id = rs.getInt("id");

            rs.close();
            ps.close();
            DriverManagerConnectionPool.releaseConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }
}