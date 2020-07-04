package View.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Einstiegspunkt der GUI.
 * Wird nicht als Einstiegspunkt der gesamten Anwendung verwendet!
 *
 * Hier werden die Startwerte der GUI festgelegt: z.B. Größe und mit welcher Ansicht gestartet werden soll.
 * Die Definition als Einstiegspunkt kommt durch die Vererbung, da die GUI von Application erben muss.
 */
public class MainFrame extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("LoginScene.fxml"));
        primaryStage.setTitle("eShop");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setMinHeight(350);
        primaryStage.setMinWidth(400);
        primaryStage.show();
    }
}