package View.GUI.FXMLController;

import Controller.MainController;

import Utilities.JavaFXExtension;
import Utilities.Message;
import Utilities.PersonType;
import Utilities.Result;

import View.GUI.MainSceneController;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class LoginSceneController {

    @FXML
    private TextField textField_username;
    @FXML
    private TextField textField_password;

    @FXML
    private void button_login(ActionEvent event) throws IOException {
        String username = textField_username.getText();
        String password = textField_password.getText();

        Result<PersonType> result = MainController.getInstance().login(username, password);

        if (result.getState() == Result.State.SUCCESSFULL) {
            if (result.getObject() == PersonType.Employee) {
                MainSceneController.showEmployeeScene(this, event);
            } else {
                MainSceneController.showCustomerScene(this, event);
            }
        } else {
            String title = Message.get(Message.MessageType.Error);
            String header = Message.get(Message.MessageType.Error);
            String message = result.getMessage();

            MainSceneController.showMessageBox(Alert.AlertType.ERROR, title, header, message);
        }
    }

    @FXML
    private void button_customerRegister(ActionEvent event) throws IOException {
        MainSceneController.showRegisterCustomerScene(this, event);
    }
}
