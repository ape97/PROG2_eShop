package View.GUI;

import Utilities.JavaFXExtension;
import Utilities.Message;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class MainSceneController {
    private MainSceneController() {
    }//static

    public static void showEmployeeScene(Object caller, ActionEvent event) throws IOException {
        Stage stage = JavaFXExtension.getStageByActionEvent(event);
        JavaFXExtension.showScene(caller, "EmployeeScene.fxml", stage);
    }

    public static void showCustomerScene(Object caller, ActionEvent event) throws IOException {
        Stage stage = JavaFXExtension.getStageByActionEvent(event);
        JavaFXExtension.showScene(caller, "CustomerScene.fxml", stage);
    }

    public static void showRegisterCustomerScene(Object caller, ActionEvent event) throws IOException {
        Stage stage = JavaFXExtension.getStageByActionEvent(event);
        JavaFXExtension.showScene(caller, "RegisterCustomerScene.fxml", stage);
    }

    public static void showLoginScene(Object caller, ActionEvent event) throws IOException{
        Stage stage = JavaFXExtension.getStageByActionEvent(event);
        JavaFXExtension.showScene(caller, "LoginScene.fxml", stage);
    }

    public static void showMessageBox(Alert.AlertType alertType, String title, String header, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
