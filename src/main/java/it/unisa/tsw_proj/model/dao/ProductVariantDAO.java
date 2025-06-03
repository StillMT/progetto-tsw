package it.unisa.tsw_proj.model.dao;

import it.unisa.tsw_proj.model.bean.ProductVariantBean;
import it.unisa.tsw_proj.utils.DriverManagerConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ProductVariantDAO {

    // Metodi
    public static List<ProductVariantBean> doGetAllProductsVariants() {
        final String sql = "SELECT * FROM product_variant ORDER BY id_product";
        final List<ProductVariantBean> list = new ArrayList<>();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();
            while (rs.next())
                list.add(new ProductVariantBean(rs.getInt("id"), rs.getInt("id_product"), rs.getString("color"), rs.getInt("storage"), rs.getInt("stock"), rs.getDouble("price")));
        } catch (SQLException e) {
            DriverManagerConnectionPool.logSqlError(e, logger);
        } finally {
            DriverManagerConnectionPool.closeSqlParams(con, ps, rs);
        }

        return list;
    }

    // Attributi
    private static final Logger logger = Logger.getLogger(ProductVariantDAO.class.getName());
}