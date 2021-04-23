package Controller;

import Model.Article;
import Model.BulkArticle;
import Utilities.*;

import java.util.ArrayList;

/**
 * WARNING: Sollte nur vom MainController verwendet werden
 * Verwaltet die Article-Objekte.
 */
public class ArticleController {

    public ArticleController() {
    }

    /**
     * Neuer Artikel:
     * Prüft die Parameter und erzeugt ein neues Article-Objekt und fügt dieses den Daten hinzu.
     *
     * @param name          Die Bezeichnung des Artikels
     * @param stock         Der Lagerbestand des Artikels (stk.)
     * @param price         Der Preis des Artikels
     * @param packagingUnit Die Verpackungseinheit des Artikels (relevant für BullArticle)
     * @return Gibt ein Result-Void zurück, welches aussagt, ob die Aktion erfolgreich oder nicht war inkl. Meldung.
     *         Über die getObject()-Methode gibt bei Erfolg der Aktion den neuen Artikel zurück.
     */
    public Result<Article> addArticle(String name, String stock, String price, String packagingUnit) {

        Result<Void> checkArticleValues = checkArticleValues(name, stock, price, packagingUnit);
        if (checkArticleValues.getState() == Result.State.FAILED) {
            return new Result<Article>(checkArticleValues.getState(), checkArticleValues.getMessage(), null);
        } else {
            Article article;
            int articleNumber = generateArticleNumber();
            // Entescheidung ob ein Article oder ein BulkArticle Objekt erstellt werden soll
            // Wenn die angegebene Verpackungseinheit größer als 1 ist, wird ein BulkArticle erstellt (>=1 wird immer als normaler Article erstellt)
            if (Parse.parseInteger(packagingUnit) > 1) {
                article = new BulkArticle(name, articleNumber, Parse.parseInteger(stock), Parse.parseDouble(price), Parse.parseInteger(packagingUnit));
            } else {
                article = new Article(name, articleNumber, Parse.parseInteger(stock), Parse.parseDouble(price));
            }
            DataController.getInstance().getArticleList().add(article);
            System.out.println(article.getArticleNumber());

            return new Result<Article>(Result.State.SUCCESSFUL, Message.get(Message.MessageType.Info_ArticleCreated), article);
        }

    }

    /**
     * Prüft die übergebenen Artikeldaten auf Ihre Zulässigkeit und gibt dementsprechend ein Result-Void zurück.
     *
     * @param name          Artikelbezeichnung
     * @param stock         Lagerbestand
     * @param price         Preis
     * @param packagingUnit Verpackungseinheit
     * @return Gibt den Erfolgsstaus der Aktion als Result<Void> zurück.
     */
    private Result<Void> checkArticleValues(String name, String stock, String price, String packagingUnit) {

        Result<Integer> stockParseResult = Parse.tryParseInt(stock);
        if (stockParseResult.getState() == Result.State.FAILED) {
            return new Result<Void>(Result.State.FAILED, "Anzahl kein gültiger Ganzzahlenwert.", null);
        }

        Result<Double> priceParseResult = Parse.tryParseDouble(price);
        if (priceParseResult.getState() == Result.State.FAILED) {
            return new Result<Void>(Result.State.FAILED, "Preis kein gültiger Fließkommazahlenwert.", null);
        }

        Result<Integer> packagingUnitParseResult = Parse.tryParseInt(packagingUnit);
        if (packagingUnitParseResult.getState() == Result.State.FAILED) {
            return new Result<Void>(Result.State.FAILED, "Verpackungseinheit kein gültiger Ganzzahlenwert.", null);
        }

        if (name.trim().isEmpty()) {
            return new Result<Void>(Result.State.FAILED, Message.get(Message.MessageType.Error_ArticleNameNotEmpty), null);
        }

        if (priceParseResult.getObject() <= 0.0) {
            return new Result<Void>(Result.State.FAILED, Message.get(Message.MessageType.Error_ArticlePriceGreaterThanZero), null);
        }

        if (!checkArticleStockMatchPackagingUnit(packagingUnitParseResult.getObject(), stockParseResult.getObject())) {
            return new Result<Void>(Result.State.FAILED, Message.get(Message.MessageType.Error_ArticleStockNotMatchPackagingUnit), null);
        }

        return new Result<Void>(Result.State.SUCCESSFUL, "", null);
    }

