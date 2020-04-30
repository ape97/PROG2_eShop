package Controller;

import Model.Article;
import Model.ShoppingCart;
import Utilities.BooleanString;
import Utilities.Message;

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
    public BooleanString addArticle(ShoppingCart shoppingCart, Article article, int numberOfArticles) {
        BooleanString booleanStringResult = new BooleanString(false, "");
        if (numberOfArticles == 0) {
            //remove
            if (shoppingCart.getArticleAndQuantityMap().containsKey(article)) {
                shoppingCart.getArticleAndQuantityMap().remove(article);
                booleanStringResult.setValueB(true);
                booleanStringResult.setValueS("ENTFERNT");
            } else{
                booleanStringResult.setValueB(false);
                booleanStringResult.setValueS("NICHT 0");
            }
        } else {
            shoppingCart.getArticleAndQuantityMap().put(article, numberOfArticles);
            booleanStringResult.setValueB(true);
            booleanStringResult.setValueS(Message.get(Message.MessageType.Info_ArticleAddedToCart));
        }

        return booleanStringResult;
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
