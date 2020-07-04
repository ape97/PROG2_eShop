package View.GUI.FXMLController;

import Communication.ClientController;
import Data.DataCache;
import Model.Article;
import Model.Employee;
import Model.Event;
import Utilities.ArticleExtension;
import Utilities.Result;
import View.GUI.MainSceneController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.util.Date;

/**
 * Der Controller für die EmployeeScene.fxml.
 * Die GUI für Employee (Mitarbeiter), die grafische Schnittstelle zum eShop.
 */
public class EmployeeSceneController {

    @FXML
    private TableView tableView_events; // Tabelle mit den Events (Änderungsprotokoll)
    @FXML
    private TableView tableView_articles; // Tabelle mit den Artikeln
    @FXML
    private TableView tableView_employees; // Tabelle mit den Mitarbeietrn

    @FXML
    public void initialize() {
        initEventTableView();
        initArticleTableView();
        initEmployeeTableView();
    }

    @FXML
    /**
     * Öffnet eine neue Ansicht, in der ein neuer Artikel erstellt werden kann.
     */
    private void button_addArticle_clicked(ActionEvent event) {
        MainSceneController.showAddArticleScene(this, event);
        refreshArticles();
        refreshEvents();
    }

    @FXML
    /**
     * Wird der removeArticle-Button (Artikel entfernen) geklickt, wird die entsprechende Methode auf dem ClientController aufgerufen.
     * Der Erfolg der Aktion wird hier ausgwertet und dem Benutzer angezeigt.
     */
    private void button_removeArticle_clicked(ActionEvent event) {

        // Ermittelt den selektierten Artikel in der Tabelle
        Object selectedItem = tableView_articles.getSelectionModel().getSelectedItem();

        // Ist ein Artikel selektiert, wird fortgesetzt
        if (selectedItem != null) {

            Article article = (Article) selectedItem;

            // Ruft auf dem ClientController die removeArticle()-Methode auf, diese wird an den Server weiter gereicht
            Result<Void> result = ClientController.getInstance().removeArticle(article);

            MainSceneController.showResultMessageBox(result);
            if (result.getState() == Result.State.SUCCESSFULL) {
                refreshArticles();
                refreshEvents();
            }
        }
    }

    @FXML
    /**
     * Öffnet eine neue Ansicht, in der ein neuer Mitarbeiter erstellt werden kann.
     */
    private void button_addEmployee_clicked(ActionEvent event) {
        MainSceneController.showRegisterEmployeeScene(this, event);
        refreshEmployees();
    }

    @FXML
    /**
     * Wird der removeArticle-Button (Artikel entfernen) geklickt, wird die entsprechende Methode auf dem ClientController aufgerufen.
     * Der Erfolg der Aktion wird hier ausgwertet und dem Benutzer angezeigt.
     */
    private void button_removeEmployee_clicked(ActionEvent event) {

        // Ermittelt den selektierten Mitarbeiter in der Tabelle
        Object selectedItem = tableView_employees.getSelectionModel().getSelectedItem();

        // Ist ein Mitarbeiter selektiert, wird fortgesetzt
        if (selectedItem != null) {

            Employee employee = (Employee) selectedItem;

            // Ruft auf dem ClientController die removePerson()-Methode auf, diese wird an den Server weiter gereicht
            Result<Void> result = ClientController.getInstance().removePerson(employee);

            MainSceneController.showResultMessageBox(result);
            if (result.getState() == Result.State.SUCCESSFULL) {
                refreshEmployees();
            }
        }
    }

    @FXML
    /**
     * Wird der logout-Button (Abmelden) geklickt, wird die entsprechende Methode auf dem ClientController aufgerufen.
     * Der Erfolg der Aktion wird hier ausgwertet und dem Benutzer angezeigt.
     */
    private void button_logout_clicked(ActionEvent event) {
        // Ruft auf dem ClientController die logout()-Methode auf, diese wird an den Server weiter gereicht
        ClientController.getInstance().logout();
        MainSceneController.showLoginScene(this, event);
    }

    @FXML
    /**
     * Wird der eventRefresh-Button (Events Aktualisieren) geklickt, wird die entsprechende Methode auf dem ClientController aufgerufen.
     */
    private void button_eventRefresh_clicked(ActionEvent event) {
        refreshEvents();
    }

    @FXML
    /**
     * Wird der articleRefresh-Button (Artikel Aktualisieren) geklickt, wird die entsprechende Methode auf dem ClientController aufgerufen.
     */
    private void button_articleRefresh_clicked(ActionEvent event) {
        refreshArticles();
    }

    @FXML
    /**
     * Wird der employeeRefresh-Button (Mitarbeiter Aktualisieren) geklickt, wird die entsprechende Methode auf dem ClientController aufgerufen.
     */
    private void button_employeeRefresh_clicked(ActionEvent event) {
        refreshEmployees();
    }

