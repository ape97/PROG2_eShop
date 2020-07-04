package Utilities;

import Model.Article;
import Model.BulkArticle;

/**
 * [WICHTIG]
 * <p>
 * Verwendungsgrund dieser Klasse:
 * Auf der GUI (FXML) werden die Tabellen gefüllt, indem definiert wird welche Spalte welchen Getter eines Artikel anzeigt.
 * Das Problem dabei, es gibt Article und BulkArticle. Ein BulkArticle erbt von Article, daher ist es kein Problem
 * diese zusammen in einer Tabelle anzuzeigen. ABER ein BulkArticle hat eine Eigenschaft mehr, die Verpackungseinheit.
 * <p>
 * Damit nun für Article allgemein eine Verpackungseinheit angezeigt werden kann, gibt es diese Extension-Methode (getPackagingUnit).
 * Der Tabelle wird in der Spalte Verpackungseinheit statt einem Getter, der Aufruf dieser Funktion zugeordnet.
 */
public class ArticleExtension {
    private ArticleExtension() { // private, weil statisch
    }

    /**
     * Diese Funktion prüft ob der Article ein BulkArticle ist und gibt dessen Verpackungseinheit zurück
     * oder gibt 1 zurück, falls es sich um einen normalen Artikel handelt.
     *
     * @param article Der zu prüfende Artikel
     * @return Die Verpackungseinheit des Artikels
     */
    public static int getPackagingUnit(Article article) {
        int unit = 1;

        String className = article.getClass().getSimpleName();

        if (className.equals(BulkArticle.class.getSimpleName())) {
            unit = ((BulkArticle) article).getPackagingUnit();
        }

        return unit;
    }
}
