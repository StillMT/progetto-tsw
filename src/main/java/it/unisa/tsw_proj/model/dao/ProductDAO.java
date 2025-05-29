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

public class ProductDAO {

    // Metodi
    public static List<ProductBean> doGetAllProducts() {
        final String sql = "SELECT * FROM product";
        final List<ProductBean> products = new ArrayList<ProductBean>();
        final List<ProductVariantBean> variants = ProductVariantDAO.doGetAllProductsVariants();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();
            while (rs.next()) {
                boolean found = false;

                ProductBean product = new ProductBean(rs.getInt("id"), rs.getString("brand"), rs.getString("model"), rs.getString("description"), rs.getInt("base_price"), rs.getInt("id_category"));

                for (ProductVariantBean productVariantBean : variants)
                    if (productVariantBean.getIdProduct() == product.getId()) {
                        found = true;

                        product.addVariant(productVariantBean);
                        variants.remove(productVariantBean);
                    } else if (found)
                        break;

                products.add(product);
            }
        } catch (SQLException e) {
            DriverManagerConnectionPool.logSqlError(e, logger);
        } finally {
            DriverManagerConnectionPool.closeSqlParams(con, ps, rs);
        }

        return products;
    }

    // Attributi
    private static final Logger logger = Logger.getLogger(ProductDAO.class.getName());
}