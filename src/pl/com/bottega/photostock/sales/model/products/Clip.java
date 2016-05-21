package pl.com.bottega.photostock.sales.model.products;

import java.time.Duration;

/**
 * Created by Beata Iłowiecka on 06.04.16.
 */
public class Clip extends AbstractProduct{

    private Duration duration;

    public Clip(String number, double price, Duration duration, boolean active){
        super(number, price, active);
        this.duration = duration;
    }

    public  Clip(String number, double price, Duration duration){
        this(number, price, duration, true);
    }

    public Clip(){
        this("nr5", 10, Duration.ofSeconds(224), true);
    }
}
