package Model;

public class BulkArticle extends Article {
    private int _packagingUnit; // Verpackungseinheit

    public BulkArticle(String name, int articleNumber, int stock, double price, int packagingUnit) {
        super(name, articleNumber, stock, price);
        _packagingUnit = packagingUnit;
    }

    // Muss überschrieben werden, weil hier die Verpackungseinheit mit ausgegeben wird
    public String toString(boolean withStock) {
        String result = getArticleNumber() + " - " + getName() + " - " + getPrice() + "€";

        if (withStock) {
            result += " - " + getStock() + " " + _packagingUnit;
        }

        return result;
    }

    public void setPackagingUnit(int packagingUnit) {
        _packagingUnit = packagingUnit;
    }
    public int getPackagingUnit() {
        return _packagingUnit;
    }
}
