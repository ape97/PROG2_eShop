package Controller;

import Model.*;
import Utilities.*;
import javafx.collections.ObservableList;

import java.io.Serializable;

/**
 * Verbindet alle Controller miteinander.
 */
public class MainController implements Serializable {

    private PersonController _personController;
    private AddressController _addressController;
    private ArticleController _articleController;
    private EventController _eventController;
    private ShoppingCartController _shoppingCartController;
    private BillController _billController;

    // Singelton Implementierung
    private static MainController _instance;
    public static MainController getInstance(){
        if(_instance == null){
            _instance = new MainController();
        }
        return _instance;
    }
    // Relevant für das Laden der Daten
    public static  void setInstance(MainController instance){
        _instance = instance;
    }

    private MainController() {
        _personController = new PersonController();
        _addressController = new AddressController();
        _articleController = new ArticleController();
        _eventController = new EventController();
        _shoppingCartController = new ShoppingCartController();
        _billController = new BillController();
    }

    public void InitAfterSerialization(){
        _personController.InitAfterSerialization();
        _articleController.InitAfterSerialization();
        _eventController.InitAfterSerialization();
    }


    // TEST FOR FX


    // PERSON

    /**
     * Reicht den Funktionsaufruf weiter an PersonController, für weitere Informationen siehe: PersonController:addEmployee(...)
     *
     * @return Gibt das BooleanString-Objekt von PersonController.addEmployee(...) zurück.
     */
    public Result<Void> addEmployee(String firstname, String lastname, String username, String password) {
        Result<Void> result = new Result<Void>(Result.State.FAILED, Message.get(Message.MessageType.Error_NoPrivileges), null);

        if (_personController.getRegisteredPersonType() == PersonType.Employee) {
            result = _personController.addEmployee(firstname, lastname, username, password);
        }

        return result;
    }

    /**
     * Reicht den Funktionsaufruf weiter an PersonController und AdressController
     * Für weitere Informationen siehe:
     * PersonController:addCustomer(...)
     * AddressController:createAddress(...)
     * <p>
     * Erstellt mit AddressController ein Address-Objekt und gib dieses weiter an PersonController.
     */
    public Result<Void> addCustomer(String firstname, String lastname, String username, String password,
                                    String street, String houseNumber, String postCode, String city) {
        Result<Void> result = new Result<Void>(Result.State.FAILED, Message.get(Message.MessageType.Error_NoPrivileges), null);

        if (_personController.getRegisteredPersonType() == PersonType.Guest) {
            Result<Address> createAddressResult = _addressController.createAddress(street, houseNumber, postCode, city);

            if (createAddressResult.getState() != Result.State.SUCCESSFULL) {
                result.setMessage(createAddressResult.getMessage());
            } else {
                result = _personController.addCustomer(firstname, lastname, username, password, (Address) createAddressResult.getObject());
            }
        }

        return result;
    }

    public Result<Void> removePerson(Person person) {

        Result<Void> result = new Result<Void>(Result.State.FAILED, Message.get(Message.MessageType.Error_NoPrivileges), null);

        if (_personController.getRegisteredPersonType() == PersonType.Employee) {

            result = _personController.removePerson(person);
        }

        return result;
    }

    /**
     * Reicht den Funktionsaufruf weiter an PersonController
     * Für weitere Informationen siehe: PersonController:login(...)
     */
    public Result<PersonType> login(String username, String password) {
        Result<PersonType> result = new Result<PersonType>(Result.State.FAILED, Message.get(Message.MessageType.Error_NoPrivileges), null);

        result = _personController.login(username, password);

        return result;
    }

    public void logout() {
        _personController.logout();

    }


    // ARTICLE

    /**
     * Reicht den Funktionsaufruf weiter an ArticleController
     * Für weitere Informationen siehe: ArticleConctroller:getSortedArticleStringList(...)
     */
 /*   public String getSortedArticleStringList(ArticleSortMode articleSortMode) {
        return _articleController.getSortedArticlesString(articleSortMode);
    }*/

