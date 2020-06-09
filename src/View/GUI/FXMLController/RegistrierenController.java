package View.GUI.FXMLController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.Node;

public class RegistrierenController {
    @FXML
    private void goRegistrierenLogin(ActionEvent event)throws IOException {

        Parent view2 = FXMLLoader.load(getClass().getClassLoader().getResource("Employee.fxml"));

        Scene scene2 = new Scene(view2);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }
}
