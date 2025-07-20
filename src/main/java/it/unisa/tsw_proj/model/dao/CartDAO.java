package it.unisa.tsw_proj.model.dao;

import it.unisa.tsw_proj.model.bean.CartBean;
import it.unisa.tsw_proj.model.bean.CartedProduct;
import it.unisa.tsw_proj.model.bean.UserBean;
import it.unisa.tsw_proj.utils.DriverManagerConnectionPool;

import java.sql.*;
import java.util.List;
import java.util.logging.Logger;

public class CartDAO {

    public static CartBean getCartByUser(UserBean us) {
        final String sql = "SELECT id, id_product, id_product_variant, qty, selected, id_user FROM cart WHERE id_user = ?";
        CartBean cart = new CartBean();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, us.getId());
            rs = ps.executeQuery();

            boolean first = true;
            while (rs.next()) {
                if (first) {
                    cart.setIdUser(rs.getInt("id_user"));
                    first = false;
                }

                cart.addProduct(rs.getInt("id"), rs.getInt("id_product"), rs.getInt("id_product_variant"),
                        rs.getInt("qty"), rs.getBoolean("selected"));
            }
        } catch (SQLException e) {
            DriverManagerConnectionPool.logSqlError(e, logger);
        } finally {
            DriverManagerConnectionPool.closeSqlParams(con, ps, rs);
        }

        return cart;
    }

    public static boolean createOrUpdateCart(CartBean cart, int idUser,boolean update) {
        boolean result = false;

        try (Connection con = DriverManagerConnectionPool.getConnection()) {
            con.setAutoCommit(false);

            List<CartedProduct> products = cart.getProductList();

            if (update) {
                deleteMissingCartProducts(con, idUser, products);
                updateExistingCartProducts(con, products);
                insertNewCartProducts(con, products, idUser);
            } else
                insertNewCartProducts(con, products, idUser);

            con.commit();
            result = true;
        } catch (SQLException e) {
            DriverManagerConnectionPool.logSqlError(e, logger);
        }

        return result;
    }

    private static void deleteMissingCartProducts(Connection con, int idUser, List<CartedProduct> products) throws SQLException {
        List<Integer> idsToKeep = products.stream()
                .filter(p -> p.getId() > 0)
                .map(CartedProduct::getId)
                .toList();

        String sql = idsToKeep.isEmpty()
                ? "DELETE FROM cart WHERE id_user = ?"
                : "DELETE FROM cart WHERE id_user = ? AND id NOT IN (" + "?,".repeat(idsToKeep.size()).replaceAll(",$", "") + ")";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUser);
            for (int i = 0; i < idsToKeep.size(); i++)
                ps.setInt(i + 2, idsToKeep.get(i));

            ps.executeUpdate();
        }
    }

    private static void updateExistingCartProducts(Connection con, List<CartedProduct> products) throws SQLException {
        String sql = "UPDATE cart SET qty = ?, selected = ? WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            for (CartedProduct cp : products)
                if (cp.getId() > 0) {
                    ps.setInt(1, cp.getQty());
                    ps.setBoolean(2, cp.getSelected());
                    ps.setInt(3, cp.getId());

                    ps.addBatch();
                }

            ps.executeBatch();
        }
    }

    private static void insertNewCartProducts(Connection con, List<CartedProduct> products, int idUser) throws SQLException {
        String sql = "INSERT INTO cart VALUES (NULL, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            List<CartedProduct> toInsert = products.stream()
                    .filter(cp -> cp.getId() == -1)
                    .toList();

            for (CartedProduct cp : toInsert) {
                ps.setInt(1, cp.getIdProd());
                ps.setInt(2, cp.getIdVar());
                ps.setInt(3, cp.getQty());
                ps.setBoolean(4, cp.getSelected());
                ps.setInt(5, idUser);

                ps.addBatch();
            }

            ps.executeBatch();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                int i = 0;
                for (CartedProduct cp : toInsert)
                    if (rs.next())
                        cp.setId(rs.getInt(1));
            }
        }
    }

    // Attributi
    private static final Logger logger = Logger.getLogger(CartDAO.class.getName());
}