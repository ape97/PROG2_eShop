package View.GUI.FXMLController;

import Controller.MainController;
import Model.Article;
import Model.Employee;
import Model.Event;
import Utilities.Message;
import Utilities.Result;
import View.GUI.MainSceneController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.util.Date;


public class CustomerSceneController {

    @FXML
    private TableView tableView_events;
    @FXML
    private TableView tableView_articles;
    @FXML
    private TableView tableView_employees;

    @FXML
    public void initialize() {
        initEventTableView();
        initArticleTableView();
        initEmployeeTableView();
    }


    @FXML
    private void button_addArticle(ActionEvent event) throws IOException {
        MainSceneController.showAddArticleScene(this, event);
    }

    @FXML
    private void button_editArticle(ActionEvent event) throws IOException {
        // TODO
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
        MainSceneController.showRegisterEmployeeScene(this, event);
    }

    @FXML
    private void button_editEmployee(ActionEvent event) throws IOException {
        // TODO
    }

    @FXML
    private void button_removeEmployee_clicked(ActionEvent event) throws IOException {
        Object selectedItem = tableView_employees.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Employee employee = (Employee) selectedItem;
            Result<Void> result = MainController.getInstance().removePerson(employee);

            String title = Message.get(Message.MessageType.Info);
            String header = Message.get(Message.MessageType.Info);
            String message = result.getMessage();

            MainSceneController.showMessageBox(Alert.AlertType.INFORMATION, title, header, message);
        }
    }

    private void initEmployeeTableView() {

        TableColumn<Employee, Integer> columnEmployeeId = new TableColumn<>("ID");
        columnEmployeeId.setEditable(false);
        columnEmployeeId.setSortable(true);
        columnEmployeeId.setCellValueFactory(e -> new SimpleObjectProperty<Integer>(e.getValue().getId()));
        tableView_employees.getColumns().add(columnEmployeeId);

        TableColumn<Employee, String> columnEmployeeFirstname = new TableColumn<>("Vorname");
        columnEmployeeFirstname.setEditable(false);
        columnEmployeeFirstname.setSortable(true);
        columnEmployeeFirstname.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getFirstname()));
        tableView_employees.getColumns().add(columnEmployeeFirstname);

        TableColumn<Employee, String> columnEmployeeLastname = new TableColumn<>("Nachname");
        columnEmployeeLastname.setEditable(false);
        columnEmployeeLastname.setSortable(true);
        columnEmployeeLastname.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getLastname()));
        tableView_employees.getColumns().add(columnEmployeeLastname);

        TableColumn<Employee, String> columnEmployeeUsername = new TableColumn<>("Benutzername");
        columnEmployeeUsername.setEditable(false);
        columnEmployeeUsername.setSortable(true);
        columnEmployeeUsername.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getUsername()));
        tableView_employees.getColumns().add(columnEmployeeUsername);

        TableColumn<Employee, String> columnEmployeePassword = new TableColumn<>("Kennwort");
        columnEmployeePassword.setEditable(false);
        columnEmployeePassword.setSortable(true);
        columnEmployeePassword.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getPassword()));
        tableView_employees.getColumns().add(columnEmployeePassword);

        tableView_employees.setItems(MainController.getInstance().getEmployeeList().getObject());
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