package pl.com.bottega.photostock.sales.model.products;

import pl.com.bottega.photostock.sales.model.Money;

/**
 * Created by Beata Iłowiecka on 06.04.16.
 */
public class Clip extends AbstractProduct{

    private long length;

    public Clip(String number, String name, Money price, long duration, boolean active){
        super(number, name, price, active);
        this.length = duration;
    }

    public  Clip(String number, String name, Money price, long duration){
        this(number, name, price, duration, true);
    }

    public Clip(){
        this("nr5", "some clip", new Money(10.0), 224, true);
    }

    public long getLength() {
        return length;
    }

    @Override
    public String[] export() {
        Money price = getPrice();
        return new String[] {
                getNumber(),
                String.valueOf(price.cents()),
                String.valueOf(price.getCurrency()),
                String.valueOf(isAvailable()),
                String.valueOf(length),
                "",
                "Clip"
        };
    }
}
