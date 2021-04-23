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
 * Der Controller für die RegisterCustomerScene.fxml.
 * Ermöglicht das Hinzufügen eines neuen Kunden bzw. das Registrieren.
 */
public class RegisterCustomerSceneController {
    @FXML
    private TextField textField_lastname; // Eingabefeld Nachname
    @FXML
    private TextField textField_firstname; // Eingabefeld Vorname
    @FXML
    private TextField textField_street; // Eingabefeld Straße
    @FXML
    private TextField textField_housenumber; // Eingabefeld Hausnummer
    @FXML
    private TextField textField_postcode; // Eingabefeld Postleitzahl
    @FXML
    private TextField textField_city; // Eingabefeld Stadt/Ort
    @FXML
    private TextField textField_username; // Eingabefeld Benutzername
    @FXML
    private TextField textField_password; // Eingabefeld Kennwort

    @FXML
    /**
     * Wird der register-Button (Registrieren) geklickt, werden die Benutzereingaben an den ClientController weitergeleitet.
     * Der Erfolg der Aktion wird hier ausgwertet und dem Benutzer angezeigt.
     */
    private void button_register_clicked(ActionEvent event) {

        // Ließt die Eingaben des Benutzers als Strings ein
        String firstname = textField_firstname.getText();
        String lastname = textField_lastname.getText();
        String street = textField_street.getText();
        String housenumber = textField_housenumber.getText();
        String postcode = textField_postcode.getText();
        String city = textField_city.getText();
        String username = textField_username.getText();
        String password = textField_password.getText();

        // Prüft, ob wirklich alle Daten angegeben wurden, falls nicht Info an den Benutzer
        if (firstname.trim().isEmpty() || lastname.trim().isEmpty() || street.trim().isEmpty() || housenumber.trim().isEmpty() ||
                postcode.trim().isEmpty() || city.trim().isEmpty() || username.trim().isEmpty() || password.trim().isEmpty()) {

            MainSceneController.showMessageBox(
                    Alert.AlertType.ERROR,
                    Message.get(Message.MessageType.Error),
                    Message.get(Message.MessageType.Error),
                    Message.get(Message.MessageType.Error_FillAllField));
        } else {
            // Ruft auf dem ClientController die addCustomer()-Methode auf, diese wird an den Server weiter gereicht
            Result<Void> result = ClientController.getInstance().addCustomer(
                    firstname, lastname, username, password,
                    street, housenumber, postcode, city);

            // Jenachdem wie der Status der Aktion ist, wird eine Meldung angezeigt
            MainSceneController.showResultMessageBox(result);

            if (result.getState() == Result.State.SUCCESSFUL) {
                MainSceneController.showLoginScene(this, event);
            }
        }
    }

    @FXML
    /**
     * Schließt die aktuelle Ansicht und zeigt wieder die Hauptansicht an.
     */
    private void button_cancel_clicked(ActionEvent event) throws IOException {
        MainSceneController.showLoginScene(this, event);
    }
}
