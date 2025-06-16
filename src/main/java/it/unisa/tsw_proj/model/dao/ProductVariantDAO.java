package it.unisa.tsw_proj.model.dao;

import it.unisa.tsw_proj.model.bean.ProductBean;
import it.unisa.tsw_proj.model.bean.ProductVariantBean;
import it.unisa.tsw_proj.utils.DriverManagerConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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

    public static boolean doInsertVariantBatch(ProductBean p, Connection con) {
        final String sql = "INSERT INTO product_variant VALUES (NULL, ?, ?, ?, ?, ?)";
        boolean result = false;

        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(sql);

            for (ProductVariantBean pv : p.getProductVariants()) {
                ps.setInt(1, p.getId());
                ps.setString(2, pv.getHexColor());
                ps.setInt(3, pv.getStorage());
                ps.setInt(4, pv.getStock());
                ps.setDouble(5, pv.getPrice());
                ps.addBatch();
            }

            int[] batchResult = ps.executeBatch();
            result = true;
            for (int r : batchResult)
                if (r < 1) {
                    result = false;
                    break;
                }
        } catch (SQLException e) {
            DriverManagerConnectionPool.logSqlError(e, logger);
        } finally {
            DriverManagerConnectionPool.closeSqlParams(null, ps);
        }

        return result;
    }

    public static boolean doUpdateVariantBatch(ProductBean p, Connection con) {
        boolean result = false;

        PreparedStatement ps = null;

        try {
            List<ProductVariantBean> pvs = p.getProductVariants();
            String sql = "DELETE FROM product_variant WHERE id_product = ? AND id NOT IN (" + pvs.stream().map(_ -> "?").collect(Collectors.joining(", ")) + ")";

            ps = con.prepareStatement(sql);

            ps.setInt(1, p.getId());
            for (int i = 0; i < pvs.size(); i++)
                ps.setInt(i + 2, pvs.get(i).getId());

            ps.executeUpdate();

            sql = "UPDATE product_variant SET color = ?, storage = ?, stock = ?, price = ? WHERE id = ?";
            ps = con.prepareStatement(sql);

            for (ProductVariantBean pv : pvs) {
                ps.setString(1, pv.getHexColor());
                ps.setInt(2, pv.getStorage());
                ps.setInt(3, pv.getStock());
                ps.setDouble(4, pv.getPrice());
                ps.setInt(5, pv.getId());
                ps.addBatch();
            }

            int[] batchResult = ps.executeBatch();
            result = true;
            for (int res : batchResult)
                if (res < 1) {
                    result = false;
                    break;
                }
        } catch (SQLException e) {
            DriverManagerConnectionPool.logSqlError(e, logger);
        } finally {
            DriverManagerConnectionPool.closeSqlParams(null, ps);
        }

        return result;
    }

    public static List<ProductVariantBean> doGetAllProductVariantsByProductId(int prodId, Connection con, ProductBean p) throws SQLException {
        final String sql = "SELECT * FROM product_variant WHERE id_product = ?";
        final List<ProductVariantBean> pvl = new ArrayList<>();

        boolean conGenerated = false;

        if (con == null || !con.isClosed()) {
            con = DriverManagerConnectionPool.getConnection();
            conGenerated = true;
        }

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = con.prepareStatement(sql);

            ps.setInt(1, prodId);
            rs = ps.executeQuery();

            while (rs.next()) {
                ProductVariantBean pv = new ProductVariantBean(rs.getInt("id"), rs.getInt("id_product"), rs.getString("color"), rs.getInt("storage"), rs.getInt("stock"), rs.getDouble("price"));

                if (p != null)
                    p.addVariant(pv);
                else
                    pvl.add(pv);
            }
        } catch (SQLException e) {
            DriverManagerConnectionPool.logSqlError(e, logger);
        } finally {
            DriverManagerConnectionPool.closeSqlParams(conGenerated ? con : null, ps, rs);
        }

        return pvl;
    }

    // Attributi
    private static final Logger logger = Logger.getLogger(ProductVariantDAO.class.getName());
}