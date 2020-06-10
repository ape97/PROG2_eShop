package Controller;

import Model.Article;
import Model.ShoppingCart;
import Utilities.Message;
import Utilities.Result;

import java.io.Serializable;

public class ShoppingCartController implements Serializable {

    /**
     * Fügt einen Article-Objekt und die Anzahl int dem ShoppingCart-Objekt hinzu
     * Falls das Article-Object bereits vorhanden ist, wird dieses einfach mit dem neuen Wert überschrieben
     *
     * @param shoppingCart     Das ShoppingCart-Objekt
     * @param article          Das Article-Objekt der Key
     * @param numberOfArticles Die Anzahl int die Value
     */
    public Result<Void> addArticle(ShoppingCart shoppingCart, Article article, int numberOfArticles) {
        Result<Void> result = new Result<Void>(Result.State.FAILED, "", null);
        if (numberOfArticles == 0) {
            //remove
            if (shoppingCart.getArticleAndQuantityMap().containsKey(article)) {
                shoppingCart.getArticleAndQuantityMap().remove(article);
                result.setState(Result.State.SUCCESSFULL);
                result.setMessage(Message.get(Message.MessageType.Info_ArticleRemovedFromCartSuccess));
            } else{
                result.setState(Result.State.FAILED);
                result.setMessage(Message.get(Message.MessageType.Error_ArticleItemNumberNotZero));
            }
        } else {
            shoppingCart.getArticleAndQuantityMap().put(article, numberOfArticles);
            result.setState(Result.State.SUCCESSFULL);
            result.setMessage(Message.get(Message.MessageType.Info_ArticleAddedToCart));
        }

        return result;
    }

    /**
     * Leert den Warenkorb bzw. die HashMap im ShoppingCart-Objekt
     *
     * @param shoppingCart Das ShoppingCart-Objekt
     */
    public void clear(ShoppingCart shoppingCart) {
        shoppingCart.getArticleAndQuantityMap().clear();
    }
}
