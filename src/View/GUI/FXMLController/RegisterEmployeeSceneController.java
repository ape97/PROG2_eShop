package View.GUI.FXMLController;


import Controller.MainController;
import Utilities.Message;
import Utilities.Parse;
import Utilities.Result;
import View.GUI.MainSceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegisterEmployeeSceneController {

    @FXML
    private TextField textField_firstname;
    @FXML
    private TextField textField_lastname;
    @FXML
    private TextField textField_username;
    @FXML
    private TextField textField_password;

    @FXML
    private void button_add_clicked(ActionEvent event) throws IOException {
        String title;
        String header;
        String message;

        String firstname = textField_firstname.getText();
        String lastname = textField_lastname.getText();
        String username = textField_username.getText();
        String password = textField_password.getText();

        if (firstname.trim().isEmpty() || lastname.trim().isEmpty() || username.trim().isEmpty() || password.trim().isEmpty()) {
            title = Message.get(Message.MessageType.Error);
            header = Message.get(Message.MessageType.Error);
            message = Message.get(Message.MessageType.Error_FillAllField);
            MainSceneController.showMessageBox(Alert.AlertType.ERROR, title, header, message);
        } else {

            Result<Void> result = MainController.getInstance().addEmployee(firstname, lastname, username, password);

            message = result.getMessage();

            if (result.getState() == Result.State.SUCCESSFULL) {
                title = Message.get(Message.MessageType.Info);
                header = Message.get(Message.MessageType.Info);
                MainSceneController.showMessageBox(Alert.AlertType.INFORMATION, title, header, message);
                MainSceneController.showEmployeeScene(this, event); // TODO
            } else {
                title = Message.get(Message.MessageType.Error);
                header = Message.get(Message.MessageType.Error);
                MainSceneController.showMessageBox(Alert.AlertType.ERROR, title, header, message);
            }
        }
    }

    @FXML
    private void button_cancel_clicked(ActionEvent event) throws IOException {
        MainSceneController.showEmployeeScene(this, event); //TODO
    }
}