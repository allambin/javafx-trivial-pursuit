package com.pixtends.services;

import com.pixtends.helpers.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthService {
    static Connection connection
            = DBConnection.getConnection();

    public Boolean login(String username, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, username);
        ps.setString(2, password); // todo hash
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }
}
