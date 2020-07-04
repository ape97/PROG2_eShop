package View.GUI.FXMLController;

import Communication.ClientController;
import Data.DataCache;
import Model.Article;
import Model.Bill;
import Model.ShoppingCartItem;
import Utilities.ArticleExtension;
import Utilities.Message;
import Utilities.Result;
import View.GUI.MainSceneController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Der Controller für die CustomerScene.fxml.
 * Die GUI für Customer (Kunden), die grafische Schnittstelle zum eShop.
 */
public class CustomerSceneController {

    @FXML
    private TextField textField_articleQuantityInCart; // Eingabefeld Artikelanzahl im Warenkorb
    @FXML
    private TextField textField_articleQuantity; // Eingabefeld Artikelanzahl in der Artikelübersicht

    @FXML
    private TableView tableView_articles; // Tabelle mit den Artikeln
    @FXML
    private TableView tableView_shoppingCart; // Tabelle mit den ARtikeln im Einkaufswagen

    @FXML
    /**
     * Initialisiert die Tabellen.
     */
    public void initialize() {
        initArticleTableView();
        initShoppingCartView();
    }

    @FXML
    /**
     * Wird der clearShoppingCart-Button (Leeren) geklickt, wird die entsprechende Methode auf dem ClientController aufgerufen.
     * Der Erfolg der Aktion wird hier ausgwertet und dem Benutzer angezeigt.
     */
    private void button_clearShoppingCart_clicked(ActionEvent event) {
        // Ruft auf dem ClientController die addCustomer()-Methode auf, diese wird an den Server weiter gereicht
        Result<Void> result = ClientController.getInstance().clearShoppingCart();

        MainSceneController.showResultMessageBox(result);
        if (result.getState() == Result.State.SUCCESSFULL) {
            refreshShoppingCartItems();
        }
    }

    @FXML
    /**
     * Wird der articleAddToCart-Button (Artikel hinzufügen) geklickt, wird die entsprechende Methode auf dem ClientController aufgerufen.
     * Der Erfolg der Aktion wird hier ausgwertet und dem Benutzer angezeigt.
     */
    private void button_articleAddToCart_clicked(ActionEvent event) {

        // Ließt die Eingaben des Benutzers als Strings ein
        String quantity = textField_articleQuantity.getText();

        // Ermittelt den selektierten Artikel in der Tabelle
        Object selectedItem = tableView_articles.getSelectionModel().getSelectedItem();

        // Ist ein Artikel selektiert, wird fortgesetzt
        if (selectedItem != null) {
            Article article = (Article) selectedItem;

            // Ruft auf dem ClientController die addCustomer()-Methode auf, diese wird an den Server weiter gereicht
            Result<Void> result = ClientController.getInstance().addArticleToShoppingCart(article, quantity);

            MainSceneController.showResultMessageBox(result);
            if (result.getState() == Result.State.SUCCESSFULL) {
                refreshShoppingCartItems();
                refreshArticles();
            }
        }
    }

    @FXML
    /**
     * Wird der articleChangeQuantity-Button (Artikelanzahl ändern) geklickt, wird die entsprechende Methode auf dem ClientController aufgerufen.
     * Der Erfolg der Aktion wird hier ausgwertet und dem Benutzer angezeigt.
     */
    private void button_articleChangeQuantityInCart_clicked(ActionEvent event) throws IOException {

        // Ließt die Eingaben des Benutzers als Strings ein
        String quantity = textField_articleQuantityInCart.getText();

        // Ermittelt das selektierte ShoppingCartItem in der Tabelle
        Object selectedItem = tableView_shoppingCart.getSelectionModel().getSelectedItem();

        // Ist ein ShoppingCartItem selektiert, wird fortgesetzt
        if (selectedItem != null) {

            ShoppingCartItem shoppingCartItem = (ShoppingCartItem) selectedItem;

            // Ruft auf dem ClientController die addArticleToShoppingCart()-Methode auf, diese wird an den Server weiter gereicht
            Result<Void> result = ClientController.getInstance().addArticleToShoppingCart(shoppingCartItem.getArticle(), quantity);

            MainSceneController.showResultMessageBox(result);
            if (result.getState() == Result.State.SUCCESSFULL) {
                refreshShoppingCartItems();
            }
        }
    }

