package View.GUI.FXMLController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.Node;


public class LoginController {


    @FXML
    private void goLogin(ActionEvent event)throws IOException{

        Parent view2 = FXMLLoader.load(getClass().getClassLoader().getResource("CustomerScene.fxml"));

        Scene scene2 = new Scene(view2);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }

    @FXML
    private void goRegistrieren(ActionEvent event)throws IOException{

        Parent view2 = FXMLLoader.load(getClass().getClassLoader().getResource("Registrieren.fxml"));

        Scene scene2 = new Scene(view2);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }
}
