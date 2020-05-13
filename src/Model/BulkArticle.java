package Model;

import java.io.Serializable;

public class BulkArticle extends Article implements Serializable {
    int _packagingUnit; // Verpackungseinheit

    public BulkArticle(String name, int articleNumber, int stock, double price, int packagingUnit) {
        super(name, articleNumber, stock, price);
        _packagingUnit = packagingUnit;
    }

    // Muss überschrieben werden, weil hier die Verpackungseinheit mit ausgegeben wird
    public String toString(boolean withStock) {
        String result = super.toString();

        if (withStock) {
            result += " " + _packagingUnit;
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
