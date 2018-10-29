package com.sbt.test.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServletContextInitializer implements ServletContextListener {

    private final static Logger log = LoggerFactory.getLogger(ServletContextInitializer.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("Initialization has completed!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("Application context has been destroyed!");
    }
}
