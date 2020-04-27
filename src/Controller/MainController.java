package Controller;

import Model.Address;
import Model.Article;
import Model.Event;
import Model.Person;
import Utilities.BooleanString;
import Utilities.BooleanStringObject;

/**
 * Verbindet alle Controller miteinader.
 */
public class MainController {

    private PersonController _personController;
    private AddressController _addressController;
    private ArticleController _articleController;
    private EventController _eventController;

    public MainController() {
        _personController = new PersonController();
        _addressController = new AddressController();
        _articleController = new ArticleController();
        _eventController = new EventController();
    }

    /**
     * Reicht den Funktionsaufruf weiter an PersonController, für weitere Informationen siehe: PersonController:addEmployee(...)
     *
     * @return Gibt das BooleanString-Objekt von PersonController.addEmployee(...) zurück.
     */
    public BooleanString addEmployee(String firstname, String lastname, String username, String password) {
        return _personController.addEmployee(firstname, lastname, username, password);
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
        BooleanString booleanStringResult = new BooleanString(false, "");

        BooleanStringObject createAddressResult = _addressController.createAddress(street, houseNumber, postCode, city);

        if (!createAddressResult.getValueB()) {
            booleanStringResult.setValueS(createAddressResult.getValueS());
        } else {
            _personController.addCustomer(firstname, lastname, username, password, (Address) createAddressResult.getValueO());
        }

        return booleanStringResult;
    }

    /**
     * Reicht den Funktionsaufruf weiter an PersonController
     * Für weitere Informationen siehe: PersonController:login(...)
     */
    public BooleanStringObject login(String username, String password) {
        return _personController.login(username, password);
    }

    /**
     * Reicht den Funktionsaufruf weiter an ArticleController
     * Für weitere Informationen siehe: ArticleController:addArticle(...)
     * Wenn addArticle(...) erfolgreich war, wird addEvent(...) aufgerufen,
     * damit die Bestandsveränderung protokolliert wird.
     */
    public BooleanString addArticle(String name, int articleNumber, int stock, double price) {
        BooleanStringObject addArticleResult = _articleController.addArticle(name, articleNumber, stock, price);

        if (addArticleResult.getValueB()) {
            addEvent((Article) addArticleResult.getValueO(), stock);
        }

        return (BooleanString) addArticleResult;
    }

    /**
     * Reicht den Funktionsaufruf weiter an ArticleController
     * Für weitere Informationen siehe: ArticleController:updateStock(...)
     * Wenn updateStock(...) erfolgreich war, wird addEvent(...) aufgerufen,
     * damit die Bestandsveränderung protokolliert wird.
     */
    public BooleanString updateStock(Article article, int stockChangeValue) {
        BooleanString articleUpdateStockResult = _articleController.updateStock(article, stockChangeValue);

        if (articleUpdateStockResult.getValueB()) {
            addEvent(article, stockChangeValue);
        }

        return articleUpdateStockResult;
    }

    /**
     * Reicht den Funktionsaufruf weiter an EventController
     * Für weitere Informationen siehe: EventController:addEvent(...)
     */
    private void addEvent(Article article, int stockChangeValue) {
        _eventController.addEvent(article, _personController.getRegisteredPerson(), stockChangeValue);
    }
}
