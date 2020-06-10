package View.GUI.FXMLController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class EmployeeSceneController {

    public static Stage window;

    @FXML
    private void button_addArticle(ActionEvent event) throws IOException {

        Parent view2 = FXMLLoader.load(getClass().getClassLoader().getResource("AddArticleScene.fxml"));

        Scene scene2 = new Scene(view2);

        window = new Stage();
        window.setScene(scene2);
        window.show();
    }

    @FXML
    private void button_editArticle(ActionEvent event) throws IOException {

        Parent view2 = FXMLLoader.load(getClass().getClassLoader().getResource("EditArticleScene.fxml"));

        Scene scene2 = new Scene(view2);

        window = new Stage();
        window.setScene(scene2);
        window.show();
    }





    @FXML
    private void button_addEmployee(ActionEvent event) throws IOException {

        Parent view2 = FXMLLoader.load(getClass().getClassLoader().getResource("RegisterEmployeeScene.fxml"));

        Scene scene2 = new Scene(view2);

        window = new Stage();
        window.setScene(scene2);
        window.show();
    }

    @FXML
    private void button_editEmployee(ActionEvent event) throws IOException {

        Parent view2 = FXMLLoader.load(getClass().getClassLoader().getResource("EditEmployeeScene.fxml"));

        Scene scene2 = new Scene(view2);

        window = new Stage();
        window.setScene(scene2);
        window.show();
    }
}