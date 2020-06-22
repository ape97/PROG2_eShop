package Utilities;

import Model.Article;
import Model.BulkArticle;

public class ArticleExtension {
    private ArticleExtension() {
    } // static

    public static int getPackagingUnit(Article article) {
        int unit = 1;

            String className = article.getClass().getSimpleName();

            if (className.equals(BulkArticle.class.getSimpleName())) {
                unit = ((BulkArticle)article).getPackagingUnit();
            }

        return unit;
    }
}
