package View.GUI.FXMLController;

import Controller.MainController;
import Model.Article;
import Model.Employee;
import Model.Person;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


public class EmployeeController {

    @FXML private TableView employeeTable;
    //TabPane root = (Stage) ap.getScene().getWindow();

    @FXML
    public void initialize() {

        MainController mainController = new MainController();
        mainController.login("admin", "admin");


        TableColumn<Employee, String> columnName = new TableColumn<>("Vorname");
        columnName.setEditable(false);
        columnName.setSortable(true);
        columnName.setCellValueFactory( e -> new SimpleStringProperty(e.getValue().getFirstname()));

        employeeTable.getColumns().add(columnName);

        employeeTable.setItems(mainController.getEmployeeList().getObject());

        mainController.addEmployee("Hans", "Wrust", "leggi", "laggi872637");
        employeeTable.getSelectionModel().setCellSelectionEnabled(true);


    }

    @FXML
    private void test(ActionEvent event)throws IOException {

        Person p = (Person)employeeTable.getSelectionModel().getSelectedItem();
        System.out.println(p.getUsername());
    }

}
