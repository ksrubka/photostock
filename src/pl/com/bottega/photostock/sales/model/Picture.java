package pl.com.bottega.photostock.sales.model;

/**
 * Created by Beata Iłowiecka on 12.03.2016.
 */
public class Picture {

    private String number;
    private String[] tags;
    private double price;
    private boolean active;

    public Picture(String number, double price, String[] tags, boolean active) {
        this.number = number;
        this.price = price;
        this.tags = tags;
        this.active = active;
    }

    public Picture(String number, double price, String[] tags) {
        this(number, price, tags, true);
    }

    public double calculatePrice(){

        return 0; //TODO dodaj obliczenia
    }

    public boolean isAvailable(){
        return active;
    }

    public void reservedPer(Client client){

    }

    public void unreservedPer(Client client){

    }

    public void soldPer(Client client){

    }

    public String getNumber() {
        return number;
    }

    public double getPrice() {
        return price;
    }

}
