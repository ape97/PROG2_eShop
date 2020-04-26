package Controller;

import Model.Article;
import Utilities.BooleanString;
import Utilities.BooleanStringObject;

import java.util.ArrayList;

/**
 * WARNING: Sollte nur vom MainController verwendet werden
 * Verwaltet die Article-Objekte.
 */
public class ArticleController {

    private ArrayList<Article> _articleList;

    /**
     * Der Konstruktor erzeugt eine leere ArrayList _articleList für Article-Objekte.
     */
    public ArticleController(){
        _articleList = new ArrayList<>();
    }

    /**
     * Neuer Artikel:
     * Prüft die Parameter und erzeugt ein neues Article-Objekt und fügt dieses der ArrayList _articleList hinzu.
     *
     * @param name Die Bezeichnung des Artikels
     * @param articleNumber  Die Artikelnummer des Artikels
     * @param stock  Der Lagerbestand des Artikels (stk.)
     * @param price  Der Preis des Artikels
     * @return Gibt ein BooleanString-Objekt zurück.
     * Der boolean gibt an, ob das erzeugen des Article-Objekts erfolgreich war oder nicht.
     * Der String gibt die entsprechende (Fehler-) Meldung an.
     * Das Object gibt ist das erstellte Article-Object, ansonsten null.
     */
    public BooleanStringObject addArticle(String name, int articleNumber, int stock, double price){
        BooleanStringObject booleanStringObjectResult = new BooleanStringObject(false, "", null);

        if (name.trim().isEmpty()) {
            booleanStringObjectResult.setValueS("Artikel Bezeichnung darf nicht leer sein.");
        } else if (price <= 0.0) {
            booleanStringObjectResult.setValueS("Der Preis muss größer als 0 sein.");
        } else if (!checkArticleNumberExists(articleNumber)) {
            booleanStringObjectResult.setValueS("Die Artikelnummer ist bereits vergeben.");
        } else {
            Article article = new Article(name,articleNumber, stock, price);
            _articleList.add(article);

            booleanStringObjectResult.setValueB(true);
            booleanStringObjectResult.setValueS("Der Artikel wurde erfolgreich erstellt.");
            booleanStringObjectResult.setValueO(article);
        }

        return booleanStringObjectResult;
    }

    /**
     * Bestandsänderung des Artikels
     * @param article Der Artikel dessen Bestand sich verändern soll
     * @param stockChangeValue Die Anzahl um die sich der Bestand verändern soll
     * @return Gibt ein BooleanString-Objekt zurück.
     * Der boolean gibt an, ob der Bestand erfolgreich geändert wurde.
     * Der String gibt die entsprechene (Fehler-) Meldung an.
     */
    public BooleanString updateStock(Article article, int stockChangeValue){
        BooleanString booleanStringResult = new BooleanString(false, "");

        if(stockChangeValue == 0){
            booleanStringResult.setValueS("Die Bestandsveränderung darf nicht 0 sein.");
        } else{
            article.setStock(article.getStock() + stockChangeValue);
        }

        return booleanStringResult;
    }

    /**
     * Prüft ob die angegebene ARtikelnummer bereits einem Article-Objekt zugeordnet ist.
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

    // TODO: GET SORTED LIST
}
