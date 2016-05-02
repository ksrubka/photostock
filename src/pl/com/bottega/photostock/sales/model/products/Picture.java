package pl.com.bottega.photostock.sales.model.products;

import pl.com.bottega.photostock.sales.model.Client;

import java.util.ArrayList;

/**
 * Created by Beata Iłowiecka on 12.03.2016.
 */
public class Picture extends AbstractProduct {

    public Picture(String name, String number, double price, String[] tags, boolean active) {
        super(name, number, price, tags, active);
    }

    public Picture(String name, String number, double price, String[] tags) {
        this(name, number, price, tags, true);
    }

    public Picture() {
        this("programming", "nr4", 13, new String[]{"mouse", "screen", "coffee", "crayons"}, true);
    }

}
