package View.GUI.FXMLController;


import Communication.ClientController;

import Utilities.Message;
import Utilities.Result;
import View.GUI.MainSceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Der Controller für die RegisterEmployeeScene.fxml.
 * Ermöglicht das Hinzufügen eines neuen Mitarbeiters bzw. das Registrieren.
 */
public class RegisterEmployeeSceneController {
    @FXML
    private TextField textField_firstname; // Eingabefeld Vorname
    @FXML
    private TextField textField_lastname; // Eingabefeld Nachname
    @FXML
    private TextField textField_username; // Eingabefeld Benutzername
    @FXML
    private TextField textField_password; // Eingabefeld Kennwort

    @FXML
    /**
     * Wird der add-Button (Hinzufügen) geklickt, werden die Benutzereingaben an den ClientController weitergeleitet.
     * Der Erfolg der Aktion wird hier ausgwertet und dem Benutzer angezeigt.
     */
    private void button_add_clicked(ActionEvent event) {

        // Ließt die Eingaben des Benutzers als Strings ein
        String firstname = textField_firstname.getText();
        String lastname = textField_lastname.getText();
        String username = textField_username.getText();
        String password = textField_password.getText();

        // Prüft, ob wirklich alle Daten angegeben wurden, falls nicht Info an den Benutzer
        if (firstname.trim().isEmpty() || lastname.trim().isEmpty() || username.trim().isEmpty() || password.trim().isEmpty()) {
            MainSceneController.showMessageBox(
                    Alert.AlertType.ERROR,
                    Message.get(Message.MessageType.Error),
                    Message.get(Message.MessageType.Error),
                    Message.get(Message.MessageType.Error_FillAllField));
        } else {
            // Ruft auf dem ClientController die addEmployee()-Methode auf, diese wird an den Server weiter gereicht
            Result<Void> result = ClientController.getInstance().addEmployee(firstname, lastname, username, password);

            // Jenachdem wie der Status der Aktion ist, wird eine Meldung angezeigt
            if (result.getState() == Result.State.SUCCESSFULL) {
                MainSceneController.showMessageBox(
                        Alert.AlertType.INFORMATION,
                        Message.get(Message.MessageType.Info),
                        Message.get(Message.MessageType.Info),
                        result.getMessage());

                // Zeigt wieder die Hauptansicht an
                MainSceneController.showEmployeeScene(this, event);
            } else {
                MainSceneController.showMessageBox(
                        Alert.AlertType.ERROR,
                        Message.get(Message.MessageType.Error),
                        Message.get(Message.MessageType.Error),
                        result.getMessage());
            }
        }
    }

    @FXML
    /**
     * Schließt die aktuelle Ansicht und zeigt wieder die Hauptansicht an.
     */
    private void button_cancel_clicked(ActionEvent event) throws IOException {
        MainSceneController.showEmployeeScene(this, event);
    }
}