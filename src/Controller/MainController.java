package Controller;

import Model.*;
import Utilities.*;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Verbindet alle Controller miteinader.
 */
public class MainController implements Serializable {

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


    // PERSON

    /**
     * Reicht den Funktionsaufruf weiter an PersonController, für weitere Informationen siehe: PersonController:addEmployee(...)
     *
     * @return Gibt das BooleanString-Objekt von PersonController.addEmployee(...) zurück.
     */
    public BooleanString addEmployee(String firstname, String lastname, String username, String password) {
        BooleanString booleanStringResult = new BooleanString(false, Message.get(Message.MessageType.Error_NoPrivileges));

        if (_personController.getRegisteredPersonType() == PersonType.Employee) {
            booleanStringResult = _personController.addEmployee(firstname, lastname, username, password);
        }

        return booleanStringResult;
    }

    /**
     * Reicht den Funktionsaufruf weiter an PersonController und AdressController
     * Für weitere Informationen siehe:
     * PersonController:addCustomer(...)
     * AddressController:createAddress(...)
     * <p>
     * Erstellt mit AddressController ein Address-Objekt und gib dieses weiter an PersonController.
     */
    public BooleanString addCustomer(String firstname, String lastname, String username, String password,
                                     String street, String houseNumber, String postCode, String city) {
        BooleanString booleanStringResult = new BooleanString(false, Message.get(Message.MessageType.Error_NoPrivileges));

        if (_personController.getRegisteredPersonType() == PersonType.Guest) {
            BooleanStringObject createAddressResult = _addressController.createAddress(street, houseNumber, postCode, city);

            if (!createAddressResult.getValueB()) {
                booleanStringResult.setValueS(createAddressResult.getValueS());
            } else {
                booleanStringResult = _personController.addCustomer(firstname, lastname, username, password, (Address) createAddressResult.getValueO());
            }
        }

        return booleanStringResult;
    }

    /**
     * Reicht den Funktionsaufruf weiter an PersonController
     * Für weitere Informationen siehe: PersonController:login(...)
     */
    public BooleanStringObject login(String username, String password) {
        BooleanStringObject booleanStringObjectResult = new BooleanStringObject(false, Message.get(Message.MessageType.Error_NoPrivileges), null);

        booleanStringObjectResult = _personController.login(username, password);

        return booleanStringObjectResult;
    }

    public void logout() {
        _personController.logout();
        ;
    }


    // ARTICLE

    /**
     * Reicht den Funktionsaufruf weiter an ArticleController
     * Für weitere Informationen siehe: ArticleConctroller:getSortedArticleStringList(...)
     */
    public String getSortedArticleStringList(ArticleSortMode articleSortMode) {
        return _articleController.getSortedArticlesString(articleSortMode);
    }

    /**
     * Reicht den Funktionsaufruf weiter an ArticleController
     * Für weitere Informationen siehe: ArticleController:addArticle(...)
     * Wenn addArticle(...) erfolgreich war, wird addEvent(...) aufgerufen,
     * damit die Bestandsveränderung protokolliert wird.
     */
    public BooleanString addArticle(String name, int articleNumber, int stock, double price, int packagingUnit) {

        BooleanString booleanStringResult = new BooleanString(false, Message.get(Message.MessageType.Error_NoPrivileges));

        if (_personController.getRegisteredPersonType() == PersonType.Employee) {

            BooleanStringObject addArticleResult = _articleController.addArticle(name, articleNumber, stock, price, packagingUnit);

            if (addArticleResult.getValueB()) {
                addEvent((Article) addArticleResult.getValueO(), stock);
            }

            booleanStringResult = (BooleanString) addArticleResult;
        }

        return booleanStringResult;
    }

