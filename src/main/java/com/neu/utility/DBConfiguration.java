/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neu.utility;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author abhishek
 */
public class DBConfiguration {

    public static Configuration getConfiguaration() {
        Configuration configuration = null;
        try {
            Properties properties = new Properties();
            InputStream inputStream;
            inputStream = new FileInputStream(System.getProperty("user.home") + "/configurartion.properties");
            properties.load(inputStream);
            inputStream.close();

            String dbName = properties.getProperty("dbName").trim();
            String dbUserName = properties.getProperty("dbUserName").trim();
            String dbPassword = properties.getProperty("dbPassword").trim();
            String dbHostName = properties.getProperty("dbHostName").trim();
            System.out.println(dbHostName+dbName+dbPassword+dbUserName);
            

            configuration = new Configuration()
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                    .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                    .setProperty("hibernate.connection.url", "jdbc:mysql://"+dbHostName+":3306/" + dbName+"?createDatabaseIfNotExist=true&useUnicode=yes&characterEncoding=UTF-8")
                    .setProperty("hibernate.connection.username", dbUserName)
                    .setProperty("hibernate.connection.password", dbPassword)
                    .setProperty("hibernate.hbm2ddl.auto", "create")
                    .setProperty("hibernate.enable_lazy_load_no_trans", "true")
                    .setProperty("show_sql", "true")
                    .addResource("user.hbm.xml")
                    .addResource("book.hbm.xml")
                    .addResource("seller.hbm.xml")
                    .addResource("Cart.hbm.xml");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return configuration;
    }

}
