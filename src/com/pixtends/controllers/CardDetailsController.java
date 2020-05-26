package com.pixtends.controllers;

import com.pixtends.helpers.TypeStringConverter;
import com.pixtends.models.Card;
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
    private Card editingCard;

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
//        typeComboBox.getSelectionModel().selectFirst();
        typeComboBox.setConverter(new TypeStringConverter(typeComboBox));

        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // todo handle return + errors
                saveCard();
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

        Platform.runLater(() -> {
            questionTextArea.requestFocus();
            if (editingCard != null) {
                questionTextArea.setText(editingCard.getQuestion());
                answerTextArea.setText(editingCard.getAnswer());
                typeComboBox.setValue(editingCard.getType());
            } else {
                typeComboBox.getSelectionModel().selectFirst();
            }
        });
    }

    public void setEditingCard(Card card) {
        this.editingCard = card;
    }

    public boolean saveCard() {
        String questionTextAreaText = questionTextArea.getText();
        String answerTextAreaText = answerTextArea.getText();
        CardType cardType = typeComboBox.getSelectionModel().getSelectedItem();
        if (editingCard == null) {
            // todo handle return
            return cardRepository.create(questionTextAreaText, answerTextAreaText, cardType);
        } else {
            editingCard.setQuestion(questionTextAreaText);
            editingCard.setAnswer(answerTextAreaText);
            editingCard.setType(cardType);
            return cardRepository.update(editingCard);
        }
    }
}
