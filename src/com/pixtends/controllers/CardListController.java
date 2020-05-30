package com.pixtends.controllers;

import com.pixtends.helpers.TypeStringConverter;
import com.pixtends.models.Card;
import com.pixtends.models.CardType;
import com.pixtends.repositories.CardRepository;
import com.pixtends.repositories.CardTypeRepository;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CardListController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<CardType> typeComboBox;

    @FXML
    private TableView<Card> cardListTableView;

    @FXML
    private Button newButton;

    @FXML
    private Button deleteButton;

    private CardRepository cardRepository;
    private CardTypeRepository cardTypeRepository;
    private ObservableList<Card> cardsList = FXCollections.observableArrayList();
    private ArrayList<Card> selectedCards;
    private Alert alert;

    public CardListController() {
        cardTypeRepository = new CardTypeRepository();
        cardRepository = new CardRepository();
        selectedCards = new ArrayList<>();
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText("Delete selected cards");
        alert.setContentText("Those cards will be deleted forever, please confirm the action.");
    }

    @FXML
    void initialize() {
        deleteButton.setDisable(true);
        newButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                openEditWindow(null);
            }
        });
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    selectedCards.forEach((c) -> cardRepository.delete(c.getId()));
                    selectedCards.clear();
                } else {
                    alert.hide();
                }
            }
        });

        ObservableList<CardType> cardTypes = FXCollections.observableArrayList();
        cardTypes.addAll(cardTypeRepository.findAll());
        cardTypes.add(0, null);
        typeComboBox.setItems(cardTypes);
        typeComboBox.setConverter(new TypeStringConverter(typeComboBox));

        initCardListTableView();

        cardListTableView.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Card>() {
            public void onChanged(ListChangeListener.Change<? extends Card> c) {
                selectedCards.clear();
                selectedCards.addAll(c.getList());
                if (!selectedCards.isEmpty()) {
                    deleteButton.setDisable(false);
                } else {
                    deleteButton.setDisable(true);
                }
            }
        });
    }

    private void initCardListTableView() {
        cardListTableView.setEditable(false);
        TableColumn<Card, String> questionColumn = new TableColumn<>("Question");
        questionColumn.setCellValueFactory(new PropertyValueFactory<Card, String>("question"));
        TableColumn<Card, String> answerColumn = new TableColumn<>("Answer");
        answerColumn.setCellValueFactory(new PropertyValueFactory<Card, String>("answer"));
        TableColumn<Card, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<Card, String>("typeName"));
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
                        Card selectedItem = null;
                        if (cardListTableView.getSelectionModel().getSelectedItem() != null) {
                            selectedItem = cardListTableView.getSelectionModel().getSelectedItem();
                        }
                        openEditWindow(selectedItem);
                    }
                });
                return cell;
            }
        });

        cardListTableView.getColumns().addAll(questionColumn, answerColumn, typeColumn, publishedColumn, actionsColumn);
        cardListTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        cardsList.addAll(cardRepository.findAll());
        FilteredList<Card> filteredData = new FilteredList<>(cardsList, p -> true);
        typeComboBox.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
            filteredData.setPredicate(card -> {
                // If filter text is empty, display all persons.
                if (newValue == null) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.getName().toLowerCase();
                return card.getTypeName().toLowerCase().contains(lowerCaseFilter);
            });
        });
        SortedList<Card> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(cardListTableView.comparatorProperty());
        cardListTableView.setItems(sortedData);
    }

    private void openEditWindow(Card card) {
        Stage cardDetailsStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/cardDetails.fxml"));
            Parent root = loader.load();
            CardDetailsController controller = loader.getController();
            controller.setEditingCard(card);
            Scene cardDetailsScene = new Scene(root);
            cardDetailsStage.setTitle("Trivial Pursuit - Edit Card");
            cardDetailsStage.setScene(cardDetailsScene);
            cardDetailsStage.initModality(Modality.APPLICATION_MODAL);
            cardDetailsStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshCardListTableView() {
        cardsList.clear();
        cardsList.addAll(cardRepository.findAll());
        cardListTableView.setItems(cardsList);
//        Platform.runLater(() -> {
//            ObservableList<Card> cards = FXCollections.observableArrayList();
//            cards.addAll(cardRepository.findAll());
////            cardListTableView.setItems(null);
////            cardListTableView.layout();
//            cardListTableView.setItems(cards);
//
//            cardListTableView.getColumns().get(0).setVisible(false);
//            cardListTableView.getColumns().get(0).setVisible(true);
//
//        });
    }
}
