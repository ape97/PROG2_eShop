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

public class EditArticleSceneController {

    @FXML
    private TextField textField_price;
    @FXML
    private TextField textField_name;
    @FXML
    private TextField textField_stock;
    @FXML
    private TextField textField_packagingUnit;

    @FXML
    private void button_edit_clicked(ActionEvent event) throws IOException {
        MainSceneController.showEmployeeScene(this, event);// TODO
    }

    @FXML
    private void button_cancel_clicked(ActionEvent event) throws IOException {
        MainSceneController.showEmployeeScene(this, event);// TODO
    }
}
