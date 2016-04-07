package pl.com.bottega.photostock.sales.model.products;

import pl.com.bottega.photostock.sales.model.Client;

import java.time.Duration;
import java.util.ArrayList;


/**
 * Created by Beata Iłowiecka on 06.04.16.
 */
public class Clip extends AbstractProduct{

    private Duration duration;

    public Clip(String number, double price, String[] tags, Duration duration, boolean active){
        super(number, price, tags, active);
        this.duration = duration;
    }

    public  Clip(String number, double price, String[] tags, Duration duration){
        this(number, price, tags, duration, true);
    }

    public Clip(){
        this("01a", 10, new String[] {"Boy", "clapping", "numbers"}, Duration.ofSeconds(224), true);
    }
}
