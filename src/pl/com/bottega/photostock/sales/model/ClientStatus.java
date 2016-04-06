package pl.com.bottega.photostock.sales.model;

/**
 * Created by Beata Iłowiecka on 06.04.16.
 */
public enum ClientStatus {

    STANDARD("standardowy"), VIP("vip"), GOLD("złoty"), SILVER("srebrny"), PLATINUM("platynowy");

    String polishStatus;

    private ClientStatus(String polishStatus){
        this.polishStatus = polishStatus;
    }

    public String getPolishString(){
        return polishStatus;
    }
}
