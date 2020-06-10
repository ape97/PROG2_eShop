package Utilities;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaFXExtension {

    private JavaFXExtension() {
    } // Static

    public static void showScene(Object caller, String sceneName, Stage window) throws IOException {
        Parent view = FXMLLoader.load(caller.getClass().getClassLoader().getResource(sceneName));
        Scene scene = new Scene(view);

        window.setScene(scene);
        window.show();
    }

    public static Stage getStageByActionEvent(ActionEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        return window;
    }
}