    /**
     * Artikel entfernen
     * <p>
     * Entfernt das Article-Objekt mit übereinstimmender Artikelnummer aus den Daten.
     *
     * @param articleNumber Die Artikelnummer des zu entfernenden Artikels
     * @return Gibt ein Result-Article zurück, welches aussagt, ob die Aktion erfolgreich oder nicht war inkl. Meldung.
     *         Über die getObject()-Methode gibt bei Erfolg der Aktion den entfernten Artikel zurück.
     */
    public Result<Article> removeArticle(String articleNumber) {

        Result<Integer> parseArticleNumberResult = Parse.tryParseInt(articleNumber);
        if (parseArticleNumberResult.getState() == Result.State.FAILED) {
            return new Result<Article>(Result.State.FAILED, "Artikelnummer ist kein gültiger Ganzzahlenwert.", null);
        }

        Article article = getArticleByArticleNumber(Parse.parseInteger(articleNumber));
        if (article == null) {
            return new Result<Article>(Result.State.FAILED, "Kein Artikel mit dieser Artikelnummer gefunden.", null);
        }

        DataController.getInstance().getArticleList().remove(article);
        return new Result<Article>(Result.State.SUCCESSFUL, Message.get(Message.MessageType.Info_ArticleRemoved), article);
    }


    /**
     * Bestandsänderung des Artikels
     *
     * @param article Der Artikel dessen Bestand sich verändern soll
     * @param stockChangeValue Die Anzahl um die sich der Bestand verändern soll
     * @return Gibt ein Result-Void zurück, welches aussagt, ob die Aktion erfolgreich oder nicht war inkl. Meldung.
     */
    public Result<Void> updateStock(Article article, int stockChangeValue) {
        Result<Void> result = new Result<Void>(Result.State.FAILED, "", null);

        if (stockChangeValue == 0) {
            result.setMessage(Message.get(Message.MessageType.Error_ChangeValueNotZero));
        } else if (!checkArticleStockMatchPackagingUnit(article, stockChangeValue)) {
            result.setMessage(Message.get(Message.MessageType.Error_ArticleStockNotMatchPackagingUnit));
        } else {
            result.setState(Result.State.SUCCESSFUL);
            result.setMessage(Message.get(Message.MessageType.Info_ArticleStockChanged));
            article.setStock(article.getStock() + stockChangeValue);
        }

        return result;
    }

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

        for (Article article : DataController.getInstance().getArticleList()) {
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

    /**
     * Ermittelt das Article-Objekt, welches der übergebenen Artikelnummer zugeordnet ist
     * @param articleNumber Die Artikelnummer
     * @return Der Artikel der mit der entsprechenden Artikelnummer, null falls dieser nicht extistiert
     */
    public Article getArticleByArticleNumber(int articleNumber) {
        Article articleResult = null;

        for (Article article : DataController.getInstance().getArticleList()) {
            if (article.getArticleNumber() == articleNumber) {
                articleResult = article;
                break;
            }
        }

        return articleResult;
    }

    /**
     * Prüft, on eine bestimmte Anzaahl eines Artikels auf Lager ist.
     * @param article Der Artikel
     * @param stockNumber Die Anzahl des Artikels
     * @return Der Wahrheitswert sagt aus, ob der Artikel auf Lager ist (true) oder nicht (false)
     */
    public boolean checkArticleIsStock(Article article, int stockNumber) {
        return article.getStock() >= stockNumber;
    }

    /**
     * Generiert basierend auf den Artikeldaten eine Artikelnummer, die aktuell nicht in Verwendung ist
     * Nachteil: Nach Löschung des Artikels mit der größten Artikelnummer, könnte diese wieder an einen neuen Artikel vergeben werden.
     *           Da immer von der höchsten Nummer aus, weiter gezählt wird.
     * @return Gibt die generierte Artikelnummer zurück
     */
    private int generateArticleNumber() {
        int articleNumber = 0;
        for (Article article : DataController.getInstance().getArticleList()) {
            if (article.getArticleNumber() >= articleNumber) {
                articleNumber = article.getArticleNumber() + 1;
            }
        }
        return articleNumber;
    }

    /**
     * Gibt die Artikeldaten, also die Artikelliste zurück
     * @return Artikelliste
     */
    public ArrayList<Article> getArticleList() {
        return DataController.getInstance().getArticleList();
    }
}
