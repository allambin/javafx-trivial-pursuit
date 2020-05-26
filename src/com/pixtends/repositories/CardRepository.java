package com.pixtends.repositories;

import com.pixtends.helpers.DBConnection;
import com.pixtends.models.Card;
import com.pixtends.models.CardType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CardRepository {
    static Connection connection
            = DBConnection.getConnection();
    private CardTypeRepository cardTypeRepository;

    public CardRepository() {
        cardTypeRepository = new CardTypeRepository();
    }

    public ArrayList<Card> findAll() {
        ArrayList<CardType> cardTypes = cardTypeRepository.findAll();
        ArrayList<Card> cards = new ArrayList<>();
        String query = "SELECT id, question, answer, type_id FROM cards";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String question = rs.getString(2);
                String answer = rs.getString(3);
                int type_id = rs.getInt(4);
                Card card = new Card(id, question, answer, cardTypes.stream().filter(ct ->
                        ct.getId() == type_id).findFirst().orElse(null));
                cards.add(card);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    public boolean create(String question, String answer, CardType type) {
        String query = "INSERT INTO cards (question, answer, type_id) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, question);
            ps.setString(2, answer);
            ps.setInt(3, type.getId());
            return ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Card card) {
        String query = "UPDATE cards SET question = ?, answer = ?, type_id = ? WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, card.getQuestion());
            ps.setString(2, card.getAnswer());
            ps.setInt(3, card.getType().getId());
            ps.setInt(4, card.getId());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
