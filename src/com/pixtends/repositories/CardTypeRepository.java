package com.pixtends.repositories;

import com.pixtends.helpers.DBConnection;
import com.pixtends.models.CardType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CardTypeRepository {
    static Connection connection
            = DBConnection.getConnection();

    // todo exception handling
    public ArrayList<CardType> findAll() {
        ArrayList<CardType> cardTypes = new ArrayList<>();
        String query = "SELECT id, name, color FROM card_types";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String color = rs.getString(3);
                CardType cardType = new CardType(id, name, color);
                cardTypes.add(cardType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cardTypes;
    }
}
