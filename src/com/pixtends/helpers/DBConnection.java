package com.pixtends.helpers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static Connection dbConnection = null;

    static {
        Properties properties = new Properties();
        String filename = "./src/com/pixtends/db.config";
        InputStream is = null;
        try {
            is = new FileInputStream(filename);
            properties.load(is);
            Class.forName("com.mysql.jdbc.Driver");
            String connectionString = "jdbc:mysql://" + properties.getProperty("DB_HOST") + ":" + properties.getProperty("DB_PORT") + "/" + properties.getProperty("DB_NAME");
            dbConnection = DriverManager.getConnection(connectionString, properties.getProperty("DB_USER"), properties.getProperty("DB_PASSWORD"));
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return dbConnection;
    }
}
