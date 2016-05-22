package pl.com.bottega.photostock.sales.model.products;

import pl.com.bottega.photostock.sales.model.Money;

import java.time.Duration;

/**
 * Created by Beata Iłowiecka on 06.04.16.
 */
public class Clip extends AbstractProduct{

    private Duration duration;

    public Clip(String number, Money price, Duration duration, boolean active){
        super(number, price, active);
        this.duration = duration;
    }

    public  Clip(String number, Money price, Duration duration){
        this(number, price, duration, true);
    }

    public Clip(){
        this("nr5", new Money(10.0), Duration.ofSeconds(224), true);
    }

}
