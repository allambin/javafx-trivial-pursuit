package com.pixtends.helpers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBHandler {
    private Connection dbConnection;

    public Connection getConnection() throws SQLException, IOException {
        Properties properties = new Properties();
        String filename = "./src/com/pixtends/db.config";
        InputStream is = new FileInputStream(filename);
        properties.load(is);

        String connectionString = "jdbc:mysql://" + properties.getProperty("DB_HOST") + ":" + properties.getProperty("DB_PORT") + "/" + properties.getProperty("DB_NAME");
        dbConnection = DriverManager.getConnection(connectionString, properties.getProperty("DB_USER"), properties.getProperty("DB_PASSWORD"));
        return dbConnection;
    }
}
