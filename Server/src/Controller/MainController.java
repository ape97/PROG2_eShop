package Controller;

import Model.*;
import Utilities.*;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

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
    // private static MainController _instance;

//    public static MainController getInstance() {
//        if (_instance == null) {
//            _instance = new MainController();
//        }
//        return _instance;
//    }

//    // Relevant für das Laden der Daten
//    public static void setInstance(MainController instance) {
//        _instance = instance;
//    }

    public MainController() {
        _personController = new PersonController();
        _addressController = new AddressController();
        _articleController = new ArticleController();
        _eventController = new EventController();
        _shoppingCartController = new ShoppingCartController();
        _billController = new BillController();
    }

    public void InitAfterSerialization() {
//        _personController.InitAfterSerialization();
//        _articleController.InitAfterSerialization();
//        _eventController.InitAfterSerialization();
//
//        for (Customer customer : _personController.getCustomerList()) {
//            customer.getShoppingCart().initAfterSerialization();
//        }
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

    public Result<Void> removePerson(String personID) {
        if (_personController.getRegisteredPersonType() == PersonType.Employee) {
            Result<Integer> parseResult = Parse.tryParseInt(personID);
            if (parseResult.getState() == Result.State.SUCCESSFULL) {
                return _personController.removePerson(parseResult.getObject());
            } else {
                return new Result<Void>(Result.State.FAILED, "Die angegeben ID ist nicht gültig.", null);
            }
        } else {
            return new Result<Void>(Result.State.FAILED, Message.get(Message.MessageType.Error_NoPrivileges), null);
        }
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

    public Result<Void> logout() {
        Result<Void> result = new Result<>(Result.State.SUCCESSFULL, "Sie wurden ausgeloggt.", null);
        _personController.logout();
        return result;
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
    public Result<Void> addArticle(String name, String stock, String price, String packagingUnit) {
        if (_personController.getRegisteredPersonType() == PersonType.Employee) {
            Result<Article> addArticleResult = _articleController.addArticle(name, stock, price, packagingUnit);
            if (addArticleResult.getState() == Result.State.SUCCESSFULL) {
                addEvent(addArticleResult.getObject(), Parse.parseInteger(stock));
            }
            return new Result<Void>(addArticleResult.getState(), addArticleResult.getMessage(), null);
        } else {
            return new Result<Void>(Result.State.FAILED, Message.get(Message.MessageType.Error_NoPrivileges), null);
        }
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

    public Result<Void> removeArticle(String articleNumber) {
        if (_personController.getRegisteredPersonType() == PersonType.Employee) {
            Result<Article> removeArticleResult = _articleController.removeArticle(articleNumber);
            if (removeArticleResult.getState() == Result.State.SUCCESSFULL) {
                addEvent(removeArticleResult.getObject(), -removeArticleResult.getObject().getStock());
            }
            return new Result<Void>(Result.State.SUCCESSFULL, removeArticleResult.getMessage(), null);
        } else {
            return new Result<Void>(Result.State.FAILED, Message.get(Message.MessageType.Error_NoPrivileges), null);
        }
    }


    /**
     * Reicht den Funktionsaufruf weiter an ArticleController
     * Für weitere Informationen siehe: ArticleController:updateStock(...)
     * Wenn updateStock(...) erfolgreich war, wird addEvent(...) aufgerufen,
     * damit die Bestandsveränderung protokolliert wird.
     */
    public Result<Void> updateStock(String articleNumber, String stockChangeValue) {

        if (_personController.getRegisteredPersonType() != PersonType.Employee) {
            return new Result<Void>(Result.State.FAILED, Message.get(Message.MessageType.Error_NoPrivileges), null);
        }

        Result<Integer> parseArticleNumberResult = Parse.tryParseInt(articleNumber);
        if (parseArticleNumberResult.getState() == Result.State.FAILED) {
            return new Result<Void>(Result.State.FAILED, "Artikelnummer kein gültiger Ganzzahlenwert.", null);
        }

        Result<Integer> parseStockChangeValueResult = Parse.tryParseInt(stockChangeValue);
        if (parseStockChangeValueResult.getState() == Result.State.FAILED) {
            return new Result<Void>(Result.State.FAILED, "Lagerbestandsänderung kein gültiger Ganzzahlenwert.", null);
        }

        Article article = _articleController.getArticleByArticleNumber(parseArticleNumberResult.getObject());
        if (article == null) {
            return new Result<Void>(Result.State.FAILED, Message.get(Message.MessageType.Error_ArticleNumberNotFound), null);
        }

        Result<Void> articleUpdateStockResult = _articleController.updateStock(article, parseStockChangeValueResult.getObject());
        if (articleUpdateStockResult.getState() == Result.State.SUCCESSFULL) {
            addEvent(article, parseStockChangeValueResult.getObject());
        }

        return articleUpdateStockResult;
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
    public Result<Void> addArticleToShoppingCart(String articleNumber, String numberOfArticles) {

        if (_personController.getRegisteredPersonType() != PersonType.Customer) {
            return new Result<Void>(Result.State.FAILED, Message.get(Message.MessageType.Error_NoPrivileges), null);
        }

        Result<Integer> parseArticleNumberResult = Parse.tryParseInt(articleNumber);
        if (parseArticleNumberResult.getState() == Result.State.FAILED) {
            return new Result<Void>(Result.State.FAILED, "Artikelnummer ist kein gültiger Ganzzahlenwert.", null);
        }

        Result<Integer> parseNumberOfArticlesResult = Parse.tryParseInt(numberOfArticles);
        if (parseNumberOfArticlesResult.getState() == Result.State.FAILED) {
            return new Result<Void>(Result.State.FAILED, "Anzahl ist kein gültiger Ganzzahlenwert.", null);
        }

        Article article = _articleController.getArticleByArticleNumber(parseArticleNumberResult.getObject());
        if (article == null) {
            return new Result<>(Result.State.FAILED, Message.get(Message.MessageType.Error_ArticleNumberNotFound), null);
        }

        if (!_articleController.checkArticleIsStock(article, parseNumberOfArticlesResult.getObject())) {
            return new Result<>(Result.State.FAILED, Message.get(Message.MessageType.Error_ArticleStockNotEnough), null);
        }

        if (parseNumberOfArticlesResult.getObject() < 0) {
            return new Result<>(Result.State.FAILED, Message.get(Message.MessageType.Error_ArticleItemNumberNotNegative), null);
        }

        if (!_articleController.checkArticleStockMatchPackagingUnit(article, parseNumberOfArticlesResult.getObject())) {
            return new Result<>(Result.State.FAILED, Message.get(Message.MessageType.Error_ArticleStockNotMatchPackagingUnit), null);
        }

        Customer customer = (Customer) _personController.getRegisteredPerson();
        return _shoppingCartController.addShoppingCartItem(customer.getShoppingCart(), new ShoppingCartItem(article, parseNumberOfArticlesResult.getObject()));
    }

//    public Result<Void> addArticleToShoppingCart(Article article, int numberOfArticles) {
//        return addArticleToShoppingCart(article.getArticleNumber(), numberOfArticles);
//    }

//    public Result<Void> addArticleToShoppingCart(ShoppingCartItem shoppingCartItem, int numberOfArticles) {
//        return addArticleToShoppingCart(shoppingCartItem.getArticle(), numberOfArticles);
//    }

    public Result<Void> removeArticleFromShoppingCart(String articleNumber) {

        if (_personController.getRegisteredPersonType() != PersonType.Customer) {
            return new Result<Void>(Result.State.FAILED, Message.get(Message.MessageType.Error_NoPrivileges), null);
        }

        Result<Integer> parseArticleNumberResult = Parse.tryParseInt(articleNumber);
        if (parseArticleNumberResult.getState() == Result.State.FAILED) {
            return new Result<Void>(Result.State.FAILED, "Artikelnummer ist kein gültiger Ganzzhalenwert.", null);
        }

        Customer customer = (Customer) _personController.getRegisteredPerson();
        return  _shoppingCartController.removeShoppingCartItem(customer.getShoppingCart(), parseArticleNumberResult.getObject());
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
            for (ShoppingCartItem shoppingCartItem : shoppingCart.getShoppingCartItemList()) {
                boolean articleInStock = _articleController.checkArticleIsStock(shoppingCartItem.getArticle(), shoppingCartItem.getQuantity());
                if (!articleInStock) {
                    result.setState(Result.State.FAILED);
                    result.setMessage(Message.get(Message.MessageType.Error_ArticleStockCartNotEnough));
                    break;
                }
            }

            // Lagerbestand der Artikel wird angepasst, sofern der booleanStringResult Wert nicht auf false gesetzt wurde
            if (result.getState() == Result.State.SUCCESSFULL) {
                double totalPrice = 0;
                for (ShoppingCartItem shoppingCartItem : shoppingCart.getShoppingCartItemList()) {
                    // updateStock muss über ArticleController erfolgen, da die lokale Methode den LoginTypen auf Employee prüft
                    int numberOfArticles = shoppingCartItem.getQuantity();
                    _articleController.updateStock(shoppingCartItem.getArticle(), -numberOfArticles); // Achtung: Negierung der Artikelanzahl -
                    addEvent(shoppingCartItem.getArticle(), -numberOfArticles);

                    totalPrice += shoppingCartItem.getArticle().getPrice() * numberOfArticles;
                    _billController.addBillPosition(bill, shoppingCartItem.getArticle().toString(false) + " " + numberOfArticles + " stk");
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
        Result<Void> result = new Result<Void>(Result.State.SUCCESSFULL, Message.get(Message.MessageType.Info_ShoppingCartClearSuccess), null);

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
    public Result<ArrayList<ShoppingCartItem>> getShoppingCartItemList() {
        Result<ArrayList<ShoppingCartItem>> result = new Result<ArrayList<ShoppingCartItem>>(Result.State.FAILED, "", null);


        if (_personController.getRegisteredPersonType() == PersonType.Customer) {
            Customer customer = (Customer) _personController.getRegisteredPerson();
            result.setObject(customer.getShoppingCart().getShoppingCartItemList());
            result.setState(Result.State.SUCCESSFULL);
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
    public Result<ArrayList<Article>> getArticleList() {
        Result<ArrayList<Article>> result = new Result<ArrayList<Article>>(Result.State.SUCCESSFULL, "", null);

        result.setObject(_articleController.getArticleList());

        return result;
    }


    public Result<ArrayList<Customer>> getCustomerList() {
        Result<ArrayList<Customer>> result = new Result<ArrayList<Customer>>(Result.State.SUCCESSFULL, "", null);

        if (_personController.getRegisteredPersonType() == PersonType.Employee) {
            result.setObject(_personController.getCustomerList());
        } else {
            result.setState(Result.State.FAILED);
            result.setMessage(Message.get(Message.MessageType.Error_NoPrivileges));
        }

        return result;
    }


    public Result<ArrayList<Employee>> getEmployeeList() {
        Result<ArrayList<Employee>> result = new Result<ArrayList<Employee>>(Result.State.SUCCESSFULL, "", null);

        if (_personController.getRegisteredPersonType() == PersonType.Employee) {
            result.setObject(_personController.getEmployeeList());
        } else {
            result.setState(Result.State.FAILED);
            result.setMessage(Message.get(Message.MessageType.Error_NoPrivileges));
        }

        return result;
    }


    public Result<ArrayList<Event>> getEventList() {
        Result<ArrayList<Event>> result = new Result<ArrayList<Event>>(Result.State.SUCCESSFULL, "", null);

        if (_personController.getRegisteredPersonType() == PersonType.Employee) {
            result.setObject(_eventController.getEventList());
        } else {
            result.setState(Result.State.FAILED);
            result.setMessage(Message.get(Message.MessageType.Error_NoPrivileges));
        }

        return result;
    }

}
