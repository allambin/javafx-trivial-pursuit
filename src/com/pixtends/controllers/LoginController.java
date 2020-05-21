package com.pixtends.controllers;

import com.pixtends.services.AuthService;
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

    private AuthService authService;

    public LoginController() {
        authService = new AuthService();
    }

    @FXML
    void initialize() {
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    if (!authService.login(usernameField.getText(), passwordField.getText())) {
                        // todo error text field
                        System.out.println("Login information not recognised");
                        return;
                    }
                    loginButton.getScene().getWindow().hide();
                    Stage cardListStage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("../views/cardList.fxml"));
                    Scene cardListScene = new Scene(root);
                    cardListStage.setTitle("Trivial Pursuit - Admin Panel");
                    cardListStage.setScene(cardListScene);
                    cardListStage.show();
                } catch (SQLException e) {
                    System.out.println("Login unsuccessful");
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.println("Error FXML");
                    e.printStackTrace();
                }
            }
        });
    }
}
