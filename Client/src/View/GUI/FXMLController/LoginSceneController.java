package View.GUI.FXMLController;




import Communication.ClientController;

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
    private void button_login_clicked(ActionEvent event) throws IOException {
        String username = textField_username.getText();
        String password = textField_password.getText();

        Result<PersonType> result = ClientController.getInstance().login(username, password);
        System.out.println(result.getMessage());

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
    private void button_register_clicked(ActionEvent event) throws IOException {
        MainSceneController.showRegisterCustomerScene(this, event);
    }
}
