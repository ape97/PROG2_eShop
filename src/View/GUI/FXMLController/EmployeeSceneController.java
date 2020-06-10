package View.GUI.FXMLController;

import Controller.MainController;
import Model.Event;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    public void initialize() {
        initEventTableView();
    }


    @FXML
    private void button_addArticle(ActionEvent event) throws IOException {


    }

    @FXML
    private void button_editArticle(ActionEvent event) throws IOException {


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

        TableColumn<Event, String> columnFirstname= new TableColumn<>("Vorname");
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
}