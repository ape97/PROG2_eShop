package Controller;

import Model.Article;
import Model.BulkArticle;
import Model.Person;
import Utilities.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Observable;

/**
 * WARNING: Sollte nur vom MainController verwendet werden
 * Verwaltet die Article-Objekte.
 */
public class ArticleController implements Serializable {

    private ArrayList<Article> _articleList;
    private transient ObservableList<Article> _articleObservableList;

    private boolean result;

    /**
     * Der Konstruktor erzeugt eine leere ArrayList _articleList für Article-Objekte.
     */
    public ArticleController() {
        _articleList = new ArrayList<>();
        _articleObservableList = FXCollections.observableList(_articleList);
    }

    public void InitAfterSerialization() {
        _articleObservableList = FXCollections.observableList(_articleList);

        _articleObservableList.addListener(new ListChangeListener<Article>() {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends Article> c) {

            }
        });


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
    public Result<Article> addArticle(String name, int stock, double price, int packagingUnit) {
        Result<Article> result = new Result<Article>(Result.State.FAILED, "", null);

        if (name.trim().isEmpty()) {
            result.setMessage(Message.get(Message.MessageType.Error_ArticleNameNotEmpty));
        } else if (price <= 0.0) {
            result.setMessage(Message.get(Message.MessageType.Error_ArticlePriceGreaterThanZero));
        } else if (!checkArticleStockMatchPackagingUnit(packagingUnit, stock)) {
            result.setMessage(Message.get(Message.MessageType.Error_ArticleStockNotMatchPackagingUnit));
        } else {
            Article article;
            int articleNumber = generateArticleNumber();
            // Entescheidung ob ein Article oder ein BulkArticle Objekt erstellt werden soll
            // Wenn die angegebene Verpackungseinheit größer als 1 ist, wird ein BulkArticle erstellt (>=1 wird immer als normaler Article erstellt)
            if (packagingUnit > 1) {
                article = new BulkArticle(name, articleNumber, stock, price, packagingUnit);
            } else {
                article = new Article(name, articleNumber, stock, price);
            }

            _articleObservableList.add(article);

            result.setState(Result.State.SUCCESSFULL);
            result.setMessage(Message.get(Message.MessageType.Info_ArticleCreated));
            result.setObject(article);
        }

        return result;
    }


    public Result<Article> editArticle(Article article, String name, double price) {
        Result<Article> result = new Result<Article>(Result.State.FAILED, "", null);

        if (name.trim().isEmpty()) {
            result.setMessage(Message.get(Message.MessageType.Error_ArticleNameNotEmpty));
        } else if (price <= 0.0) {
            result.setMessage(Message.get(Message.MessageType.Error_ArticlePriceGreaterThanZero));
        } else {
            article.setName(name);
            article.setPrice(price);

            result.setState(Result.State.SUCCESSFULL);
            result.setMessage(Message.get(Message.MessageType.Info_ArticleEdited));
            result.setObject(article);
        }

        return result;
    }

