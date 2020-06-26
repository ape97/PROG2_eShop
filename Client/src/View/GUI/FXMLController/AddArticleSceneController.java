package View.GUI.FXMLController;


import Communication.ClientController;
import Utilities.Message;
import Utilities.Parse;
import Utilities.Result;
import View.GUI.MainSceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;


import java.io.IOException;

public class AddArticleSceneController {

    @FXML
    private TextField textField_price;
    @FXML
    private TextField textField_name;
    @FXML
    private TextField textField_stock;
    @FXML
    private TextField textField_packagingUnit;

    @FXML
    private void button_add_clicked(ActionEvent event) throws IOException {
        String title;
        String header;
        String message;

        String name = textField_name.getText();
        String price = textField_price.getText();
        String stock = textField_stock.getText();
        String unit = textField_packagingUnit.getText();

        if (name.trim().isEmpty() || price.trim().isEmpty() || stock.trim().isEmpty() || unit.trim().isEmpty()) {
            title = Message.get(Message.MessageType.Error);
            header = Message.get(Message.MessageType.Error);
            message = Message.get(Message.MessageType.Error_FillAllField);
            MainSceneController.showMessageBox(Alert.AlertType.ERROR, title, header, message);
        } else {


            Result<Void> result = ClientController.getInstance().addArticle(name, stock, price, unit);

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
        MainSceneController.showEmployeeScene(this, event);// TODO
    }
}
