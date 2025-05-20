package it.unisa.tsw_proj.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.*;

public class DriverManagerConnectionPool {

    private static final String DB_URL = "jdbc:mysql://192.168.1.71:3306/renovatech_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Ciao";
    private static final long MAX_IDLE_TIME = 120_000; // 2 minuti
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

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
                        } catch (SQLException ignored) {}
                        it.remove();
                    }
                }
            }
        }, 1, 30, TimeUnit.SECONDS); // ogni 30 secondi
    }

    private static synchronized Connection createDBConnection() throws SQLException {
        Connection newConnection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        newConnection.setAutoCommit(true);
        return newConnection;
    }

    public static synchronized Connection getConnection() throws SQLException {
        while (!freeDbConnections.isEmpty()) {
            Connection c = freeDbConnections.removeFirst().connection;
            try {
                if (!c.isClosed() && c.isValid(2))
                    return c;
            } catch (SQLException ignored) {
                // ignore
            }
        }
        return createDBConnection();
    }

    public static synchronized void releaseConnection(Connection c) {
        try {
            if (c != null && !c.isClosed() && c.isValid(2)) {
                freeDbConnections.add(new PooledConnection(c));
            }
        } catch (SQLException ignored) {}
    }

    public static void shutdown() {
        scheduler.shutdown();
    }
}