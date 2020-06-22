package Model;

import org.omg.CORBA.ARG_IN;

import java.io.Serializable;

public class ShoppingCartItem implements Serializable {
    private Article _article;
    private int _quantity;

    public ShoppingCartItem(Article article, int quantity) {
        _article = article;
        _quantity = quantity;
    }


    public Article getArticle() {
        return _article;
    }

    public void setArticle(Article article) {
        _article = article;
    }

    public int getQuantity() {
        return _quantity;
    }

    public void setQuantity(int quantity) {
        _quantity = quantity;
    }
}