    /**
     * Reicht den Funktionsaufruf weiter an ArticleController
     * Für weitere Informationen siehe: ArticleController:addArticle(...)
     * Wenn addArticle(...) erfolgreich war, wird addEvent(...) aufgerufen,
     * damit die Bestandsveränderung protokolliert wird.
     */
    public Result<Void> addArticle(String name, int stock, double price, int packagingUnit) {

        Result<Void> result = new Result<Void>(Result.State.FAILED, Message.get(Message.MessageType.Error_NoPrivileges), null);

        if (_personController.getRegisteredPersonType() == PersonType.Employee) {

            Result<Article> addArticleResult = _articleController.addArticle(name, stock, price, packagingUnit);

            if (addArticleResult.getState() == Result.State.SUCCESSFULL) {
                addEvent(addArticleResult.getObject(), stock);
            }

            result = new Result<Void>(addArticleResult.getState(), addArticleResult.getMessage(), null);
        }

        return result;
    }


    public Result<Void> editArticle(Article article, String name, double price) {

        Result<Void> result = new Result<Void>(Result.State.FAILED, Message.get(Message.MessageType.Error_NoPrivileges), null);

        if (_personController.getRegisteredPersonType() == PersonType.Employee) {

            Result<Article> editArticleResult = _articleController.editArticle(article, name, price);

//            if (editArticleResult.getState() == Result.State.SUCCESSFULL) {
//                addEvent(editArticleResult.getObject(), 0);
//            }

            result = new Result<Void>(editArticleResult.getState(), editArticleResult.getMessage(), null);
        }

        return result;
    }

    public Result<Void> removeArticle(Article article) {

        Result<Void> result = new Result<Void>(Result.State.FAILED, Message.get(Message.MessageType.Error_NoPrivileges), null);

        if (_personController.getRegisteredPersonType() == PersonType.Employee) {

            result = _articleController.removeArticle(article);
        }

        return result;
    }


    /**
     * Reicht den Funktionsaufruf weiter an ArticleController
     * Für weitere Informationen siehe: ArticleController:updateStock(...)
     * Wenn updateStock(...) erfolgreich war, wird addEvent(...) aufgerufen,
     * damit die Bestandsveränderung protokolliert wird.
     */
    public Result<Void> updateStock(int articleNumber, int stockChangeValue) {
        Result<Void> result = new Result<Void>(Result.State.FAILED, Message.get(Message.MessageType.Error_NoPrivileges), null);

        if (_personController.getRegisteredPersonType() == PersonType.Employee) {

            Article article = _articleController.getArticleByArticleNumber(articleNumber);

            if (article == null) {
                result.setState(Result.State.FAILED);
                result.setMessage(Message.get(Message.MessageType.Error_ArticleNumberNotFound));
            } else {
                Result<Void> articleUpdateStockResult = _articleController.updateStock(article, stockChangeValue);

                if (articleUpdateStockResult.getState() == Result.State.SUCCESSFULL) {
                    addEvent(article, stockChangeValue);
                }

                result = articleUpdateStockResult;
            }
        }

        return result;
    }