    @FXML
    /**
     * Wird der articleRemove-Button (Artikel aus Warenkorb entfernen) geklickt, wird die entsprechende Methode auf dem ClientController aufgerufen.
     * Der Erfolg der Aktion wird hier ausgwertet und dem Benutzer angezeigt.
     */
    private void button_articleRemoveFromCart_clicked(ActionEvent event) throws IOException {

        // Ermittelt das selektierte ShoppingCartItem in der Tabelle
        Object selectedItem = tableView_shoppingCart.getSelectionModel().getSelectedItem();

        // Ist ein ShoppingCartItem selektiert, wird fortgesetzt
        if (selectedItem != null) {

            ShoppingCartItem shoppingCartItem = (ShoppingCartItem) selectedItem;

            // Ruft auf dem ClientController die removeArticleFromShoppingCart()-Methode auf, diese wird an den Server weiter gereicht
            Result<Void> result = ClientController.getInstance().removeArticleFromShoppingCart(shoppingCartItem.getArticle());

            MainSceneController.showResultMessageBox(result);
            if (result.getState() == Result.State.SUCCESSFULL) {
                refreshShoppingCartItems();
            }
        }
    }


    @FXML
    /**
     * Wird der logout-Button (Abmelden) geklickt, wird die entsprechende Methode auf dem ClientController aufgerufen.
     * Der Erfolg der Aktion wird hier ausgwertet und dem Benutzer angezeigt.
     */
    private void button_logout_clicked(ActionEvent event) throws IOException {
        // Ruft auf dem ClientController die logout()-Methode auf, diese wird an den Server weiter gereicht
        ClientController.getInstance().logout();
        MainSceneController.showLoginScene(this, event);
    }

    @FXML
    /**
     * Wird der buy-Button (Kaufen) geklickt, wird die entsprechende Methode auf dem ClientController aufgerufen.
     * Der Erfolg der Aktion wird hier ausgwertet und dem Benutzer angezeigt.
     */
    private void button_buy_clicked(ActionEvent event) throws IOException {

        // Ruft auf dem ClientController die buyShoppingCart()-Methode auf, diese wird an den Server weiter gereicht
        // In diesem Fall liefert der Server bei Erfolg der AKtion ein Bill-Objekt zurück, welches die Rechnung abbildet
        Result<Bill> result = ClientController.getInstance().buyShoppingCart();

        if (result.getState() == Result.State.SUCCESSFULL) {
            // Zeigt die Rechnung inkl. Positionen an
            MainSceneController.showMessageBox(
                    Alert.AlertType.INFORMATION,
                    Message.get(Message.MessageType.Info),
                    Message.get(Message.MessageType.Info),
                    result.getObject().toString());

            refreshShoppingCartItems();
            refreshArticles();
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
     * Wird der articleRefresh-Button (Artikel Aktualisieren) geklickt, wird die entsprechende Methode auf dem ClientController aufgerufen.
     */
    private void button_articleRefresh_clicked(ActionEvent event) throws IOException {
        refreshArticles();
    }

    @FXML
    /**
     * Wird der shoppingCartRefresh-Button (Warenkorb Aktualisieren) geklickt, wird die entsprechende Methode auf dem ClientController aufgerufen.
     */
    private void button_shoppingCartRefresh_clicked(ActionEvent event) throws IOException {
        refreshShoppingCartItems();
    }

    /**
     * Ruft die entsprechende Aktualisierungs-Methode auf und zeigt bei Fehlern dem Benutzer eine Meldung.
     */
    private void refreshArticles() {
        // Ruft auf dem ClientController die refreshArticleList()-Methode auf, diese wird an den Server weiter gereicht
        Result<Void> result = DataCache.getInstance().refreshArticleList();
        MainSceneController.showResultMessageBox(result, false);
    }

    /**
     * Ruft die entsprechende Aktualisierungs-Methode auf und zeigt bei Fehlern dem Benutzer eine Meldung.
     */
    private void refreshShoppingCartItems() {
        // Ruft auf dem ClientController die refreshShoppingCartItemList()-Methode auf, diese wird an den Server weiter gereicht
        Result<Void> result = DataCache.getInstance().refreshShoppingCartItemList();
        MainSceneController.showResultMessageBox(result, false);
    }

    /**
     * Initialisiert die Artikel-Tabelle:
     * Dafür werden hier die Spaltenbezeichnungen, sowie die Eigenschaften die sie anzeigen sollen definiert.
     * Einer Spalte wird im Prinzip gesagt, welches Objekt sie zur Anzeige bekommt und welcher Getter davon
     * aufgerufen werden soll. z.B. Spaltenbezeichnung: NAME ; aufzurufender Getter: objekt.getName() ... usw.
     * Der Tabelle wird zuletzt dann die entsprechende Liste als Quelle gesetzt, dadurch
     * zeigt die Tabelle automatisch wie definiert die Daten an.
     */
    private void initArticleTableView() {
        TableColumn<Article, String> columnName = new TableColumn<>("Bezeichnung");
        columnName.setEditable(false);
        columnName.setSortable(true);
        columnName.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getName()));
        tableView_articles.getColumns().add(columnName);

        TableColumn<Article, Integer> columnArticlenumber = new TableColumn<>("Artikelnummer");
        columnArticlenumber.setEditable(false);
        columnArticlenumber.setSortable(true);
        columnArticlenumber.setCellValueFactory(e -> new SimpleObjectProperty<Integer>(e.getValue().getArticleNumber()));
        tableView_articles.getColumns().add(columnArticlenumber);

        TableColumn<Article, Integer> columnStock = new TableColumn<>("Lagerbestand");
        columnStock.setEditable(false);
        columnStock.setSortable(true);
        columnStock.setCellValueFactory(e -> new SimpleObjectProperty<Integer>(e.getValue().getStock()));
        tableView_articles.getColumns().add(columnStock);

        TableColumn<Article, Integer> columnUnit = new TableColumn<>("Verpackungseinheit");
        columnUnit.setEditable(false);
        columnUnit.setSortable(true);
        columnUnit.setCellValueFactory(e -> new SimpleObjectProperty<Integer>(ArticleExtension.getPackagingUnit(e.getValue())));
        tableView_articles.getColumns().add(columnUnit);

        TableColumn<Article, Double> columnStockChange = new TableColumn<>("Preis");
        columnStockChange.setEditable(false);
        columnStockChange.setSortable(true);
        columnStockChange.setCellValueFactory(e -> new SimpleObjectProperty<Double>(e.getValue().getPrice()));
        tableView_articles.getColumns().add(columnStockChange);

        tableView_articles.setItems(DataCache.getInstance().getArticleList());
        tableView_articles.refresh();
    }

