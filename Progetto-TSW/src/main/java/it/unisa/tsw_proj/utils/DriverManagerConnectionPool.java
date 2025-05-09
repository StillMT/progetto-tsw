package it.unisa.tsw_proj.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DriverManagerConnectionPool {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/renovatech_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static List<Connection> freeDbConnections = new LinkedList<>();

    // Carico correttamente il driver JBDC per comunicare con MySQL
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver DB non trovato: " + e.getMessage());
        }
    }

    private static synchronized Connection createDBConnection() throws SQLException {
        Connection newConnection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        newConnection.setAutoCommit(true);

        return newConnection;
    }

    public static synchronized Connection getConnection() throws SQLException {
        if (!freeDbConnections.isEmpty()) {
            Connection c = freeDbConnections.removeFirst();
            try {
                if (c.isClosed())
                    return getConnection();
            } catch (SQLException e) {
                return getConnection();
            }
            return c;
        }

        return createDBConnection();
    }

    public static synchronized void releaseConnection(Connection c) {
        if (c != null)
            freeDbConnections.add(c);
    }
}