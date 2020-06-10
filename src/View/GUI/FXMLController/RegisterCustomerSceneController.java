package View.GUI.FXMLController;

import View.GUI.MainSceneController;
import Controller.MainController;
import Utilities.Message;
import Utilities.Result;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegisterCustomerSceneController {

    @FXML
    private TextField textField_lastname;
    @FXML
    private TextField textField_firstname;
    @FXML
    private TextField textField_street;
    @FXML
    private TextField textField_housenumber;
    @FXML
    private TextField textField_postcode;
    @FXML
    private TextField textField_city;
    @FXML
    private TextField textField_username;
    @FXML
    private TextField textField_password;

    @FXML
    private void button_register_clicked(ActionEvent event) throws IOException {

        Parent view2 = FXMLLoader.load(getClass().getClassLoader().getResource("EmployeeScene.fxml"));

        String firstname = textField_firstname.getText();
        String lastname = textField_lastname.getText();
        String street = textField_street.getText();
        String housenumber = textField_housenumber.getText();
        String postcode = textField_postcode.getText();
        String city = textField_city.getText();
        String username = textField_username.getText();
        String password = textField_password.getText();

        if (firstname.trim().isEmpty() || lastname.trim().isEmpty() || street.trim().isEmpty() || housenumber.trim().isEmpty() ||
                postcode.trim().isEmpty() || city.trim().isEmpty() || username.trim().isEmpty() || password.trim().isEmpty()) {

            String title = Message.get(Message.MessageType.Error);
            String header = Message.get(Message.MessageType.Error);
            String message = Message.get(Message.MessageType.Error_FillAllField);
            MainSceneController.showMessageBox(Alert.AlertType.ERROR, title, header, message);
        } else {
            Result<Void> result = MainController.getInstance().addCustomer(
                    firstname, lastname, username, password,
                    street, housenumber, postcode, city);

            String title;
            String header;
            String message = result.getMessage();

            if (result.getState() == Result.State.SUCCESSFULL) {
                title = Message.get(Message.MessageType.Info);
                header = Message.get(Message.MessageType.Info);
                MainSceneController.showMessageBox(Alert.AlertType.INFORMATION, title, header, message);
                MainSceneController.showLoginScene(this, event);
            } else {
                title = Message.get(Message.MessageType.Error);
                header = Message.get(Message.MessageType.Error);
                MainSceneController.showMessageBox(Alert.AlertType.ERROR, title, header, message);
            }
        }
    }

    @FXML
    private void button_cancel_clicked(ActionEvent event) throws IOException {
        MainSceneController.showLoginScene(this, event);
    }
}
