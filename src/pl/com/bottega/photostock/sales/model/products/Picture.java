package pl.com.bottega.photostock.sales.model.products;

/**
 * Created by Beata Iłowiecka on 12.03.2016.
 */
public class Picture extends AbstractProduct {

    public Picture(String number, double price, String[] tags, boolean active) {
        super(number, price, tags, active);
    }

    public Picture(String number, double price, String[] tags) {
        this(number, price, tags, true);
    }

    public Picture() {
        this("nr4", 13, new String[]{"mouse", "screen", "coffee", "crayons"}, true);
    }
}