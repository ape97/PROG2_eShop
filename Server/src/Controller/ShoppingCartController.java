package Controller;

import Model.Article;
import Model.ShoppingCart;
import Model.ShoppingCartItem;
import Utilities.Message;
import Utilities.Result;
import java.io.Serializable;

/**
 * WARNING: Sollte nur vom MainController verwendet werden
 * Verwaltet die ShoppingCartItem-Objekte.
 */
public class ShoppingCartController {

    /**
     * Fügt ein ShoppingCartItem einem ShoppingCart hinzu.
     * Falls ein ShoppingCartItem mit dem selben Artikel bereits vorhanden ist, wird dieses ersetzt.
     * @param shoppingCart Das enstprechende ShoppingCart (Warenkorb/Einkaufswagen)
     * @param shoppingCartItem Das ShoppingCartItem, welches dem ShoppingCart hinzugefügt werden soll
     * @return Gibt ein Result<Void> zurück, welches aussagt, ob die Aktion erfolgreich war oder nicht inkl. Meldung.
     */
    public Result<Void> addShoppingCartItem(ShoppingCart shoppingCart, ShoppingCartItem shoppingCartItem) {
        Result<Void> result = new Result<Void>(Result.State.FAILED, "", null);
        ShoppingCartItem oldShoppingCartItem = getShoppingCartItemByArticle(shoppingCart, shoppingCartItem.getArticle());

        if (shoppingCartItem.getQuantity() == 0) {
            //remove
            if (oldShoppingCartItem != null) {
                shoppingCart.getShoppingCartItemList().remove(oldShoppingCartItem);
                result.setState(Result.State.SUCCESSFULL);
                result.setMessage(Message.get(Message.MessageType.Info_ArticleRemovedFromCartSuccess));
            } else {
                result.setState(Result.State.FAILED);
                result.setMessage(Message.get(Message.MessageType.Error_ArticleItemNumberNotZero));
            }
        } else {
            if (oldShoppingCartItem != null) {
                int index = shoppingCart.getShoppingCartItemList().indexOf(oldShoppingCartItem);
                shoppingCart.getShoppingCartItemList().remove(oldShoppingCartItem);
                shoppingCart.getShoppingCartItemList().add(index, shoppingCartItem);
                result.setMessage(Message.get(Message.MessageType.Info_ArticleChangeQuantityInCartSuccess));
            } else {
                shoppingCart.getShoppingCartItemList().add(shoppingCartItem);
                result.setMessage(Message.get(Message.MessageType.Info_ArticleAddedToCart));
            }
            result.setState(Result.State.SUCCESSFULL);
        }

        return result;
    }


    /**
     * Entfernt ein ShoppingCartItem aus einem ShoppingCart.
     * @param shoppingCart Das enstprechende ShoppingCart aus dem das ShoppingCartItem entfernt werden soll
     * @param articleNumber Der Artikel welcher in dem ShoppingCartItem hinterlegt ist, dass entfernt werden soll
     * @return Gibt ein Result<Void> zurück, welches aussagt, ob die Aktion erfolgreich war oder nicht inkl. Meldung.
     */
    public Result<Void> removeShoppingCartItem(ShoppingCart shoppingCart, int articleNumber) {
        ShoppingCartItem shoppingCartItem = getShoppingCartItemByArticleNumber(shoppingCart, articleNumber);
        if (shoppingCartItem == null) {
            return new Result<Void>(Result.State.FAILED, "Keinm ARtikel mit dieser ARtikelnummer im Shoppingcart", null);
        }

        shoppingCart.getShoppingCartItemList().remove(shoppingCartItem);

        return new Result<Void>(Result.State.SUCCESSFULL, Message.get(Message.MessageType.Info_ArticleRemovedFromCartSuccess), null);
    }

    /**
     * Gibt das entsprechende ShoppingCartItem-Object zurück, welches auf den übergebenen Artikel referenziert.
     * @param shoppingCart Das ShoppingCart in dem sich das ShoppingCartItem befindet
     * @param article Das Article-Objekt, welches in dem egsuchten SHoppingCartItem hinterlegt ist
     * @return Gibt das ShoppingCartItem zurück, dass den Artikel referenziert, null falls nicht gefunden
     */
    public ShoppingCartItem getShoppingCartItemByArticle(ShoppingCart shoppingCart, Article article) {
        ShoppingCartItem result = null;

        for (ShoppingCartItem shoppingCartItem : shoppingCart.getShoppingCartItemList()) {
            if (shoppingCartItem.getArticle() == article) {
                result = shoppingCartItem;
                break;
            }
        }

        return result;
    }

    /**
     * Gibt das entsprechende ShoppingCartItem-Object zurück, welches auf den Artikel mit der übergebenen Artikelnummer referenziert.
     * @param shoppingCart Das ShoppingCart in dem sich das ShoppingCartItem befindet
     * @param articleNumber Die Artikelnummer
     * @return Gibt das ShoppingCartItem zurück, dass den Artikel referenziert, null falls nicht gefunden
     */
    public ShoppingCartItem getShoppingCartItemByArticleNumber(ShoppingCart shoppingCart, int articleNumber) {
        ShoppingCartItem result = null;

        for (ShoppingCartItem shoppingCartItem : shoppingCart.getShoppingCartItemList()) {
            if (shoppingCartItem.getArticle().getArticleNumber() == articleNumber) {
                result = shoppingCartItem;
                break;
            }
        }

        return result;
    }

    /**
     * Leert den Warenkorb bzw. die ShoppingCartItem-Liste im ShoppingCart
     *
     * @param shoppingCart Das ShoppingCart-Objekt
     */
    public void clear(ShoppingCart shoppingCart) {
        shoppingCart.getShoppingCartItemList().clear();
    }
}
