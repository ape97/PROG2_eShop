package View.GUI.FXMLController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class EmployeeSceneController {


    @FXML
    private void button_addEmployee(ActionEvent event) throws IOException {

        Parent view2 = FXMLLoader.load(getClass().getClassLoader().getResource("RegisterEmployee.fxml"));

        Scene scene2 = new Scene(view2);

        Stage window = new Stage();
        window.setScene(scene2);
        window.show();
    }
}