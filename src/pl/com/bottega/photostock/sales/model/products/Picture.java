package pl.com.bottega.photostock.sales.model.products;

/**
 * Created by Beata Iłowiecka on 12.03.2016.
 */
public class Picture extends AbstractProduct {

    private String[] tags;

    public Picture(String number, double price, String[] tags, boolean active) {
        super(number, price, active);
        this.tags = tags;
    }

    public Picture(String number, double price, String[] tags) {
        this(number, price, tags, true);
    }

    public Picture() {
        this("nr4", 13, new String[]{"mouse", "screen", "coffee", "crayons"}, true);
    }
}