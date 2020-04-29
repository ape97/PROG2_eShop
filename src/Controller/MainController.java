package Controller;

import Model.*;
import Utilities.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Verbindet alle Controller miteinader.
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

        if (_personController.getRegisteredPersonType() == PersonType.Guest) {
            booleanStringObjectResult = _personController.login(username, password);
        }

        return booleanStringObjectResult;
    }


    // ARTICLE

    /**
     * Reicht den Funktionsaufruf weiter an ArticleController
     * Für weitere Informationen siehe: ArticleConctroller:getSortedArticleStringList(...)
     */
    public ArrayList<String> getSortedArticleStringList(ArticleSortMode articleSortMode) {
        return _articleController.getSortedArticleStringList(articleSortMode);
    }

    /**
     * Reicht den Funktionsaufruf weiter an ArticleController
     * Für weitere Informationen siehe: ArticleController:addArticle(...)
     * Wenn addArticle(...) erfolgreich war, wird addEvent(...) aufgerufen,
     * damit die Bestandsveränderung protokolliert wird.
     */
    public BooleanString addArticle(String name, int articleNumber, int stock, double price) {

        BooleanString booleanStringResult = new BooleanString(false, Message.get(Message.MessageType.Error_NoPrivileges));

        if (_personController.getRegisteredPersonType() == PersonType.Employee) {

            BooleanStringObject addArticleResult = _articleController.addArticle(name, articleNumber, stock, price);

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
    public BooleanString updateStock(Article article, int stockChangeValue) {
        BooleanString booleanStringResult = new BooleanString(false, Message.get(Message.MessageType.Error_NoPrivileges));

        if (_personController.getRegisteredPersonType() == PersonType.Employee) {
            BooleanString articleUpdateStockResult = _articleController.updateStock(article, stockChangeValue);

            if (articleUpdateStockResult.getValueB()) {
                addEvent(article, stockChangeValue);
            }

            booleanStringResult = articleUpdateStockResult;
        }

        return booleanStringResult;
    }

    public BooleanString addArticleToShoppingCart(int articleNumber, int numberOfArticles) {
        BooleanString booleanStringResult = new BooleanString(false, Message.get(Message.MessageType.Error_NoPrivileges));

        if (_personController.getRegisteredPersonType() == PersonType.Customer) {

            booleanStringResult = new BooleanString(true, Message.get(Message.MessageType.Info_ArticleAddedToCart));

            Article article = _articleController.getArticleByArticleNumber(articleNumber);
            if (article == null) {
                booleanStringResult.setValueB(false);
                booleanStringResult.setValueS(Message.get(Message.MessageType.Error_ArticleNumberNotFound));
            } else if (!_articleController.checkArticleIsStock(article, numberOfArticles)) {
                booleanStringResult.setValueB(false);
                booleanStringResult.setValueS(Message.get(Message.MessageType.Error_ArticleStockNotEnough));
            } else if (numberOfArticles <= 0) {
                booleanStringResult.setValueB(false);
                booleanStringResult.setValueS(Message.get(Message.MessageType.Error_ArticleItemNumberGreaterZero));
            } else {
                Customer customer = (Customer) _personController.getRegisteredPerson();
                _shoppingCartController.addArticle(customer.getShoppingCart(), article, numberOfArticles);
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
                    // updateStock muss über ArticleController erfolgen, da die lokale Methode den LoginTypen auf EMplyee prüft
                    _articleController.updateStock(article, -shoppingCart.getArticleAndQuantityMap().get(article)); // Achtung: Negierung der Artikelanzahl -

                    totalPrice += article.getPrice();
                    _billController.addBillPosition(bill, article.toString(false));
                }
                bill.setTotalPrice(totalPrice);
                booleanStringObjectResult.setValueO(bill.toString());
            }
        }

        return booleanStringObjectResult;
    }

    /**
     * Reicht den Funktionsaufruf weiter an EventController
     * Für weitere Informationen siehe: EventController:addEvent(...)
     */
    private void addEvent(Article article, int stockChangeValue) {
        _eventController.addEvent(article, _personController.getRegisteredPerson(), stockChangeValue);
    }
}
