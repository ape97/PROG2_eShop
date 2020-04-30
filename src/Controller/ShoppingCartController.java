package Controller;

import Model.Article;
import Model.ShoppingCart;

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
    public void addArticle(ShoppingCart shoppingCart, Article article, int numberOfArticles) {
        if (numberOfArticles == 0) {
            //remove
            if (shoppingCart.getArticleAndQuantityMap().containsKey(article)) {
                shoppingCart.getArticleAndQuantityMap().remove(article);
            }
        } else {
            shoppingCart.getArticleAndQuantityMap().put(article, numberOfArticles);
        }
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
