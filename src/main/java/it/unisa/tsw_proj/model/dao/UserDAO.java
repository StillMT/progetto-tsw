package it.unisa.tsw_proj.model.dao;

import it.unisa.tsw_proj.model.LimitUserReturnStatus;
import it.unisa.tsw_proj.model.UserRole;
import it.unisa.tsw_proj.model.bean.UserBean;
import it.unisa.tsw_proj.utils.DriverManagerConnectionPool;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserDAO {

    // Attributi
    private static final Logger logger = Logger.getLogger(UserDAO.class.getName());

    // Metodi pubblici
    public static int doRegisterUser(UserBean user) {
        final String sql = "INSERT INTO user VALUES (NULL, ?, ?, ?, ?, ?, ?, ?);";
        int result = 0;

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPhone());
            ps.setString(6, user.getNationality());
            ps.setString(7, UserRole.USER.toDb());

            if (ps.executeUpdate() == 1)
                result = doFindIdByUser(user);
        } catch (SQLException e) {
            DriverManagerConnectionPool.logSqlError(e, logger);
        } finally {
            DriverManagerConnectionPool.closeSqlParams(con, ps);
        }

        return result;
    }

    public static UserBean doLoginUser(String username, String password) {
        final String sql = "SELECT * FROM user WHERE username = ?";
        UserBean user = null;

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, username);
            rs = ps.executeQuery();

            if (rs.next()) {
                String pwd = rs.getString("password_hash");

                if (BCrypt.checkpw(password, pwd))
                    user = new UserBean(rs.getInt("id"), rs.getString("full_name"), rs.getString("username"),
                            pwd, rs.getString("email"), rs.getString("cellphone"),
                            rs.getString("nationality"), UserRole.fromDb(rs.getString("role")));
            }
        } catch (SQLException e) {
            DriverManagerConnectionPool.logSqlError(e, logger);
        } finally {
            DriverManagerConnectionPool.closeSqlParams(con, ps, rs);
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

    public static boolean doCheckPhoneAvailability(String phone) {
        final String sql = "SELECT cellphone FROM user WHERE cellphone = ?";

        return checkAvailability(phone, sql);
    }

    // Metodi privati
    private static boolean checkAvailability(String str, String sql) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        boolean res = false;

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, str);
            rs = ps.executeQuery();

            res = rs.next();
        } catch (SQLException e) {
            DriverManagerConnectionPool.logSqlError(e, logger);
        } finally {
            DriverManagerConnectionPool.closeSqlParams(con, ps, rs);
        }

        return !res;
    }

    private static int doFindIdByUser(UserBean user) {
        final String sql = "SELECT id FROM user WHERE username = ? AND email = ?";
        int id = -1;

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());

            rs = ps.executeQuery();
            if (rs.next())
                id = rs.getInt("id");
        } catch (SQLException e) {
            DriverManagerConnectionPool.logSqlError(e, logger);
        } finally {
            DriverManagerConnectionPool.closeSqlParams(con, ps, rs);
        }

        return id;
    }

    public static List<UserBean> doGetAllUsers() {
        final String sql = "SELECT id, full_name, username, email, role, isLimited FROM user";
        List<UserBean> users = new ArrayList<>();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();
            while (rs.next()) {
                users.add(new UserBean(rs.getInt("id"), rs.getString("full_name"),
                        rs.getString("username"), rs.getString("email"),
                        UserRole.fromDb(rs.getString("role")), rs.getBoolean("isLimited")));
            }
        } catch (SQLException e) {
            DriverManagerConnectionPool.logSqlError(e, logger);
        } finally {
            DriverManagerConnectionPool.closeSqlParams(con, ps, rs);
        }

        return users;
    }

    public static LimitUserReturnStatus doToggleLimitation(int id) {
        String sql = "UPDATE user SET isLimited = NOT isLimited WHERE id = ? AND role != 'ADMIN'";
        boolean res = false;
        boolean isLimited = false;

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            if (ps.executeUpdate() == 1) {
                sql = "SELECT isLimited FROM user WHERE id = ?";

                ps = con.prepareStatement(sql);
                ps.setInt(1, id);

                rs = ps.executeQuery();
                if (rs.next()) {
                    isLimited = rs.getBoolean("isLimited");
                    res = true;
                }
            }
        } catch (SQLException e) {
            DriverManagerConnectionPool.logSqlError(e, logger);
        } finally {
            DriverManagerConnectionPool.closeSqlParams(con, ps, rs);
        }

        if (res)
            return isLimited ? LimitUserReturnStatus.OK_LIMITED : LimitUserReturnStatus.OK_UNLOCKED;
        else
            return LimitUserReturnStatus.NOT_OK;
    }
}