    /**
     * Initialisiert die Warenkorb-Tabelle:
     * Dafür werden hier die Spaltenbezeichnungen, sowie die Eigenschaften die sie anzeigen sollen definiert.
     * Einer Spalte wird im Prinzip gesagt, welches Objekt sie zur Anzeige bekommt und welcher Getter davon
     * aufgerufen werden soll. z.B. Spaltenbezeichnung: NAME ; aufzurufender Getter: objekt.getName() ... usw.
     * Der Tabelle wird zuletzt dann die entsprechende Liste als Quelle gesetzt, dadurch
     * zeigt die Tabelle automatisch wie definiert die Daten an.
     */
    private void initShoppingCartView() {
        TableColumn<ShoppingCartItem, String> columnArticle = new TableColumn<>("Artikel");
        columnArticle.setEditable(false);
        columnArticle.setSortable(true);
        columnArticle.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getArticle().getName()));
        tableView_shoppingCart.getColumns().add(columnArticle);

        TableColumn<ShoppingCartItem, Integer> columnArticlenumber = new TableColumn<>("Artikelnummer");
        columnArticlenumber.setEditable(false);
        columnArticlenumber.setSortable(true);
        columnArticlenumber.setCellValueFactory(e -> new SimpleObjectProperty<Integer>(e.getValue().getArticle().getArticleNumber()));
        tableView_shoppingCart.getColumns().add(columnArticlenumber);

        TableColumn<ShoppingCartItem, Double> columnArticlePrice = new TableColumn<>("Preis");
        columnArticlePrice.setEditable(false);
        columnArticlePrice.setSortable(true);
        columnArticlePrice.setCellValueFactory(e -> new SimpleObjectProperty<Double>(e.getValue().getArticle().getPrice()));
        tableView_shoppingCart.getColumns().add(columnArticlePrice);

        TableColumn<ShoppingCartItem, Integer> columnArticleQuantity = new TableColumn<>("Anzahl");
        columnArticleQuantity.setEditable(false);
        columnArticleQuantity.setSortable(true);
        columnArticleQuantity.setCellValueFactory(e -> new SimpleObjectProperty<Integer>(e.getValue().getQuantity()));
        tableView_shoppingCart.getColumns().add(columnArticleQuantity);

        tableView_shoppingCart.setItems(DataCache.getInstance().getShoppingCartItemList());
        tableView_shoppingCart.refresh();
    }
}