    public Result<Void> removeArticle(Article article) {
        Result<Void> result = new Result<Void>(Result.State.SUCCESSFULL, Message.get(Message.MessageType.Info_ArticleRemoved), null);

        _articleObservableList.remove(article);

        return result;
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
    public Result<Void> updateStock(Article article, int stockChangeValue) {
        Result<Void> result = new Result<Void>(Result.State.FAILED, "", null);

        if (stockChangeValue == 0) {
            result.setMessage(Message.get(Message.MessageType.Error_ChangeValueNotZero));
        } else if (!checkArticleStockMatchPackagingUnit(article, stockChangeValue)) {
            result.setMessage(Message.get(Message.MessageType.Error_ArticleStockNotMatchPackagingUnit));
        } else {
            result.setState(Result.State.SUCCESSFULL);
            result.setMessage(Message.get(Message.MessageType.Info_ArticleStockChanged));
            article.setStock(article.getStock() + stockChangeValue);
        }

        return result;
    }

    /**
     * TODO: ERSTMAL AUSKOMMENTIERT FÜR FX
     *
     * Sortierte Liste der Article-Objekte als Strings
     * Ist für die CUI gedacht, weil dort String Ausgaben gemacht werden
     *
     * @param articleSortMode Gibt an, wie die Artikel sortiert werden sollen
     * @return Gibt einen formatierten String mit Artikel Strings (article.toString) zurück
     */
    /** public String getSortedArticlesString(ArticleSortMode articleSortMode) {
     String result = "";
     sortArticles(articleSortMode);

     result += "Artikelnummer  -  Artikelbezeichnung  -  Preis  -  Lagerbestand  -  Verpackungseinheit\n";

     for (Article article : _articleList) {
     result += article.toString(true) + "\n";
     }

     return result;
     }*/

    /**
     * Sortiert die Artikel nach dem angegebenen Schema
     *
     * @param articleSortMode Das angegebene Schema nachdem sortiert werden soll
     */
    //TODO: ERSTMAL AUSKOMMENTIERT FÜR FX
   /* private void sortArticles(ArticleSortMode articleSortMode) {
        Collections.sort(_articleList, new ArticleComparator(articleSortMode));
    }*/

    /**
     * Prüft ob die angegebene Artikelnummer bereits einem Article-Objekt zugeordnet ist.
     *
     * @param articleNumber Die Artikelnummer die gesucht wird
     * @return Gibt einen boolean zurück.
     * true -> Artikelnummer ist bereits vergeben
     * false -> Artikelnummer ist noch nicht vergeben
     */
    private boolean checkArticleNumberExists(int articleNumber) {
        boolean result = false;

        for (Article article : _articleObservableList) {
            if (article.getArticleNumber() == articleNumber) {
                result = true;
                break;
            }
        }

        return result;
    }

    /**
     * Prüft, ob die Artikelanzahl basierend auf der Verpackungseinheit erlaubt ist.
     * Dafür wird zunächst geprüft, ob es sich überhaupt um ein BulkArticle-Objekt handelt
     * Wenn das der Fall ist, wird die erweiterte Methode aufgerufen
     *
     * @param article    Der zu überprüfende Artikel
     * @param stockValue Die Artikelanzahl
     * @return Gibt einen boolean zurück, der aussagt, ob die Anzahl gültig ist oder nicht
     */
    public boolean checkArticleStockMatchPackagingUnit(Article article, int stockValue) {
        boolean result = true;

        String className = article.getClass().getSimpleName();

        if (className.equals(BulkArticle.class.getSimpleName())) {
            int packagingUnit = ((BulkArticle) article).getPackagingUnit();
            result = checkArticleStockMatchPackagingUnit(packagingUnit, stockValue);
        }

        return result;
    }

    /**
     * Prüft, ob die Artikelanzahl basierend auf der Verpackungseinheit erlaubt ist.
     * Anzahl stockValue modulo der Verpackungseinheit
     *
     * @param packagingUnit Die Verpackungseinehit
     * @param stockValue    Die Artikelanzahl
     * @return Gibt einen boolean zurück, der aussagt, ob die Anzahl gültig ist oder nicht
     */
    private boolean checkArticleStockMatchPackagingUnit(int packagingUnit, int stockValue) {
        boolean result = true;

        if (stockValue % packagingUnit != 0) {
            result = false;
        }

        return result;
    }

    public Article getArticleByArticleNumber(int articleNumber) {
        Article articleResult = null;

        for (Article article : _articleObservableList) {
            if (article.getArticleNumber() == articleNumber) {
                articleResult = article;
                break;
            }
        }

        return articleResult;
    }

    public boolean checkArticleIsStock(Article article, int stockNumber) {
        return article.getStock() >= stockNumber;
    }

    // TODO: ERSTMALAUSKOMMENTIERT FÜR JAVA FX
    /**
     * Ein Comparator für Article-Objekte, damit diese nach ihren Eigenschaften sortiert werden können
     * Quelle: // https://stackoverflow.com/questions/2784514/sort-arraylist-of-custom-objects-by-property
     */
   /* private class ArticleComparator implements Comparator<Article> {
        private ArticleSortMode _articleSortMode;

        *//**
     * Im Konstruktor wird das Schema, der SortMode als Member gesetzt
     *
     * @param articleSortMode Das Schema nachdem sortiert werden soll
     *//*
        public ArticleComparator(ArticleSortMode articleSortMode) {
            _articleSortMode = articleSortMode;
        }

        */

    /**
     * Je nachdem welcher SortMode in _articleSortMode festgelegt ist,
     * werden die Artikel anders verglichen.
     *
     * @param o1 Artikel 1 zum vergleichen
     * @param o2 Artikel 2 zum vergleichen
     * @return https://www.javatpoint.com/java-string-compareto
     *//*
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


    }*/
    private int generateArticleNumber() {
        int articleNumber = 0;
        for (Article article : _articleObservableList) {
            if (article.getArticleNumber() >= articleNumber) {
                articleNumber = article.getArticleNumber() + 1;
            }
        }
        return articleNumber;
    }

    public ObservableList<Article> getArticleList() {
        return _articleObservableList;
    }
}
