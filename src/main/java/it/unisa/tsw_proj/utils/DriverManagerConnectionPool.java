package it.unisa.tsw_proj.utils;

import java.sql.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class DriverManagerConnectionPool {

    // Costruttore privato per evitare di istanziare
    private DriverManagerConnectionPool() {}

    // Attributi
    private static final String DB_URL = "jdbc:mysql://server:3306/renovatech_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static final long MAX_IDLE_TIME = 120_000; // 2 minuti
    private static final int CHECK_PERIOD = 30;
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private static final Logger logger = Logger.getLogger(DriverManagerConnectionPool.class.getName());

    private static class PooledConnection {
        Connection connection;
        long releaseTime;

        PooledConnection(Connection connection) {
            this.connection = connection;
            this.releaseTime = System.currentTimeMillis();
        }
    }

    private static final List<PooledConnection> freeDbConnections = new LinkedList<>();

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver DB non trovato: " + e.getMessage());
        }

        // Thread che rimuove connessioni inattive
        scheduler.scheduleAtFixedRate(() -> {
            synchronized (DriverManagerConnectionPool.class) {
                Iterator<PooledConnection> it = freeDbConnections.iterator();
                long now = System.currentTimeMillis();

                while (it.hasNext()) {
                    PooledConnection pc = it.next();
                    if (now - pc.releaseTime > MAX_IDLE_TIME) {
                        try {
                            pc.connection.close();
                        } catch (SQLException e) {
                            logSqlError(e);
                        }
                        it.remove();
                    }
                }
            }
        }, 1, CHECK_PERIOD, TimeUnit.SECONDS);
    }

    private static synchronized Connection createDBConnection() {
        Connection newConnection = null;

        try {
            newConnection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            newConnection.setAutoCommit(true);
        } catch (SQLException e) {
            logSqlError(e);
        }

        return newConnection;
    }

    public static synchronized Connection getConnection() {
        while (!freeDbConnections.isEmpty()) {
            Connection c = freeDbConnections.removeFirst().connection;
            try {
                if (!c.isClosed() && c.isValid(2))
                    return c;
            } catch (SQLException e) {
                logSqlError(e);
            }
        }

        return createDBConnection();
    }

    public static synchronized void releaseConnection(Connection c) {
        try {
            if (c != null && !c.isClosed() && c.isValid(2)) {
                freeDbConnections.add(new PooledConnection(c));
            }
        } catch (SQLException e) {
            logSqlError(e);
        }
    }

    public static void closeSqlParams(Connection c, PreparedStatement ps) {
        releaseConnection(c);

        try {
            if (ps != null) ps.close();
        } catch (SQLException e) {
            logSqlError(e);
        }
    }

    public static void closeSqlParams(Connection c, PreparedStatement ps, ResultSet rs) {
        closeSqlParams(c, ps);

        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            logSqlError(e);
        }
    }

    public static void shutdown() {
        scheduler.shutdown();
    }

    public static void logSqlError(SQLException e, Logger logger) {
        logger.log(Level.SEVERE, "SQL error", e);
    }

    private static void logSqlError(SQLException e) {
        logSqlError(e, logger);
    }
}