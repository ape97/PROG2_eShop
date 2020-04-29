package Controller;

import Model.Article;
import Utilities.ArticleSortMode;
import Utilities.BooleanString;
import Utilities.BooleanStringObject;
import Utilities.Message;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * WARNING: Sollte nur vom MainController verwendet werden
 * Verwaltet die Article-Objekte.
 */
public class ArticleController implements Serializable {

    private ArrayList<Article> _articleList;

    /**
     * Der Konstruktor erzeugt eine leere ArrayList _articleList für Article-Objekte.
     */
    public ArticleController() {
        _articleList = new ArrayList<>();
    }

    /**
     * Neuer Artikel:
     * Prüft die Parameter und erzeugt ein neues Article-Objekt und fügt dieses der ArrayList _articleList hinzu.
     *
     * @param name          Die Bezeichnung des Artikels
     * @param articleNumber Die Artikelnummer des Artikels
     * @param stock         Der Lagerbestand des Artikels (stk.)
     * @param price         Der Preis des Artikels
     * @return Gibt ein BooleanString-Objekt zurück.
     * Der boolean gibt an, ob das erzeugen des Article-Objekts erfolgreich war oder nicht.
     * Der String gibt die entsprechende (Fehler-) Meldung an.
     * Das Object gibt ist das erstellte Article-Object, ansonsten null.
     */
    public BooleanStringObject addArticle(String name, int articleNumber, int stock, double price) {
        BooleanStringObject booleanStringObjectResult = new BooleanStringObject(false, "", null);

        if (name.trim().isEmpty()) {
            booleanStringObjectResult.setValueS(Message.get(Message.MessageType.Error_ArticleNameNotEmpty));
        } else if (price <= 0.0) {
            booleanStringObjectResult.setValueS(Message.get(Message.MessageType.Error_ArticlePriceGreaterThanZero));
        } else if (!checkArticleNumberExists(articleNumber)) {
            booleanStringObjectResult.setValueS(Message.get(Message.MessageType.Error_ArticleNumberExists));
        } else {
            Article article = new Article(name, articleNumber, stock, price);
            _articleList.add(article);

            booleanStringObjectResult.setValueB(true);
            booleanStringObjectResult.setValueS(Message.get(Message.MessageType.Info_ArticleCreated));
            booleanStringObjectResult.setValueO(article);
        }

        return booleanStringObjectResult;
    }

    /**
     * Bestandsänderung des Artikels
     *
     * @param article          Der Artikel dessen Bestand sich verändern soll
     * @param stockChangeValue Die Anzahl um die sich der Bestand verändern soll
     * @return Gibt ein BooleanString-Objekt zurück.
     * Der boolean gibt an, ob der Bestand erfolgreich geändert wurde.
     * Der String gibt die entsprechene (Fehler-) Meldung an.
     */
    public BooleanString updateStock(Article article, int stockChangeValue) {
        BooleanString booleanStringResult = new BooleanString(false, "");

        if (stockChangeValue == 0) {
            booleanStringResult.setValueS(Message.get(Message.MessageType.Error_ChangeValueNotZero));
        } else {
            article.setStock(article.getStock() + stockChangeValue);
        }

        return booleanStringResult;
    }

    /**
     * Sortierte Liste der Article-Objekte als Strings
     * Ist für die CUI gedacht, weil dort String Ausgaben gemacht werden
     *
     * @param articleSortMode Gibt an, wie die Artikel sortiert werden sollen
     * @return Gibt eine ArrayList mit Artikel Strings (article.toString) zurück
     */
    public ArrayList<String> getSortedArticleStringList(ArticleSortMode articleSortMode) {
        ArrayList<String> sortedArticleStringList = new ArrayList<>();
        sortArticles(articleSortMode);

        for (Article article : _articleList) {
            sortedArticleStringList.add(article.toString(true));
        }

        return sortedArticleStringList;
    }

    /**
     * Sortiert die Artikel nach dem angegebenen Schema
     *
     * @param articleSortMode Das angegebene Schema nachdem sortiert werden soll
     */
    private void sortArticles(ArticleSortMode articleSortMode) {
        Collections.sort(_articleList, new ArticleComparator(articleSortMode));
    }

    /**
     * Prüft ob die angegebene ARtikelnummer bereits einem Article-Objekt zugeordnet ist.
     *
     * @param articleNumber Die Artikelnummer die gesucht wird
     * @return Gibt einen boolean zurück.
     * true -> Artikelnummer ist bereits vergeben
     * false -> Artikelnummer ist noch nicht vergeben
     */
    private boolean checkArticleNumberExists(int articleNumber) {
        boolean result = false;

        for (Article article : _articleList) {
            if (article.getArticleNumber() == articleNumber) {
                result = true;
                break;
            }
        }

        return result;
    }

    public Article getArticleByArticleNumber(int articleNumber) {
        Article articleResult = null;

        for (Article article : _articleList) {
            if (article.getArticleNumber() == articleNumber) {
                articleResult = article;
                break;
            }
        }

        return articleResult;
    }

    public boolean checkArticleIsStock(Article article, int stockNumber){
        return article.getStock() >= stockNumber;
    }

    /**
     * Ein Comparator für Article-Objekte, damit diese nach ihren Eigenschaften sortiert werden können
     * Quelle: // https://stackoverflow.com/questions/2784514/sort-arraylist-of-custom-objects-by-property
     */
    private class ArticleComparator implements Comparator<Article> {
        private ArticleSortMode _articleSortMode;

        /**
         * Im Konstruktor wird das Schema, der SortMode als Member gesetzt
         *
         * @param articleSortMode Das Schema nachdem sortiert werden soll
         */
        public ArticleComparator(ArticleSortMode articleSortMode) {
            _articleSortMode = articleSortMode;
        }

        /**
         * Je nachdem welcher SortMode in _articleSortMode festgelegt ist,
         * werden die Artikel anders verglichen.
         *
         * @param o1 Artikel 1 zum vergleichen
         * @param o2 Artikel 2 zum vergleichen
         * @return https://www.javatpoint.com/java-string-compareto
         */
        @Override
        public int compare(Article o1, Article o2) {
            int result;

            switch (_articleSortMode) {
                case ArticleName:
                    result = o1.getName().compareTo(o2.getName());
                    break;
                case ArticleNumber:
                    result = ((Integer) o1.getArticleNumber()).compareTo(((Integer) o2.getArticleNumber()));
                    break;
                default:
                    throw new InvalidParameterException();
            }
            return result;
        }
    }
}
