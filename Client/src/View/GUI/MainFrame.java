package View.GUI;

import Communication.ClientController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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