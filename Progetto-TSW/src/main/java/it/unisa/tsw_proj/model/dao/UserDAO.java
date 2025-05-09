package it.unisa.tsw_proj.model.dao;

import it.unisa.tsw_proj.model.UserBean;
import it.unisa.tsw_proj.model.UserRole;
import it.unisa.tsw_proj.utils.DriverManagerConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public UserBean doRetrieveByKey(int id) throws SQLException {
        final String sql = "SELECT * FROM users WHERE id_user = ?";
        UserBean us = null;

        try (Connection con = DriverManagerConnectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                us = new UserBean();
                setUser(rs, us);
            }

            rs.close();
        }

        return us;
    }

    public List<UserBean> doRetrieveAll() throws SQLException {
        final String sql = "SELECT * FROM users";
        List<UserBean> list = new ArrayList<>();

        try (Connection con = DriverManagerConnectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                UserBean us = new UserBean();
                setUser(rs, us);
                list.add(us);
            }

            rs.close();
        }

        return list;
    }

    public void doSave(UserBean us) throws SQLException {
        final String sql = "INSERT INTO users (username_user, password_user, email_user, phone_user, role_user) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DriverManagerConnectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, us.getUsername());
            ps.setString(2, us.getPassword());
            ps.setString(3, us.getEmail());
            ps.setString(4, us.getPhone());
            ps.setString(5, us.getRole().toDb());

            ps.executeUpdate();
        }
    }

    public boolean doDelete(int id) throws SQLException {
        final String sql = "DELETE FROM users WHERE id_user = ?";
        int res = 0;

        try (Connection con = DriverManagerConnectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            res = ps.executeUpdate();
        }

        return res > 0;
    }

    private void setUser(ResultSet rs, UserBean us) throws SQLException {
        us.setId(rs.getInt("id_user"));
        us.setUsername(rs.getString("username_user"));
        us.setPassword(rs.getString("password_user"));
        us.setEmail(rs.getString("email_user"));
        us.setPhone(rs.getString("phone_user"));
        us.setRole(UserRole.fromDb(rs.getString("role_user")));
    }
}
