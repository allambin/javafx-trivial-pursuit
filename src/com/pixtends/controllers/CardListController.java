package com.pixtends.controllers;

import com.pixtends.models.Card;
import com.pixtends.models.CardType;
import com.pixtends.repositories.CardRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CardListController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<?> typeComboBox;

    @FXML
    private TableView<Card> cardListTableView;

    @FXML
    private Button newButton;

    private CardRepository cardRepository;

    public CardListController() {
        cardRepository = new CardRepository();
    }

    @FXML
    void initialize() {
        newButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                openEditWindow();
            }
        });

        ObservableList<Card> cards = FXCollections.observableArrayList();
        cards.addAll(cardRepository.findAll());
        initCardListTableView();
        cardListTableView.setItems(cards);
    }

    private void initCardListTableView() {
        TableColumn<Card, String> questionColumn = new TableColumn<>("Question");
        questionColumn.setCellValueFactory(new PropertyValueFactory<Card, String>("question"));
        TableColumn<Card, String> answerColumn = new TableColumn<>("Answer");
        answerColumn.setCellValueFactory(new PropertyValueFactory<Card, String>("answer"));
        TableColumn<Card, String> typeColumn = new TableColumn<>("Type");
        TableColumn<Card, String> publishedColumn = new TableColumn<>("Published");

        TableColumn<Card, String> actionsColumn = new TableColumn<>("");
        actionsColumn.setSortable(false);

        actionsColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell call(TableColumn p) {
                final Image editIcon = new Image("assets/images/edit.png");
                TableCell<Card, String> cell = new TableCell<Card, String>() {
                    ImageView imageView = new ImageView();

                    @Override
                    protected void updateItem(String active, boolean empty) {
                        super.updateItem(active, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            imageView.setFitWidth(15);
                            imageView.setFitHeight(15);
                            imageView.setImage(editIcon);
                            setGraphic(imageView);
                        }
                    }
                };
                cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        openEditWindow();
                    }
                });
                return cell;
            }
        });

        cardListTableView.getColumns().addAll(questionColumn, answerColumn, typeColumn, publishedColumn, actionsColumn);
        cardListTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private void openEditWindow() {
        Stage cardDetailsStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../views/cardDetails.fxml"));
            Scene cardDetailsScene = new Scene(root);
            cardDetailsStage.setTitle("Trivial Pursuit - Edit Card");
            cardDetailsStage.setScene(cardDetailsScene);
            cardDetailsStage.initModality(Modality.APPLICATION_MODAL);
            cardDetailsStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
