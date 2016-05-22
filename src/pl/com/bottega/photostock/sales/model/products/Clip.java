package pl.com.bottega.photostock.sales.model.products;

import pl.com.bottega.photostock.sales.model.Money;

/**
 * Created by Beata Iłowiecka on 06.04.16.
 */
public class Clip extends AbstractProduct{

    private long length;

    public Clip(String number, Money price, long duration, boolean active){
        super(number, price, active);
        this.length = duration;
    }

    public  Clip(String number, Money price, long duration){
        this(number, price, duration, true);
    }

    public Clip(){
        this("nr5", new Money(10.0), 224, true);
    }

}