    /**
     * Reicht den Funktionsaufruf weiter an ArticleController
     * Für weitere Informationen siehe: ArticleController:updateStock(...)
     * Wenn updateStock(...) erfolgreich war, wird addEvent(...) aufgerufen,
     * damit die Bestandsveränderung protokolliert wird.
     */
    public BooleanString updateStock(int articleNumber, int stockChangeValue) {
        BooleanString booleanStringResult = new BooleanString(false, Message.get(Message.MessageType.Error_NoPrivileges));

        if (_personController.getRegisteredPersonType() == PersonType.Employee) {

            Article article = _articleController.getArticleByArticleNumber(articleNumber);

            if (article == null) {
                booleanStringResult.setValueB(false);
                booleanStringResult.setValueS(Message.get(Message.MessageType.Error_ArticleNumberNotFound));
            } else {
                BooleanString articleUpdateStockResult = _articleController.updateStock(article, stockChangeValue);

                if (articleUpdateStockResult.getValueB()) {
                    addEvent(article, stockChangeValue);
                }

                booleanStringResult = articleUpdateStockResult;
            }
        }

        return booleanStringResult;
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
    public BooleanString addArticleToShoppingCart(int articleNumber, int numberOfArticles) {
        BooleanString booleanStringResult = new BooleanString(false, Message.get(Message.MessageType.Error_NoPrivileges));

        if (_personController.getRegisteredPersonType() == PersonType.Customer) {

            booleanStringResult = new BooleanString(true, "");

            Article article = _articleController.getArticleByArticleNumber(articleNumber);
            if (article == null) {
                booleanStringResult.setValueB(false);
                booleanStringResult.setValueS(Message.get(Message.MessageType.Error_ArticleNumberNotFound));
            } else if (!_articleController.checkArticleIsStock(article, numberOfArticles)) {
                booleanStringResult.setValueB(false);
                booleanStringResult.setValueS(Message.get(Message.MessageType.Error_ArticleStockNotEnough));
            } else if (numberOfArticles < 0) {
                booleanStringResult.setValueB(false);
                booleanStringResult.setValueS(Message.get(Message.MessageType.Error_ArticleItemNumberNotNegative));
            } else if (!_articleController.checkArticleStockMatchPackagingUnit(article, numberOfArticles)) {
                booleanStringResult.setValueB(false);
                booleanStringResult.setValueS(Message.get(Message.MessageType.Error_ArticleStockNotMatchPackagingUnit));
            } else {
                Customer customer = (Customer) _personController.getRegisteredPerson();
                booleanStringResult = _shoppingCartController.addArticle(customer.getShoppingCart(), article, numberOfArticles);
            }
        }

        return booleanStringResult;
    }

    /**
     * Kauft die Artikel in dem ShoppingCart-Objekt des angemeldeten Customer-Objektes
     *
     * @return Gibt ein BooleanStringObject-Objekt zurück
     * boolean -> Gibt an, ob der Kauf erfolgreich war
     * String -> Gibt die entsprechende (Fehler-) Meldung aus
     * Object -> Enthält die Rechnung als formatierten String, sofern der Kauf erfolgreich war
     */
    public BooleanStringObject buyShoppingCart() {
        BooleanStringObject booleanStringObjectResult = new BooleanStringObject(false, Message.get(Message.MessageType.Error_NoPrivileges), null);

        if (_personController.getRegisteredPersonType() == PersonType.Customer) {
            booleanStringObjectResult = new BooleanStringObject(true, Message.get(Message.MessageType.Info_OrderSuccess), null);
            Customer customer = (Customer) _personController.getRegisteredPerson();
            ShoppingCart shoppingCart = customer.getShoppingCart();
            Bill bill = _billController.createBill(customer);

            // Prüft ob alle Artikel noch auf Lager sind
            for (Article article : shoppingCart.getArticleAndQuantityMap().keySet()) {
                boolean articleInStock = _articleController.checkArticleIsStock(article, shoppingCart.getArticleAndQuantityMap().get(article));
                if (!articleInStock) {
                    booleanStringObjectResult.setValueB(false);
                    booleanStringObjectResult.setValueS(Message.get(Message.MessageType.Error_ArticleStockCartNotEnough));
                    break;
                }
            }

            // Lagerbestand der Artikel wird angepasst, sofern der booleanStringResult Wert nicht auf false gesetzt wurde
            if (booleanStringObjectResult.getValueB()) {
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
                booleanStringObjectResult.setValueO(bill.toString());
            }
        }

        return booleanStringObjectResult;
    }

    /**
     * Leert den Einkaufswagen blabla TODO
     *
     * @return Gibt ein BooleanString-Object zurück.
     * boolean -> Gibt zurück, ob das leeren erfolgreich geklappt hat.
     * String -> Gibt die entsprechende (Fehler-) Meldung aus.
     */
    public BooleanString clearShoppingCart() {
        BooleanString booleanStringResult = new BooleanString(true, Message.get(Message.MessageType.Info_ShoppingCartClearSuccess));

        if (_personController.getRegisteredPersonType() == PersonType.Customer) {
            Customer customer = (Customer) _personController.getRegisteredPerson();
            _shoppingCartController.clear(customer.getShoppingCart());
        } else {
            booleanStringResult.setValueB(false);
            booleanStringResult.setValueS(Message.get(Message.MessageType.Error_NoPrivileges));
        }

        return booleanStringResult;
    }

    /**
     * Gibt den Einkaufswagen als formatierten String zurück blabla TODO
     *
     * @return Gibt ein BooleanString-Objekt zurück.
     * boolean -> Ob der Benutzer die Rechte zum anzeigen hat
     * String --> enthält die Fehlermeldung oder das ShppoingCart als String
     */
    public BooleanString getShoppingCartString() {
        BooleanString booleanStringResult = new BooleanString(true, "");

        if (_personController.getRegisteredPersonType() == PersonType.Customer) {
            Customer customer = (Customer) _personController.getRegisteredPerson();
            booleanStringResult.setValueS(customer.getShoppingCart().toString());
        } else {
            booleanStringResult.setValueB(false);
            booleanStringResult.setValueS(Message.get(Message.MessageType.Error_NoPrivileges));
        }

        return booleanStringResult;
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
    public BooleanString getEventsString() {
        BooleanString booleanStringResult = new BooleanString(true, "");

        if (_personController.getRegisteredPersonType() == PersonType.Employee) {
            booleanStringResult.setValueS(_eventController.getEventsString());
        } else {
            booleanStringResult.setValueB(false);
            booleanStringResult.setValueS(Message.get(Message.MessageType.Error_NoPrivileges));
        }

        return booleanStringResult;
    }
}
