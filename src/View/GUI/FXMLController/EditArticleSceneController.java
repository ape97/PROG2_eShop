package View.GUI.FXMLController;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;

public class EditArticleSceneController {

    @FXML
    private void button_cancelEditArticle(ActionEvent event) throws IOException {
        if (EmployeeSceneController.window != null) {
            EmployeeSceneController.window.close();
        }
    }
}
