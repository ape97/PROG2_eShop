package View.GUI.FXMLController;


import Communication.ClientController;
import Utilities.Message;
import Utilities.Result;
import View.GUI.MainSceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 * Der Controller für die AddArticleScene.fxml.
 * Ermöglicht das Hinzufügen eines neuen Artikels.
 */
public class AddArticleSceneController {
    @FXML
    private TextField textField_price; // Eingabefeld für Preis
    @FXML
    private TextField textField_name; // Eingabefeld für Artikelbezeichnung
    @FXML
    private TextField textField_stock; // Eingabefeld für Lagerbestand
    @FXML
    private TextField textField_packagingUnit; // Eingabefeld für Verpackungseinheit

    @FXML
    /**
     * Wird der add-Button (Hinzufügen) geklickt, werden die Benutzereingaben an den ClientController weitergeleitet.
     * Der Erfolg der Aktion wird hier ausgwertet und dem Benutzer angezeigt.
     */
    private void button_add_clicked(ActionEvent event) {

        // Ließt die Eingaben des Benutzers als Strings ein
        String name = textField_name.getText();
        String price = textField_price.getText();
        String stock = textField_stock.getText();
        String unit = textField_packagingUnit.getText();

        // Prüft, ob wirklich alle Daten angegeben wurden, falls nicht Info an den Benutzer
        if (name.trim().isEmpty() || price.trim().isEmpty() || stock.trim().isEmpty() || unit.trim().isEmpty()) {
            MainSceneController.showMessageBox(
                    Alert.AlertType.ERROR,
                    Message.get(Message.MessageType.Error),
                    Message.get(Message.MessageType.Error),
                    Message.get(Message.MessageType.Error_FillAllField));
        } else {
            // Ruft auf dem ClientController die addArticle()-Methode auf, diese wird an den Server weiter gereicht
            Result<Void> result = ClientController.getInstance().addArticle(name, stock, price, unit);

            // Jenachdem wie der Status der Aktion ist, wird eine Meldung angezeigt

            MainSceneController.showResultMessageBox(result);
            if(result.getState() == Result.State.SUCCESSFUL){
                MainSceneController.showEmployeeScene(this, event);
            }
        }
    }

    @FXML
    /**
     * Schließt die aktuelle Ansicht und zeigt wieder die Hauptansicht an.
     */
    private void button_cancel_clicked(ActionEvent event) {
        MainSceneController.showEmployeeScene(this, event);
    }
}
