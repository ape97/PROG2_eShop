package View.GUI.FXMLController;

import Controller.MainController;
import Model.Article;
import Model.Event;
import Utilities.Message;
import Utilities.Result;
import View.GUI.MainSceneController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class EmployeeSceneController {

    @FXML
    private TableView tableView_events;

    @FXML
    private TableView tableView_articles;

    @FXML
    public void initialize() {
        initEventTableView();
        initArticleTableView();
    }


    @FXML
    private void button_addArticle(ActionEvent event) throws IOException {
        MainSceneController.showAddArticleScene(this, event);
    }

    @FXML
    private void button_editArticle(ActionEvent event) throws IOException {


    }

    @FXML
    private void button_removeArticle(ActionEvent event) throws IOException {
        Object selectedItem = tableView_articles.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Article article = (Article) selectedItem;
            Result<Void> result = MainController.getInstance().removeArticle(article);

            String title = Message.get(Message.MessageType.Info);
            String header = Message.get(Message.MessageType.Info);
            String message = result.getMessage();

            MainSceneController.showMessageBox(Alert.AlertType.INFORMATION, title, header, message);
        }
    }


    @FXML
    private void button_addEmployee(ActionEvent event) throws IOException {


    }

    @FXML
    private void button_editEmployee(ActionEvent event) throws IOException {


    }

    private void initEventTableView() {
        TableColumn<Event, Date> columnTimestamp = new TableColumn<>("Zeitstempel");
        columnTimestamp.setEditable(false);
        columnTimestamp.setSortable(true);
        columnTimestamp.setCellValueFactory(e -> new SimpleObjectProperty<Date>(e.getValue().getTimeStamp()));
        tableView_events.getColumns().add(columnTimestamp);

        TableColumn<Event, String> columnArticle = new TableColumn<>("Artikel");
        columnArticle.setEditable(false);
        columnArticle.setSortable(true);
        columnArticle.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getArticleName()));
        tableView_events.getColumns().add(columnArticle);

        TableColumn<Event, Integer> columnArticlenumber = new TableColumn<>("Artikelnummer");
        columnArticlenumber.setEditable(false);
        columnArticlenumber.setSortable(true);
        columnArticlenumber.setCellValueFactory(e -> new SimpleObjectProperty<Integer>(e.getValue().getArticleNumber()));
        tableView_events.getColumns().add(columnArticlenumber);

        TableColumn<Event, Integer> columnStockChange = new TableColumn<>("Bestandsänderung");
        columnStockChange.setEditable(false);
        columnStockChange.setSortable(true);
        columnStockChange.setCellValueFactory(e -> new SimpleObjectProperty<Integer>(e.getValue().getStockChangeValue()));
        tableView_events.getColumns().add(columnStockChange);

        TableColumn<Event, Integer> columnPersonId = new TableColumn<>("Person ID");
        columnPersonId.setEditable(false);
        columnPersonId.setSortable(true);
        columnPersonId.setCellValueFactory(e -> new SimpleObjectProperty<Integer>(e.getValue().getPersonId()));
        tableView_events.getColumns().add(columnPersonId);

        TableColumn<Event, String> columnFirstname = new TableColumn<>("Vorname");
        columnFirstname.setEditable(false);
        columnFirstname.setSortable(true);
        columnFirstname.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getPersonFirstname()));
        tableView_events.getColumns().add(columnFirstname);

        TableColumn<Event, String> columnLastname = new TableColumn<>("Nachname");
        columnLastname.setEditable(false);
        columnLastname.setSortable(true);
        columnLastname.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getPersonLastname()));
        tableView_events.getColumns().add(columnLastname);

        tableView_events.setItems(MainController.getInstance().getEventList().getObject());
    }

    private void initArticleTableView() {
        TableColumn<Article, String> columnName = new TableColumn<>("Bezeichnung");
        columnName.setEditable(false);
        columnName.setSortable(true);
        columnName.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getName()));
        tableView_articles.getColumns().add(columnName);

        TableColumn<Article, Integer> columnArticlenumber = new TableColumn<>("Artikelnummer");
        columnArticlenumber.setEditable(false);
        columnArticlenumber.setSortable(true);
        columnArticlenumber.setCellValueFactory(e -> new SimpleObjectProperty<Integer>(e.getValue().getArticleNumber()));
        tableView_articles.getColumns().add(columnArticlenumber);

        TableColumn<Article, Integer> columnStock = new TableColumn<>("Lagerbestand");
        columnStock.setEditable(false);
        columnStock.setSortable(true);
        columnStock.setCellValueFactory(e -> new SimpleObjectProperty<Integer>(e.getValue().getStock()));
        tableView_articles.getColumns().add(columnStock);

        TableColumn<Article, Double> columnStockChange = new TableColumn<>("Preis");
        columnStockChange.setEditable(false);
        columnStockChange.setSortable(true);
        columnStockChange.setCellValueFactory(e -> new SimpleObjectProperty<Double>(e.getValue().getPrice()));
        tableView_articles.getColumns().add(columnStockChange);

        tableView_articles.setItems(MainController.getInstance().getArticleList().getObject());
    }
}