    /**
     * Prüft blabla TODO
     * Fügt einen Artikel mit der Anzahl über die Artikelnummer dem Warenkorb hinzu
     *
     * @param articleNumber    Artikelnummer
     * @param numberOfArticles Anzahl
     * @return Gibt ein BooleanString-Objekt
     * boolean -> Sagt aus, ob der Artikel erfolgreich dem Warenkorb hinzugefügt wurde oder nicht
     * String -> Gibt die zugehörige (Fehler-) Meldung aus
     */
    public Result<Void> addArticleToShoppingCart(int articleNumber, int numberOfArticles) {
        Result<Void> result = new Result<Void>(Result.State.FAILED, Message.get(Message.MessageType.Error_NoPrivileges), null);

        if (_personController.getRegisteredPersonType() == PersonType.Customer) {

            Article article = _articleController.getArticleByArticleNumber(articleNumber);
            if (article == null) {
                result.setState(Result.State.FAILED);
                result.setMessage(Message.get(Message.MessageType.Error_ArticleNumberNotFound));
            } else if (!_articleController.checkArticleIsStock(article, numberOfArticles)) {
                result.setState(Result.State.FAILED);
                result.setMessage(Message.get(Message.MessageType.Error_ArticleStockNotEnough));
            } else if (numberOfArticles < 0) {
                result.setState(Result.State.FAILED);
                result.setMessage(Message.get(Message.MessageType.Error_ArticleItemNumberNotNegative));
            } else if (!_articleController.checkArticleStockMatchPackagingUnit(article, numberOfArticles)) {
                result.setState(Result.State.FAILED);
                result.setMessage(Message.get(Message.MessageType.Error_ArticleStockNotMatchPackagingUnit));
            } else {
                Customer customer = (Customer) _personController.getRegisteredPerson();
                result = _shoppingCartController.addArticle(customer.getShoppingCart(), article, numberOfArticles);
            }
        }

        return result;
    }

    /**
     * Kauft die Artikel in dem ShoppingCart-Objekt des angemeldeten Customer-Objektes
     *
     * @return Gibt ein BooleanStringObject-Objekt zurück
     * boolean -> Gibt an, ob der Kauf erfolgreich war
     * String -> Gibt die entsprechende (Fehler-) Meldung aus
     * Object -> Enthält die Rechnung als formatierten String, sofern der Kauf erfolgreich war
     */
    public Result<Bill> buyShoppingCart() {
        Result<Bill> result = new Result<Bill>(Result.State.FAILED, Message.get(Message.MessageType.Error_NoPrivileges), null);

        if (_personController.getRegisteredPersonType() == PersonType.Customer) {
            result = new Result<Bill>(Result.State.SUCCESSFULL, Message.get(Message.MessageType.Info_OrderSuccess), null);
            Customer customer = (Customer) _personController.getRegisteredPerson();
            ShoppingCart shoppingCart = customer.getShoppingCart();
            Bill bill = _billController.createBill(customer);

            // Prüft ob alle Artikel noch auf Lager sind
            for (Article article : shoppingCart.getArticleAndQuantityMap().keySet()) {
                boolean articleInStock = _articleController.checkArticleIsStock(article, shoppingCart.getArticleAndQuantityMap().get(article));
                if (!articleInStock) {
                    result.setState(Result.State.FAILED);
                    result.setMessage(Message.get(Message.MessageType.Error_ArticleStockCartNotEnough));
                    break;
                }
            }

            // Lagerbestand der Artikel wird angepasst, sofern der booleanStringResult Wert nicht auf false gesetzt wurde
            if (result.getState() == Result.State.SUCCESSFULL) {
                double totalPrice = 0;
                for (Article article : shoppingCart.getArticleAndQuantityMap().keySet()) {
                    // updateStock muss über ArticleController erfolgen, da die lokale Methode den LoginTypen auf Employee prüft
                    int numberOfArticles = shoppingCart.getArticleAndQuantityMap().get(article);
                    _articleController.updateStock(article, -numberOfArticles); // Achtung: Negierung der Artikelanzahl -

                    totalPrice += article.getPrice() * numberOfArticles;
                    _billController.addBillPosition(bill, article.toString(false));
                }
                bill.setTotalPrice(totalPrice);
                _shoppingCartController.clear(shoppingCart);
                result.setObject(bill);
            }
        }

        return result;
    }

    /**
     * Leert den Einkaufswagen blabla TODO
     *
     * @return Gibt ein BooleanString-Object zurück.
     * boolean -> Gibt zurück, ob das leeren erfolgreich geklappt hat.
     * String -> Gibt die entsprechende (Fehler-) Meldung aus.
     */
    public Result<Void> clearShoppingCart() {
        Result<Void> result = new Result<Void>(Result.State.FAILED, Message.get(Message.MessageType.Info_ShoppingCartClearSuccess), null);

        if (_personController.getRegisteredPersonType() == PersonType.Customer) {
            Customer customer = (Customer) _personController.getRegisteredPerson();
            _shoppingCartController.clear(customer.getShoppingCart());
        } else {
            result.setState(Result.State.FAILED);
            result.setMessage(Message.get(Message.MessageType.Error_NoPrivileges));
        }

        return result;
    }

