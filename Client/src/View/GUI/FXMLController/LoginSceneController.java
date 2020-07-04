package View.GUI.FXMLController;

import Communication.ClientController;
import Utilities.Message;
import Utilities.PersonType;
import Utilities.Result;
import View.GUI.MainSceneController;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;

/**
 * Der Controller für die LoginScene.fxml.
 * Ermöglicht das Anmelden/Einloggen einer Registrierten Person (Kunde/Mitarbeiter).
 */
public class LoginSceneController {

    @FXML
    private TextField textField_username; // Eingabefeld Benutzername
    @FXML
    private TextField textField_password; // Eingabefeld Kennwort

    @FXML
    /**
     * Wird der login-Button (Anmelden) geklickt, werden die Benutzereingaben an den ClientController weitergeleitet.
     * Der Erfolg der Aktion wird hier ausgwertet und dem Benutzer angezeigt.
     */
    private void button_login_clicked(ActionEvent event) {

        // Ließt die Eingaben des Benutzers als Strings ein
        String username = textField_username.getText();
        String password = textField_password.getText();

        // Ruft auf dem ClientController die login()-Methode auf, diese wird an den Server weiter gereicht
        // In diesem Fall gibt der Server in dem Result den PersonType zurück, damit differenziert werden kann,
        // ob es sich um einen Kunden oder einen Mitarbeiter handelt
        Result<PersonType> result = ClientController.getInstance().login(username, password);

        // War das Anmelden erfolgreich, wird jenachdem welcher Typ die angemeldete Person ist
        // entweder die Ansicht für Kunden oder für Mitarbeiter angezeigt
        if (result.getState() == Result.State.SUCCESSFULL) {
            if (result.getObject() == PersonType.Employee) {
                MainSceneController.showEmployeeScene(this, event);
            } else {
                MainSceneController.showCustomerScene(this, event);
            }
        } else {
            MainSceneController.showMessageBox(
                    Alert.AlertType.ERROR,
                    Message.get(Message.MessageType.Error),
                    Message.get(Message.MessageType.Error),
                    result.getMessage());
        }
    }

    @FXML
    /**
     * Öffnet eine neue Ansicht, in der sich ein Neukunde registrieren kann.
     */
    private void button_register_clicked(ActionEvent event) throws IOException {
        MainSceneController.showRegisterCustomerScene(this, event);
    }
}
