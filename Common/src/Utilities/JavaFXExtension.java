package Utilities;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Eine Sammlung an nützlichen Funktionen für JavaFX.
 * [Anmerkung] Befindet sich in Common, da der Server später theoretisch ebenfalls eine GUI verwenden könnte.
 */
public class JavaFXExtension {

    private JavaFXExtension() { //  private, weil statisch
    }

    /**
     * Zeigt eine beliebige Scene auf der übergebenden Stage an.
     * @param caller Der Aufrufer
     * @param sceneName Der Name der Scene in den Ressourcen
     * @param window Die Stage bzw. das Fenster auf dem die Scene angezeigt werden soll
     */
    public static void showScene(Object caller, String sceneName, Stage window) {
        try {
            Parent view = FXMLLoader.load(caller.getClass().getClassLoader().getResource(sceneName));
            Scene scene = new Scene(view);

            window.setScene(scene);
            window.show();
        } catch (IOException ex) {
            // Sollte dieser Fehler auftreten, liegt ein Fehler im Code vor, kann nicht durch Benutzer verursacht werden
            // daher wird der Fehler auch nicht weiter behandelt.
            System.out.print(ex.getMessage());
        }
    }

    /**
     * Ermittelt die Stage von einem ActionEvent und gibt dieses zurück.
     * @param event
     * @return Die ermittelte Stage
     */
    public static Stage getStageByActionEvent(ActionEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        return window;
    }
}
