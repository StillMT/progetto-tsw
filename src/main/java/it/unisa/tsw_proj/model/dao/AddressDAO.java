package it.unisa.tsw_proj.model.dao;

import it.unisa.tsw_proj.model.bean.AddressBean;
import it.unisa.tsw_proj.utils.DriverManagerConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AddressDAO {

    // Metodi
    public static List<AddressBean> doGetAddressesByIdUser(int idUser) {
        final String sql = "SELECT * FROM address WHERE id_user = ? AND isDeleted = FALSE";
        List<AddressBean> addresses = new ArrayList<>();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, idUser);

            rs = ps.executeQuery();

            while (rs.next()) {
                AddressBean add = new AddressBean(rs.getString("street"), rs.getString("city"),
                        rs.getString("state"), rs.getString("zip"), rs.getString("country"));
                add.setId(rs.getInt("id"));

                addresses.add(add);
            }
        } catch (SQLException e) {
            DriverManagerConnectionPool.logSqlError(e, logger);
        } finally {
            DriverManagerConnectionPool.closeSqlParams(con, ps, rs);
        }

        return addresses;
    }

    public static boolean doCheckAddress(String addressIdStr, int idUser) {
        int addressId;
        try { addressId = Integer.parseInt(addressIdStr); } catch (NumberFormatException _) { return false; }

        final String sql = "SELECT id FROM address WHERE id_user = ? AND id = ? AND isDeleted = FALSE";
        boolean result = false;

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DriverManagerConnectionPool.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, idUser);
            ps.setInt(2, addressId);

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
    private static final Logger logger = Logger.getLogger(AddressDAO.class.getName());
}
