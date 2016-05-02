package pl.com.bottega.photostock.sales.model.products;

import java.time.Duration;

/**
 * Created by Beata Iłowiecka on 06.04.16.
 */
public class Song extends AbstractProduct{

    private String performer;
    private String title;
    private Duration duration;
    private CHANNEL channel;

    public Song(String name, String number, double price, String[] tags, String performer, String title, Duration duration, CHANNEL channel, boolean active) {
        super(name, number, price, tags, active);
        this.performer = performer;
        this.title = title;
        this.duration = duration;
        this.channel = channel;

    }

    public Song(String name, String number, double price, String[] tags, String performer, String title, Duration duration, CHANNEL channel) {
        this(name, number, price, tags, performer, title, duration, channel, true);
    }

    public Song(){
        this("Gold on the ceiling", "nr6", 12, new String[] {"black keys", "ceiling", "gold"}, "Black keys", "Gold on the ceiling", Duration.ofSeconds(225), CHANNEL.STEREO);
    }
}
