package it.unisa.tsw_proj.model.dao;

import it.unisa.tsw_proj.model.bean.CategoryBean;
import it.unisa.tsw_proj.utils.DriverManagerConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CategoryDAO {

    // Metodi
    public static List<CategoryBean> doGetAllCategories() {
        if (!cats.isEmpty())
            return cats;

        final String sql = "SELECT * FROM category ORDER BY name";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();
            while (rs.next())
                cats.add(new CategoryBean(rs.getInt("id"), rs.getString("name")));

        } catch (SQLException e) {
            DriverManagerConnectionPool.logSqlError(e, logger);
        } finally {
            DriverManagerConnectionPool.closeSqlParams(con, ps, rs);
        }

        return cats;
    }

    public static boolean doCheckCategory(int idCategory) {
        if (cats.stream().anyMatch(c -> c.getId() == idCategory))
            return true;

        final String sql = "SELECT id FROM category WHERE id = ?";
        boolean result = false;

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, idCategory);
            rs = ps.executeQuery();

            result = rs.next();
        } catch (SQLException e) {
            DriverManagerConnectionPool.logSqlError(e, logger);
        } finally {
            DriverManagerConnectionPool.closeSqlParams(con, ps, rs);
        }

        return result;
    }

    // Attributi
    private static final Logger logger = Logger.getLogger(CategoryDAO.class.getName());
    private static final List<CategoryBean> cats = new ArrayList<>();
}