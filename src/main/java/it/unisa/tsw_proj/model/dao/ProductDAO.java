package it.unisa.tsw_proj.model.dao;

import it.unisa.tsw_proj.model.bean.ProductBean;
import it.unisa.tsw_proj.model.bean.ProductVariantBean;
import it.unisa.tsw_proj.utils.DriverManagerConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ProductDAO {

    // Metodi
    public static boolean doCheckProductForCart(int id, int pvId, int qty) {
        final String sql = "SELECT p.id FROM product p JOIN product_variant pv ON pv.id_product = p.id WHERE p.id = ? AND pv.id = ? AND pv.stock >= ? AND  p.isDeleted = FALSE";
        boolean result = false;

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, id);
            ps.setInt(2, pvId);
            ps.setInt(3, qty);

            rs = ps.executeQuery();
            result = rs.next();
        } catch (SQLException e) {
            DriverManagerConnectionPool.logSqlError(e, logger);
        } finally {
            DriverManagerConnectionPool.closeSqlParams(con, ps, rs);
        }

        return result;
    }

    public static List<ProductBean> doGetAllProducts() {
        final String sql = "SELECT * FROM product WHERE isDeleted = FALSE";
        List<ProductBean> products = new ArrayList<>();
        List<ProductVariantBean> variants = ProductVariantDAO.doGetAllProductsVariants();

        Map<Integer, List<ProductVariantBean>> variantMap = new HashMap<>();
        for (ProductVariantBean v : variants)
            variantMap.computeIfAbsent(v.getIdProduct(), _ -> new ArrayList<>()).add(v);

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                ProductBean product = new ProductBean(
                        rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("description"),
                        rs.getInt("id_category")
                );

                List<ProductVariantBean> productVariants = variantMap.get(product.getId());
                if (productVariants != null)
                    for (ProductVariantBean variant : productVariants)
                        product.addVariant(variant);

                products.add(product);
            }

        } catch (SQLException e) {
            DriverManagerConnectionPool.logSqlError(e, logger);
        } finally {
            DriverManagerConnectionPool.closeSqlParams(con, ps, rs);
        }

        return products;
    }

    public static ProductBean doGetProduct(int id, Integer pvId) {
        final String sql = "SELECT * FROM product WHERE id = ?";
        ProductBean p = null;

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                p = new ProductBean(rs.getInt("id"), rs.getString("brand"), rs.getString("model"), rs.getString("description"), rs.getInt("id_category"));
                if (pvId == null)
                    ProductVariantDAO.doGetAllProductVariantsByProductId(id, con, p);
                else
                    p.addVariant(ProductVariantDAO.doGetProductVariantById(pvId, con));
            }
        } catch (SQLException e) {
            DriverManagerConnectionPool.logSqlError(e, logger);
        } finally {
            DriverManagerConnectionPool.closeSqlParams(con, ps, rs);
        }

        return p;
    }

    public static boolean doDeleteProduct(String idStr) {
        int id;

        try { id = Integer.parseInt(idStr); }
        catch (NumberFormatException _) { return false; }

        final String sql = "UPDATE product SET isDeleted = TRUE WHERE id = ?";
        boolean result = false;

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            if (ps.executeUpdate() > 0)
                result = true;
        } catch (SQLException e) {
            DriverManagerConnectionPool.logSqlError(e, logger);
        } finally {
            DriverManagerConnectionPool.closeSqlParams(con, ps);
        }

        return result;
    }

    public static int doInsertProduct(ProductBean product) {
        final String sql = "INSERT INTO product (brand, model, description, id_category) VALUES (?, ?, ?, ?)";
        int result = -1;

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DriverManagerConnectionPool.getConnection();
            con.setAutoCommit(false);

            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, product.getBrand());
            ps.setString(2, product.getModel());
            ps.setString(3, product.getDescription());
            ps.setInt(4, product.getIdCategory());

            if (ps.executeUpdate() > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    product.setId(rs.getInt(1));
                    if (ProductVariantDAO.doInsertVariantBatch(product, con)) {
                        con.commit();
                        result = rs.getInt(1);
                    } else
                        con.rollback();
                }
            }

            con.setAutoCommit(true);
        } catch (SQLException e) {
            DriverManagerConnectionPool.logSqlError(e, logger);
        } finally {
            DriverManagerConnectionPool.closeSqlParams(con, ps, rs);
        }

        return result;
    }

    public static boolean doUpdateProduct(ProductBean product) {
        final String sql = "UPDATE product SET brand = ?, model = ?, description = ?, id_category = ? WHERE id = ?";
        boolean result = false;

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, product.getBrand());
            ps.setString(2, product.getModel());
            ps.setString(3, product.getDescription());
            ps.setInt(4, product.getIdCategory());
            ps.setInt(5, product.getId());

            if (ps.executeUpdate() > 0)
                result = ProductVariantDAO.doUpdateVariantBatch(product, con);
        } catch (SQLException e) {
            DriverManagerConnectionPool.logSqlError(e, logger);
        } finally {
            DriverManagerConnectionPool.closeSqlParams(con, ps);
        }

        return result;
    }

    // Attributi
    private static final Logger logger = Logger.getLogger(ProductDAO.class.getName());
}