    /**
     * Ruft die entsprechende Aktualisierungs-Methode auf und zeigt bei Fehlern dem Benutzer eine Meldung.
     */
    private void refreshEmployees() {
        // Ruft auf dem ClientController die refreshEmployeeList()-Methode auf, diese wird an den Server weiter gereicht
        Result<Void> result = DataCache.getInstance().refreshEmployeeList();
        MainSceneController.showResultMessageBox(result, false);
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
    private void refreshEvents() {
        // Ruft auf dem ClientController die refreshEventList()-Methode auf, diese wird an den Server weiter gereicht
        Result<Void> result = DataCache.getInstance().refreshEventList();
        MainSceneController.showResultMessageBox(result, false);
    }

    /**
     * Initialisiert die Mitarbeiter-Tabelle:
     * Dafür werden hier die Spaltenbezeichnungen, sowie die Eigenschaften die sie anzeigen sollen definiert.
     * Einer Spalte wird im Prinzip gesagt, welches Objekt sie zur Anzeige bekommt und welcher Getter davon
     * aufgerufen werden soll. z.B. Spaltenbezeichnung: NAME ; aufzurufender Getter: objekt.getName() ... usw.
     * Der Tabelle wird zuletzt dann die entsprechende Liste als Quelle gesetzt, dadurch
     * zeigt die Tabelle automatisch wie definiert die Daten an.
     */
    private void initEmployeeTableView() {
        TableColumn<Employee, Integer> columnEmployeeId = new TableColumn<>("ID");
        columnEmployeeId.setEditable(false);
        columnEmployeeId.setSortable(true);
        columnEmployeeId.setCellValueFactory(e -> new SimpleObjectProperty<Integer>(e.getValue().getId()));
        tableView_employees.getColumns().add(columnEmployeeId);

        TableColumn<Employee, String> columnEmployeeFirstname = new TableColumn<>("Vorname");
        columnEmployeeFirstname.setEditable(false);
        columnEmployeeFirstname.setSortable(true);
        columnEmployeeFirstname.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getFirstname()));
        tableView_employees.getColumns().add(columnEmployeeFirstname);

        TableColumn<Employee, String> columnEmployeeLastname = new TableColumn<>("Nachname");
        columnEmployeeLastname.setEditable(false);
        columnEmployeeLastname.setSortable(true);
        columnEmployeeLastname.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getLastname()));
        tableView_employees.getColumns().add(columnEmployeeLastname);

        TableColumn<Employee, String> columnEmployeeUsername = new TableColumn<>("Benutzername");
        columnEmployeeUsername.setEditable(false);
        columnEmployeeUsername.setSortable(true);
        columnEmployeeUsername.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getUsername()));
        tableView_employees.getColumns().add(columnEmployeeUsername);

        TableColumn<Employee, String> columnEmployeePassword = new TableColumn<>("Kennwort");
        columnEmployeePassword.setEditable(false);
        columnEmployeePassword.setSortable(true);
        columnEmployeePassword.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getPassword()));
        tableView_employees.getColumns().add(columnEmployeePassword);

        tableView_employees.setItems(DataCache.getInstance().getEmployeeList());
        tableView_employees.refresh();
    }

    /**
     * Initialisiert die Event-Tabelle:
     * Dafür werden hier die Spaltenbezeichnungen, sowie die Eigenschaften die sie anzeigen sollen definiert.
     * Einer Spalte wird im Prinzip gesagt, welches Objekt sie zur Anzeige bekommt und welcher Getter davon
     * aufgerufen werden soll. z.B. Spaltenbezeichnung: NAME ; aufzurufender Getter: objekt.getName() ... usw.
     * Der Tabelle wird zuletzt dann die entsprechende Liste als Quelle gesetzt, dadurch
     * zeigt die Tabelle automatisch wie definiert die Daten an.
     */
    private void initEventTableView() {
        TableColumn<Event, Date> columnTimestamp = new TableColumn<>("Zeitstempel");
        columnTimestamp.setEditable(false);
        columnTimestamp.setSortable(true);
        columnTimestamp.setCellValueFactory(e -> new SimpleObjectProperty<Date>(e.getValue().getTimeStamp()));
        tableView_events.getColumns().add(columnTimestamp);

        TableColumn<Event, String> columnArticle = new TableColumn<>("Artikel");
        columnArticle.setEditable(false);
        columnArticle.setSortable(true);
        columnArticle.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getArticleName()));
        tableView_events.getColumns().add(columnArticle);

        TableColumn<Event, Integer> columnArticlenumber = new TableColumn<>("Artikelnummer");
        columnArticlenumber.setEditable(false);
        columnArticlenumber.setSortable(true);
        columnArticlenumber.setCellValueFactory(e -> new SimpleObjectProperty<Integer>(e.getValue().getArticleNumber()));
        tableView_events.getColumns().add(columnArticlenumber);

        TableColumn<Event, Integer> columnStockChange = new TableColumn<>("Bestandsänderung");
        columnStockChange.setEditable(false);
        columnStockChange.setSortable(true);
        columnStockChange.setCellValueFactory(e -> new SimpleObjectProperty<Integer>(e.getValue().getStockChangeValue()));
        tableView_events.getColumns().add(columnStockChange);

        TableColumn<Event, Integer> columnPersonId = new TableColumn<>("Person ID");
        columnPersonId.setEditable(false);
        columnPersonId.setSortable(true);
        columnPersonId.setCellValueFactory(e -> new SimpleObjectProperty<Integer>(e.getValue().getPersonId()));
        tableView_events.getColumns().add(columnPersonId);

        TableColumn<Event, String> columnFirstname = new TableColumn<>("Vorname");
        columnFirstname.setEditable(false);
        columnFirstname.setSortable(true);
        columnFirstname.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getPersonFirstname()));
        tableView_events.getColumns().add(columnFirstname);

        TableColumn<Event, String> columnLastname = new TableColumn<>("Nachname");
        columnLastname.setEditable(false);
        columnLastname.setSortable(true);
        columnLastname.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getPersonLastname()));
        tableView_events.getColumns().add(columnLastname);

        tableView_events.setItems(DataCache.getInstance().getEventList());
        tableView_events.refresh();
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
}