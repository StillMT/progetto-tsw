package it.unisa.tsw_proj.controller.listener;

import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletContextEvent;
import it.unisa.tsw_proj.utils.DriverManagerConnectionPool;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DriverManagerConnectionPool.shutdown();
    }

}