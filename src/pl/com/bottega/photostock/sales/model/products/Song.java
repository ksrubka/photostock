package pl.com.bottega.photostock.sales.model.products;

import pl.com.bottega.photostock.sales.model.Money;

import java.time.Duration;

/**
 * Created by Beata Iłowiecka on 06.04.16.
 */
public class Song extends AbstractProduct {

    private String performer;
    private String title;
    private Duration duration;
    private Channel channel;

    public Song(String number, Money price, String performer, String title, Duration duration, Channel channel, boolean active) {
        super(number, price, active);
        this.performer = performer;
        this.title = title;
        this.duration = duration;
        this.channel = channel;
    }

    public Song(String number, Money price, String performer, String title, Duration duration, Channel channel) {
        this(number, price, performer, title, duration, channel, true);
    }

    public Song() {
        this("nr6", new Money(12), "Black keys", "Gold on the ceiling", Duration.ofSeconds(225), Channel.STEREO);
    }

    @Override
    public String[] export() {
        return new String[0];
    }
}