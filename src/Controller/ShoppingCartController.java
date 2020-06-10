package Controller;

import Model.Article;
import Model.ShoppingCart;
import Model.ShoppingCartItem;
import Utilities.Message;
import Utilities.Result;

import java.io.Serializable;

public class ShoppingCartController implements Serializable {

    /**
     * Fügt einen Article-Objekt und die Anzahl int dem ShoppingCart-Objekt hinzu
     * Falls das Article-Object bereits vorhanden ist, wird dieses einfach mit dem neuen Wert überschrieben
     *
     * @param shoppingCart Das ShoppingCart-Objekt
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


    public Result<Void> removeShoppingCartItem(ShoppingCart shoppingCart, ShoppingCartItem shoppingCartItem) {
        Result<Void> result = new Result<Void>(Result.State.SUCCESSFULL, Message.get(Message.MessageType.Info_ArticleRemovedFromCartSuccess), null);
        shoppingCart.getShoppingCartItemList().remove(shoppingCartItem);

        return result;
    }

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
     * Leert den Warenkorb bzw. die HashMap im ShoppingCart-Objekt
     *
     * @param shoppingCart Das ShoppingCart-Objekt
     */
    public void clear(ShoppingCart shoppingCart) {
        shoppingCart.getShoppingCartItemList().clear();
    }
}
