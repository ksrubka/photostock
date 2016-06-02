package pl.com.bottega.photostock.sales.model.products;

import pl.com.bottega.photostock.sales.model.Money;

/**
 * Created by Beata Iłowiecka on 06.04.16.
 */
public class Song extends AbstractProduct {

    private String performer;
    private long length;
    private Channel channel;

    public Song(String number, String name, Money price, String performer, long length, Channel channel, boolean active) {
        super(number, name, price, active);
        this.performer = performer;
        this.length = length;
        this.channel = channel;
    }

    public Song(String number, String name, Money price, String performer, long length, Channel channel) {
        this(number, name, price, performer, length, channel, true);
    }

    public Song() {
        this("nr6", "Gold on the ceiling", new Money(12), "Black keys", 225, Channel.STEREO);
    }

    @Override
    public String[] export() {
        return new String[0];
    }
}