package pl.com.bottega.photostock.sales.model.products;

import pl.com.bottega.photostock.sales.model.Money;

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

    @Override
    public String[] export() {
        Money price = getPrice();
        String tagsJoined = String.join(" ", this.getTags());
        return new String[] {
                getNumber(),
                String.valueOf(price.cents()),
                String.valueOf(price.getCurrency()),
                String.valueOf(isAvailable()),
                "",
                tagsJoined,
                "Picture"
        };
    }

    @Override
    public String[] getTags() {
        return tags;
    }
}