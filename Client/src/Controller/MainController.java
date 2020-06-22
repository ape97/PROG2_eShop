package Controller;
import Model.*;
import Utilities.Message;
import Utilities.PersonType;
import Utilities.Result;
import javafx.collections.ObservableList;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;


public class MainController implements Serializable {

    // Singelton Implementierung
    private static MainController _instance;


    public static MainController getInstance() {
        if (_instance == null) {
            _instance = new MainController();
        }
        return _instance;
    }

    // Relevant für das Laden der Daten
    public static void setInstance(MainController instance) {
        _instance = instance;
    }

    private MainController() {

    }

    public Result<PersonType> login(String username, String password) {
        Result<PersonType> result = new Result<PersonType>(Result.State.FAILED, Message.get(Message.MessageType.Error_NoPrivileges), null);




        return result;
    }










    public Result<Void> addEmployee(String firstname, String lastname, String username, String password) {
        Result<Void> result = new Result<Void>(Result.State.FAILED, Message.get(Message.MessageType.Error_NoPrivileges), null);

        return result;
    }

    public Result<Void> addCustomer(String firstname, String lastname, String username, String password,
                                    String street, String houseNumber, String postCode, String city) {
        Result<Void> result = new Result<Void>(Result.State.FAILED, Message.get(Message.MessageType.Error_NoPrivileges), null);


        return result;
    }

    public Result<Void> removePerson(Person person) {

        Result<Void> result = new Result<Void>(Result.State.FAILED, Message.get(Message.MessageType.Error_NoPrivileges), null);



        return result;
    }




    public void logout() {

    }



    public Result<Void> addArticle(String name, int stock, double price, int packagingUnit) {

        Result<Void> result = new Result<Void>(Result.State.FAILED, Message.get(Message.MessageType.Error_NoPrivileges), null);


        return result;
    }


    public Result<Void> editArticle(Article article, String name, double price) {

        Result<Void> result = new Result<Void>(Result.State.FAILED, Message.get(Message.MessageType.Error_NoPrivileges), null);


        return result;
    }

    public Result<Void> removeArticle(Article article) {

        Result<Void> result = new Result<Void>(Result.State.FAILED, Message.get(Message.MessageType.Error_NoPrivileges), null);


        return result;
    }


    public Result<Void> updateStock(int articleNumber, int stockChangeValue) {
        Result<Void> result = new Result<Void>(Result.State.FAILED, Message.get(Message.MessageType.Error_NoPrivileges), null);


        return result;
    }


    public Result<Void> addArticleToShoppingCart(int articleNumber, int numberOfArticles) {
        Result<Void> result = new Result<Void>(Result.State.FAILED, Message.get(Message.MessageType.Error_NoPrivileges), null);

        return result;
    }

    public Result<Void> addArticleToShoppingCart(Article article, int numberOfArticles) {
        return addArticleToShoppingCart(article.getArticleNumber(), numberOfArticles);
    }

    public Result<Void> addArticleToShoppingCart(ShoppingCartItem shoppingCartItem, int numberOfArticles) {
        return addArticleToShoppingCart(shoppingCartItem.getArticle(), numberOfArticles);
    }

    public Result<Void> removeArticleFromShoppingCart(ShoppingCartItem shoppingCartItem) {
        Result<Void> result = new Result<Void>(Result.State.FAILED, Message.get(Message.MessageType.Error_NoPrivileges), null);


        return result;
    }


    public Result<Bill> buyShoppingCart() {
        Result<Bill> result = new Result<Bill>(Result.State.FAILED, Message.get(Message.MessageType.Error_NoPrivileges), null);

        return result;
    }

    public Result<Void> clearShoppingCart() {
        Result<Void> result = new Result<Void>(Result.State.SUCCESSFULL, Message.get(Message.MessageType.Info_ShoppingCartClearSuccess), null);


        return result;
    }

    public Result<ShoppingCart> getShoppingCart() {
        Result<ShoppingCart> result = new Result<ShoppingCart>(Result.State.SUCCESSFULL, "", null);

        return result;
    }

    private void addEvent(Article article, int stockChangeValue) {

    }

    public Result<ObservableList<Article>> getArticleList() {
        Result<ObservableList<Article>> result = new Result<ObservableList<Article>>(Result.State.SUCCESSFULL, "", null);

        return result;
    }


    public Result<ObservableList<Customer>> getCustomerList() {
        Result<ObservableList<Customer>> result = new Result<ObservableList<Customer>>(Result.State.SUCCESSFULL, "", null);

        return result;
    }


    public Result<ObservableList<Employee>> getEmployeeList() {
        Result<ObservableList<Employee>> result = new Result<ObservableList<Employee>>(Result.State.SUCCESSFULL, "", null);

        return result;
    }


    public Result<ObservableList<Event>> getEventList() {
        Result<ObservableList<Event>> result = new Result<ObservableList<Event>>(Result.State.SUCCESSFULL, "", null);

        return result;
    }

}
