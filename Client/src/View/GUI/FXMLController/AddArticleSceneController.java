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
        String priceString = textField_price.getText();
        String stockString = textField_stock.getText();
        String unitString = textField_packagingUnit.getText();

        if (name.trim().isEmpty() || priceString.trim().isEmpty() || stockString.trim().isEmpty() || unitString.trim().isEmpty()) {
            title = Message.get(Message.MessageType.Error);
            header = Message.get(Message.MessageType.Error);
            message = Message.get(Message.MessageType.Error_FillAllField);
            MainSceneController.showMessageBox(Alert.AlertType.ERROR, title, header, message);
        } else {

            double price;
            int stock;
            int unit;

            Result<Double> priceParseResult = Parse.tryParseDouble(priceString);
            if (priceParseResult.getState() == Result.State.FAILED) {
                price = -1;
            } else {
                price = priceParseResult.getObject();
            }

            Result<Integer> stockParseResult = Parse.tryParseInt(stockString);
            if (stockParseResult.getState() == Result.State.FAILED) {
                stock = -1;
            } else {
                stock = stockParseResult.getObject();
            }

            Result<Integer> unitParseResult = Parse.tryParseInt(unitString);
            if (unitParseResult.getState() == Result.State.FAILED) {
                unit = -1;
            } else {
                unit = unitParseResult.getObject();
            }

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
