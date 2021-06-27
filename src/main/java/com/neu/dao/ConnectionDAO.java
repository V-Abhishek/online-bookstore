/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neu.dao;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Abhishek
 */
public class ConnectionDAO {

    // private static final SessionFactory sf = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    //private static final SessionFactory sf = DBConfiguration.getConfiguaration().buildSessionFactory();
    private static final Logger logger = Logger.getLogger(ConnectionDAO.class);
    private Session session = null;

    private static final SessionFactory sf = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        SessionFactory sessionFactory = null;
        try {
            Properties properties = new Properties();
            InputStream inputStream;
            inputStream = new FileInputStream("/home/ubuntu/configuaration.properties");
            properties.load(inputStream);
            inputStream.close();

            String dbName = properties.getProperty("dbName").trim();
            String dbUserName = properties.getProperty("dbUserName").trim();
            String dbPassword = properties.getProperty("dbPassword").trim();
            String dbHostName = properties.getProperty("dbHostName").trim();
            String connectionUrl = "jdbc:mysql://" + dbHostName + "/" + dbName + "?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
            logger.info(connectionUrl);
            sessionFactory = new Configuration()
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect")
                    .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                    .setProperty("hibernate.connection.url", connectionUrl)
                    .setProperty("hibernate.connection.username", dbUserName)
                    .setProperty("hibernate.connection.password", dbPassword)
                    .setProperty("hibernate.hbm2ddl.auto", "update")
                    .setProperty("hibernate.enable_lazy_load_no_trans", "true")
                    .setProperty("show_sql", "true")
                    .setProperty("hibernate.c3p0.min_size", "500")
                    .setProperty("hibernate.c3p0.max_size", "1000")
                    .setProperty("hibernate.c3p0.timeout", "120")
                    .setProperty("hibernate.c3p0.idle_test_period", "5")
                    .setProperty("hibernate.connection.requireSSL", "true")
                    .setProperty("hibernate.connection.useSSL", "true")
                    .addResource("user.hbm.xml")
                    .addResource("book.hbm.xml")
                    .addResource("seller.hbm.xml")
                    .addResource("Cart.hbm.xml").buildSessionFactory();

        } catch (Exception ex) {
            logger.error("DB Prob", ex);
        }
        return sessionFactory;

    }

    public static SessionFactory getSessionFactory() {
        return sf;
    }

    public Session getSession() {
        if (session == null || !session.isOpen()) {
            session = getSessionFactory().openSession();
        }
        return session;
    }

    public void beginTransaction() {
        getSession().beginTransaction();
    }

    public void commit() {
        getSession().getTransaction().commit();;
    }

    public void close() {
        getSession().close();
    }

    public void rollbackTransaction() {
        getSession().getTransaction().rollback();
    }
}