    /**
     * Gibt den Einkaufswagen als formatierten String zurück blabla TODO
     *
     * @return Gibt ein BooleanString-Objekt zurück.
     * boolean -> Ob der Benutzer die Rechte zum anzeigen hat
     * String --> enthält die Fehlermeldung oder das ShppoingCart als String
     */
    public Result<Void> getShoppingCartString() {
        Result<Void> result = new Result<Void>(Result.State.SUCCESSFULL, "", null);

        if (_personController.getRegisteredPersonType() == PersonType.Customer) {
            Customer customer = (Customer) _personController.getRegisteredPerson();
            result.setMessage(customer.getShoppingCart().toString());
        } else {
            result.setState(Result.State.FAILED);
            result.setMessage(Message.get(Message.MessageType.Error_NoPrivileges));
        }

        return result;
    }

    /**
     * Reicht den Funktionsaufruf weiter an EventController
     * Für weitere Informationen siehe: EventController:addEvent(...)
     */
    private void addEvent(Article article, int stockChangeValue) {
        _eventController.addEvent(article, _personController.getRegisteredPerson(), stockChangeValue);
    }

    /**
     * Zeigt das Lagerprotokoll an blabla TODO
     *
     * @return Gibt ein BooleanString-Objekt zurück.
     * boolean -> Ob der Benutzer die Rechte zum anzeigen hat
     * String --> enthält die Fehlermeldung oder das Lagerprotokoll als String
     */
/*    public Result<Void> getEventsString() {
        Result<Void> result = new Result<Void>(Result.State.SUCCESSFULL, "", null);

        if (_personController.getRegisteredPersonType() == PersonType.Employee) {
            result.setMessage(_eventController.getEventList());
        } else {
            result.setState(Result.State.FAILED);
            result.setMessage(Message.get(Message.MessageType.Error_NoPrivileges));
        }

        return result;
    }*/
    public Result<ObservableList<Article>> getArticleList() {
        Result<ObservableList<Article>> result = new Result<ObservableList<Article>>(Result.State.SUCCESSFULL, "", null);

        result.setObject(_articleController.getArticleList());

        return result;
    }


    public Result<ObservableList<Customer>> getCustomerList() {
        Result<ObservableList<Customer>> result = new Result<ObservableList<Customer>>(Result.State.SUCCESSFULL, "", null);

        if (_personController.getRegisteredPersonType() == PersonType.Employee) {
            result.setObject(_personController.getCustomerList());
        } else {
            result.setState(Result.State.FAILED);
            result.setMessage(Message.get(Message.MessageType.Error_NoPrivileges));
        }

        return result;
    }


    public Result<ObservableList<Employee>> getEmployeeList() {
        Result<ObservableList<Employee>> result = new Result<ObservableList<Employee>>(Result.State.SUCCESSFULL, "", null);

        if (_personController.getRegisteredPersonType() == PersonType.Employee) {
            result.setObject(_personController.getEmployeeList());
        } else {
            result.setState(Result.State.FAILED);
            result.setMessage(Message.get(Message.MessageType.Error_NoPrivileges));
        }

        return result;
    }


    public Result<ObservableList<Event>> getEventList() {
        Result<ObservableList<Event>> result = new Result<ObservableList<Event>>(Result.State.SUCCESSFULL, "", null);

        if (_personController.getRegisteredPersonType() == PersonType.Employee) {
            result.setObject(_eventController.getEventList());
        } else {
            result.setState(Result.State.FAILED);
            result.setMessage(Message.get(Message.MessageType.Error_NoPrivileges));
        }

        return result;
    }

}
