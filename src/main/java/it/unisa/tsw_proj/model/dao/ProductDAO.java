package it.unisa.tsw_proj.model.dao;

import it.unisa.tsw_proj.model.bean.ProductBean;
import it.unisa.tsw_proj.model.bean.ProductVariantBean;
import it.unisa.tsw_proj.utils.DriverManagerConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ProductDAO {

    // Metodi
    public static List<ProductBean> doGetAllProducts() {
        final String sql = "SELECT * FROM product";
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
                        rs.getInt("base_price"),
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

    public static boolean doDeleteProduct(String idStr) {
        Integer id = null;
        try {

            id = Integer.parseInt(idStr);

        } catch (NumberFormatException _) {
            return false;
        }

        final String sql = "DELETE p, pv FROM product p LEFT JOIN product_variant pv ON pv.id_product = p.id WHERE p.id = ?";
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

    // Attributi
    private static final Logger logger = Logger.getLogger(ProductDAO.class.getName());
}