package com.pixtends.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CardDetailsController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea answerTextArea;

    @FXML
    private TextArea questionTextArea;

    @FXML
    private ComboBox<?> typeComboBox;

    @FXML
    private Button saveButton;


    @FXML
    void initialize() {
        questionTextArea.requestFocus();
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                saveButton.getScene().getWindow().hide();
            }
        });
    }
}
