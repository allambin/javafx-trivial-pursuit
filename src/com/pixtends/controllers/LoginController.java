package com.pixtends.controllers;

import com.pixtends.helpers.DBHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button loginButton;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    void initialize() {
        DBHandler dbHandler = new DBHandler();
        try {
            Connection connection = dbHandler.getConnection();
        } catch (SQLException e) {
            // todo disable login button if something goes wrong here
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                loginButton.getScene().getWindow().hide();
                Stage cardListStage = new Stage();
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("../views/cardList.fxml"));
                    Scene cardListScene = new Scene(root);
                    cardListStage.setTitle("Trivial Pursuit - Admin Panel");
                    cardListStage.setScene(cardListScene);
                    cardListStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
