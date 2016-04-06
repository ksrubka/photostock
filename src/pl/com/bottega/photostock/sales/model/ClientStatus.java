package pl.com.bottega.photostock.sales.model;

/**
 * Created by Beata Iłowiecka on 06.04.16.
 */
public enum ClientStatus {

    STANDARD("standardowy", "standard"), VIP("vip", "vip"), GOLD("złoty", "gold"), SILVER("srebrny", "silver"), PLATINUM("platynowy", "platinum");

    String polishStatus;
    String englishStaus;

    private ClientStatus(String polishStatus, String englishStaus){
        this.polishStatus = polishStatus;
        this.englishStaus = englishStaus;
    }

    public String getPolishString(){
        return polishStatus;
    }

    public String getEnglishStaus() { return englishStaus; }
}
