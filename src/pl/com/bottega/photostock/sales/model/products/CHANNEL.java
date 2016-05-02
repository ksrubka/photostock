package pl.com.bottega.photostock.sales.model.products;

/**
 * Created by Beata Iłowiecka on 02.05.16.
 */
public enum CHANNEL{
    STEREO("2.0"), FIVE_ONE("5.1"), SEVEN_ONE("7.1");

    private String channel;

    private CHANNEL(String channel){
        this.channel = channel;
    }

    public String getChannel(){
        return channel;
    }
}