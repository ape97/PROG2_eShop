package Controller;

import Model.*;
import Utilities.*;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Verbindet alle Controller miteinander.
 * Dadurch können Methoden unterschiedlicher Controller zusammen verwendet werden.
 * Alle Aktionen sollten über den MainController laufen, dieser ist die Schnittstelle zwischen Daten und Anzeige.
 */
public class MainController {

    private PersonController _personController;
    private AddressController _addressController;
    private ArticleController _articleController;
    private EventController _eventController;
    private ShoppingCartController _shoppingCartController;
    private BillController _billController;


    public MainController() {
        _personController = new PersonController();
        _addressController = new AddressController();
        _articleController = new ArticleController();
        _eventController = new EventController();
        _shoppingCartController = new ShoppingCartController();
        _billController = new BillController();
    }

    /**
     * Reicht den Funktionsaufruf weiter an PersonController, für weitere Informationen siehe: PersonController:addEmployee(...)
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

    /**
     * Reicht den Funktionsaufruf weiter an PersonController
     * Für weitere Informationen siehe:
     * PersonController:removePerson(...)
     */
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
        Result<PersonType> result = _personController.login(username, password);
        return result;
    }

    /**
     * Reicht den Funktionsaufruf weiter an PersonController
     * Für weitere Informationen siehe: PersonController:logout(...)
     */
    public Result<Void> logout() {
        Result<Void> result = new Result<>(Result.State.SUCCESSFULL, "Sie wurden ausgeloggt.", null);
        _personController.logout();
        return result;
    }

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

    /**
     * Reicht den Funktionsaufruf weiter an ArticleController
     * Für weitere Informationen siehe: ArticleController:removeArticle(...)
     * Wenn removeArticle(...) erfolgreich war, wird addEvent(...) aufgerufen,
     * damit die Bestandsveränderung protokolliert wird.
     */
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
     * Fügt einen Artikel mit der Anzahl, über die Artikelnummer dem Warenkorb hinzu
     * Dafür werden erst diverse Bedingungen geprüft, die aussagen ob diese Aktion möglich ist.
     * Sind die Prüfungen erfolgreich wird ShoppingCartController.addShoppingCartItem(...) aufgerufen.
     * Für weitere Informationen siehe: ShoppingCartController.addShoppingCartItem(...).
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

    /**
     * Entfernt einen Artikel über die Artikelnummer aus dem Warenkorb.
     * Reicht den Funktionsaufruf weiter an ShoppingCartController.
     * Für weitere Informationen siehe: ShoppingCartController.removeShoppingCartItem(...).
     */
    public Result<Void> removeArticleFromShoppingCart(String articleNumber) {

        if (_personController.getRegisteredPersonType() != PersonType.Customer) {
            return new Result<Void>(Result.State.FAILED, Message.get(Message.MessageType.Error_NoPrivileges), null);
        }

        Result<Integer> parseArticleNumberResult = Parse.tryParseInt(articleNumber);
        if (parseArticleNumberResult.getState() == Result.State.FAILED) {
            return new Result<Void>(Result.State.FAILED, "Artikelnummer ist kein gültiger Ganzzhalenwert.", null);
        }

        Customer customer = (Customer) _personController.getRegisteredPerson();
        return _shoppingCartController.removeShoppingCartItem(customer.getShoppingCart(), parseArticleNumberResult.getObject());
    }

    /**
     * Kauft die Artikel in dem ShoppingCart-Objekt des angemeldeten Customer-Objektes
     *
     * @return Gibt ein Result<Bill>-Objekt zurück, welches aussagt ob die Aktion erfolgreich war oder nicht.
     * getObject() enthält das Bill-Objekt, also die Rechnung, sofern der Kauf erfolgreich war
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
     * Leert den Einkaufswagen
     * Reicht den FUnktionsaufruf weiter an ShoppingCartController.
     * Für mehr Informationen siehe: ShoppingCartController.clear(...).
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
     * Gibt den Einkaufswagen als Liste von ShoppingCartItem-Objekten zurück.
     * @return Gibt ein Result-Objekt zurück, welches aussagt, ob die Aktion erfolgreich war oder nicht.
     * getObject() enthält die Liste, sofern die AKtion erfolgreich war.
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
     * Gibt die Artikel als Liste zurück.
     *
     * @return Gibt ein Result-Objekt zurück, welches aussagt, ob die Aktion erfolgreich war oder nicht.
     * getObject() enthält die Liste, sofern die AKtion erfolgreich war.
     */
    public Result<ArrayList<Article>> getArticleList() {
        Result<ArrayList<Article>> result = new Result<ArrayList<Article>>(Result.State.SUCCESSFULL, "", null);
        result.setObject(_articleController.getArticleList());
        return result;
    }

    /**
     * Gibt die Kunden als Liste zurück.
     * @return Gibt ein Result-Objekt zurück, welches aussagt, ob die Aktion erfolgreich war oder nicht.
     * getObject() enthält die Liste, sofern die AKtion erfolgreich war.
     */
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


    /**
     * Gibt die Mitarbeiter als Liste zurück.
     * @return Gibt ein Result-Objekt zurück, welches aussagt, ob die Aktion erfolgreich war oder nicht.
     * getObject() enthält die Liste, sofern die AKtion erfolgreich war.
     */
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


    /**
     * Gibt die Events als Liste zurück (Änderungsprotokoll).
     * @return Gibt ein Result-Objekt zurück, welches aussagt, ob die Aktion erfolgreich war oder nicht.
     * getObject() enthält die Liste, sofern die AKtion erfolgreich war.
     */
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
