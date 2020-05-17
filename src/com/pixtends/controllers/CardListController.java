package com.pixtends.controllers;

import com.pixtends.models.Card;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

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
    void initialize() {
        // prepare a list of cards (will come from a DB later on)
        ObservableList<Card> cards = FXCollections.observableArrayList(
                new Card("This is a question", "This is an answer"),
                new Card("Pom pom pom", "Nah nah nah")
        );

        TableColumn<Card, String> questionColumn = new TableColumn<>("Question");
        questionColumn.setCellValueFactory(new PropertyValueFactory<Card, String>("question"));
        TableColumn<Card, String> answerColumn = new TableColumn<>("Answer");
        answerColumn.setCellValueFactory(new PropertyValueFactory<Card, String>("answer"));
        TableColumn<Card, String> typeColumn = new TableColumn<>("Type");
        TableColumn<Card, String> publishedColumn = new TableColumn<>("Published");

        TableColumn<Card, String> actionsColumn = new TableColumn<>("");
        actionsColumn.setSortable(false);

        actionsColumn.setCellFactory(tc -> {
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
            return cell ;
        });

        cardListTableView.getColumns().addAll(questionColumn, answerColumn, typeColumn, publishedColumn, actionsColumn);
        cardListTableView.setItems(cards);
        cardListTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        Callback<TableColumn<Card, String>, TableCell<Card, String>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell call(TableColumn p) {
                        TableCell cell = new TableCell() {
                            @Override
                            protected void updateItem(Object o, boolean empty) {
                                super.updateItem(o, empty);
                                setText(empty ? null : getItem() == null ? "" : getItem().toString());
                                setGraphic(null);
                            }
                        };
                        cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                System.out.println("Click");
                            }
                        });
                        return cell;
                    }
                };
        questionColumn.setCellFactory(cellFactory);
    }
}
