package com.pixtends.controllers;

import com.pixtends.helpers.TypeStringConverter;
import com.pixtends.models.CardType;
import com.pixtends.repositories.CardRepository;
import com.pixtends.repositories.CardTypeRepository;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.util.StringConverter;
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
    private ComboBox<CardType> typeComboBox;

    @FXML
    private Button saveButton;

    private CardTypeRepository cardTypeRepository;
    private CardRepository cardRepository;

    public CardDetailsController() {
        cardTypeRepository = new CardTypeRepository();
        cardRepository = new CardRepository();
    }

    @FXML
    void initialize() {
        // do not initialize fields annotated with @FXML!!
        ObservableList<CardType> cardTypes = FXCollections.observableArrayList();
        cardTypes.addAll(cardTypeRepository.findAll());
        typeComboBox.setItems(cardTypes);
        typeComboBox.getSelectionModel().selectFirst();
        typeComboBox.setConverter(new TypeStringConverter(typeComboBox));

        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // save the new card
                // todo try/catch here?
                cardRepository.create(questionTextArea.getText(), answerTextArea.getText(), typeComboBox.getSelectionModel().getSelectedItem());
                saveButton.getScene().getWindow().hide();
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/cardList.fxml"));
                    loader.load();
                    CardListController controller = loader.getController();
                    controller.refreshCardListTableView();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Platform.runLater(() -> questionTextArea.requestFocus());
    }
}
