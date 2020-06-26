package View.GUI.FXMLController;


import Communication.ClientController;

import Data.DataCache;
import Model.Article;
import Model.Bill;
import Model.ShoppingCart;
import Model.ShoppingCartItem;
import Utilities.ArticleExtension;
import Utilities.Message;
import Utilities.Parse;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Set;


public class CustomerSceneController {

    @FXML
    private TextField textField_articleQuantityInCart;
    @FXML
    private TextField textField_articleQuantity;

    @FXML
    private TableView tableView_articles;
    @FXML
    private TableView tableView_shoppingCart;

    @FXML
    public void initialize() {
        initArticleTableView();
        initShoppingCartView();
    }


    @FXML
    private void button_clearShoppingCart_clicked(ActionEvent event) throws IOException {
        String title;
        String header;
        String message;

        Result<Void> result = ClientController.getInstance().clearShoppingCart();
        message = result.getMessage();

        if (result.getState() == Result.State.SUCCESSFULL) {
            title = Message.get(Message.MessageType.Info);
            header = Message.get(Message.MessageType.Info);
            MainSceneController.showMessageBox(Alert.AlertType.INFORMATION, title, header, message);
        } else {
            title = Message.get(Message.MessageType.Error);
            header = Message.get(Message.MessageType.Error);
            MainSceneController.showMessageBox(Alert.AlertType.ERROR, title, header, message);
        }

        tableView_shoppingCart.refresh();
    }

    @FXML
    private void button_articleAddToCart_clicked(ActionEvent event) throws IOException {
        String title;
        String header;
        String message;

        String quantity = textField_articleQuantity.getText();


        Object selectedItem = tableView_articles.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Article article = (Article) selectedItem;
            Result<Void> result = ClientController.getInstance().addArticleToShoppingCart(article, quantity);
            message = result.getMessage();

            if (result.getState() == Result.State.SUCCESSFULL) {
                title = Message.get(Message.MessageType.Info);
                header = Message.get(Message.MessageType.Info);
                MainSceneController.showMessageBox(Alert.AlertType.INFORMATION, title, header, message);
            } else {
                title = Message.get(Message.MessageType.Error);
                header = Message.get(Message.MessageType.Error);
                MainSceneController.showMessageBox(Alert.AlertType.ERROR, title, header, message);
            }
        }

        tableView_shoppingCart.refresh();
    }

    @FXML
    private void button_articleChangeQuantityInCart_clicked(ActionEvent event) throws IOException {
        Object selectedItem = tableView_shoppingCart.getSelectionModel().getSelectedItem();

        String title;
        String header;
        String message;

        if (selectedItem != null) {

            String quantity = textField_articleQuantityInCart.getText();



            ShoppingCartItem shoppingCartItem = (ShoppingCartItem) selectedItem;
            Result<Void> result = ClientController.getInstance().addArticleToShoppingCart(shoppingCartItem.getArticle(), quantity);
            message = result.getMessage();

            if (result.getState() == Result.State.SUCCESSFULL) {
                title = Message.get(Message.MessageType.Info);
                header = Message.get(Message.MessageType.Info);
                MainSceneController.showMessageBox(Alert.AlertType.INFORMATION, title, header, message);
            } else {
                title = Message.get(Message.MessageType.Error);
                header = Message.get(Message.MessageType.Error);
                MainSceneController.showMessageBox(Alert.AlertType.ERROR, title, header, message);
            }
        }

        tableView_shoppingCart.refresh();
    }

    @FXML
    private void button_articleRemoveFromCart_clicked(ActionEvent event) throws IOException {
        Object selectedItem = tableView_shoppingCart.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            ShoppingCartItem shoppingCartItem = (ShoppingCartItem) selectedItem;
            Result<Void> result = ClientController.getInstance().removeArticleFromShoppingCart(shoppingCartItem.getArticle());

            String title = Message.get(Message.MessageType.Info);
            String header = Message.get(Message.MessageType.Info);
            String message = result.getMessage();

            MainSceneController.showMessageBox(Alert.AlertType.INFORMATION, title, header, message);
        }

        tableView_shoppingCart.refresh();
    }


    @FXML
    private void button_logout_clicked(ActionEvent event) throws IOException {
        ClientController.getInstance().logout();
        MainSceneController.showLoginScene(this, event);
    }

    @FXML
    private void button_buy_clicked(ActionEvent event) throws IOException {
        Result<Bill> result = ClientController.getInstance().buyShoppingCart();

        String title;
        String header;
        String message;

        message = result.getMessage();

        if (result.getState() == Result.State.SUCCESSFULL) {
            title = Message.get(Message.MessageType.Info);
            header = Message.get(Message.MessageType.Bill);
            MainSceneController.showMessageBox(Alert.AlertType.INFORMATION, title, header, result.getObject().toString());
        } else {
            title = Message.get(Message.MessageType.Error);
            header = Message.get(Message.MessageType.Error);
            MainSceneController.showMessageBox(Alert.AlertType.ERROR, title, header, message);
        }

        tableView_articles.refresh();
    }



  /*  @FXML
    private void button_addArticle(ActionEvent event) throws IOException {
        MainSceneController.showAddArticleScene(this, event);
    }

    @FXML
    private void button_editArticle(ActionEvent event) throws IOException {
        // TODO
    }

    @FXML
    private void button_removeArticle(ActionEvent event) throws IOException {
        Object selectedItem = tableView_articles.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Article article = (Article) selectedItem;
            Result<Void> result = MainController.getInstance().removeArticle(article);

            String title = Message.get(Message.MessageType.Info);
            String header = Message.get(Message.MessageType.Info);
            String message = result.getMessage();

            MainSceneController.showMessageBox(Alert.AlertType.INFORMATION, title, header, message);
        }
    }


    @FXML
    private void button_addEmployee(ActionEvent event) throws IOException {
        MainSceneController.showRegisterEmployeeScene(this, event);
    }

    @FXML
    private void button_editEmployee(ActionEvent event) throws IOException {
        // TODO
    }

    @FXML
    private void button_removeEmployee_clicked(ActionEvent event) throws IOException {
        Object selectedItem = tableView_employees.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Employee employee = (Employee) selectedItem;
            Result<Void> result = MainController.getInstance().removePerson(employee);

            String title = Message.get(Message.MessageType.Info);
            String header = Message.get(Message.MessageType.Info);
            String message = result.getMessage();

            MainSceneController.showMessageBox(Alert.AlertType.INFORMATION, title, header, message);
        }
    }*/

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
    }

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
    }
}