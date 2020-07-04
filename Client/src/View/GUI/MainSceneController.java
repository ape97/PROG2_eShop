package View.GUI;

import Utilities.JavaFXExtension;
import Utilities.Message;
import Utilities.Result;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * Diese statische Klasse ist eine Sammlung von nützlichen Funktionen im Bereich der GUI.
 */
public class MainSceneController {
    private MainSceneController() {
    }// private, weil static

    /**
     * Zeigt die Mitarbeiter GUI an.
     *
     * @param caller Der Aufrufer: i.d.R. erfolgt der Aufruf von einem Controller
     * @param event  Das ActionEvent, welches i.d.R. durch ein Click-Event erzeugt wird
     */
    public static void showEmployeeScene(Object caller, ActionEvent event) {
        Stage stage = JavaFXExtension.getStageByActionEvent(event);
        JavaFXExtension.showScene(caller, "EmployeeScene.fxml", stage);
    }

    /**
     * Zeigt die Kunden GUI an.
     *
     * @param caller Der Aufrufer: i.d.R. erfolgt der Aufruf von einem Controller
     * @param event  Das ActionEvent, welches i.d.R. durch ein Click-Event erzeugt wird
     */
    public static void showCustomerScene(Object caller, ActionEvent event) {
        Stage stage = JavaFXExtension.getStageByActionEvent(event);
        JavaFXExtension.showScene(caller, "CustomerScene.fxml", stage);
    }

    /**
     * Zeigt die Kunden-Registrierungs GUI an.
     *
     * @param caller Der Aufrufer: i.d.R. erfolgt der Aufruf von einem Controller
     * @param event  Das ActionEvent, welches i.d.R. durch ein Click-Event erzeugt wird
     */
    public static void showRegisterCustomerScene(Object caller, ActionEvent event) {
        Stage stage = JavaFXExtension.getStageByActionEvent(event);
        JavaFXExtension.showScene(caller, "RegisterCustomerScene.fxml", stage);
    }

    /**
     * Zeigt die Login GUI an.
     *
     * @param caller Der Aufrufer: i.d.R. erfolgt der Aufruf von einem Controller
     * @param event  Das ActionEvent, welches i.d.R. durch ein Click-Event erzeugt wird
     */
    public static void showLoginScene(Object caller, ActionEvent event) {
        Stage stage = JavaFXExtension.getStageByActionEvent(event);
        JavaFXExtension.showScene(caller, "LoginScene.fxml", stage);
    }

    /**
     * Zeigt die Neuer Artikel GUI an.
     *
     * @param caller Der Aufrufer: i.d.R. erfolgt der Aufruf von einem Controller
     * @param event  Das ActionEvent, welches i.d.R. durch ein Click-Event erzeugt wird
     */
    public static void showAddArticleScene(Object caller, ActionEvent event) {
        Stage stage = JavaFXExtension.getStageByActionEvent(event);
        JavaFXExtension.showScene(caller, "AddArticleScene.fxml", stage);
    }

    /**
     * Zeigt die Mitarbeiter-Registrierungs GUI an.
     *
     * @param caller Der Aufrufer: i.d.R. erfolgt der Aufruf von einem Controller
     * @param event  Das ActionEvent, welches i.d.R. durch ein Click-Event erzeugt wird
     */
    public static void showRegisterEmployeeScene(Object caller, ActionEvent event) {
        Stage stage = JavaFXExtension.getStageByActionEvent(event);
        JavaFXExtension.showScene(caller, "RegisterEmployeeScene.fxml", stage);
    }

    /**
     * Zeigt eine MessageBox an.
     *
     * @param alertType Gibt den Typen der Meldung an (Fehler/Info)
     * @param title     Titel des Fensters
     * @param header    Überschrift
     * @param message   Nachricht für den Benutzer
     */
    public static void showMessageBox(Alert.AlertType alertType, String title, String header, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Zeigt eine MessageBox basierend auf einem Result-Objekt an.
     * Damit alle Result-Objekte unterstützt werden, wurde hier mit
     * dem generischen Platzhalter <T> gearbeitet.
     *
     * @param result Das auszuwertende und anzuzeigende Result
     * @param <T>    Der generische Typ, welcher im Result unterschiedlich sein kann.
     */
    public static <T> void showResultMessageBox(Result<T> result) {
        showResultMessageBox(result, true);
    }

    /**
     * Erweitert showResultMessageBox()
     * <p>
     * Zeigt eine MessageBox basierend auf einem Result-Objekt an.
     * Damit alle Result-Objekte unterstützt werden, wurde hier mit
     * dem generischen Platzhalter <T> gearbeitet.
     *
     * @param result      Das auszuwertende und anzuzeigende Result
     * @param showSuccess Sollen auch positive Meldungen angzeigt werden, bei false weren nur negative angezeigt
     * @param <T>         Der generische Typ, welcher im Result unterschiedlich sein kann.
     */
    public static <T> void showResultMessageBox(Result<T> result, boolean showSuccess) {
        if (result.getState() == Result.State.SUCCESSFULL) {

            if (showSuccess) {
                MainSceneController.showMessageBox(
                        Alert.AlertType.INFORMATION,
                        Message.get(Message.MessageType.Info),
                        Message.get(Message.MessageType.Info),
                        result.getMessage());
            }
        } else {
            MainSceneController.showMessageBox(
                    Alert.AlertType.ERROR,
                    Message.get(Message.MessageType.Error),
                    Message.get(Message.MessageType.Error),
                    result.getMessage());
        }
    }
}
