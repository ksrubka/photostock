package pl.com.bottega.photostock.sales.model.products;

import pl.com.bottega.photostock.sales.model.Client;

import java.util.ArrayList;

/**
 * Created by Beata Iłowiecka on 06.04.16.
 */
public abstract class AbstractProduct implements Product {

    protected String number;
    protected String[] tags;
    protected double price;
    protected boolean active;
    protected ArrayList<Client> reservedPer = new ArrayList<>();
    public ArrayList<Client> soldPer = new ArrayList<>();;
    protected boolean shared;

    public AbstractProduct(String number, double price, String[] tags, boolean active) {
        this.number = number;
        this.price = price;
        this.tags = tags;
        this.active = active;
    }

    public boolean isAvailable(){
        return active;
    }

    public double calculatePrice() {
        return 0;
        //TODO do implementacji gdy będą dostępne rabaty i rozdzielczości i cena będzie zależeć od nich.
    }

    public double getPrice(){
        return price;
    }

    public String getNumber(){
        return number;
    }

    //make inactive
    public void cancel(){
        active = false;
    }

    public void reservePer(Client client) throws IllegalArgumentException {
        if (!canBeReservedBy(client)) {
            throw new IllegalArgumentException("Nie można zarezerwować.");
        }
        else {
            reservedPer.add(client);
        }
    }

    public void unreservePer(Client client) throws IllegalArgumentException {
        boolean removed = reservedPer.remove(client);

        if (!removed){
            throw new IllegalArgumentException("Klient " + client.getName() +" nie rezerwował tego zdjęcia");
        }
    }

    public boolean canBeReservedBy(Client client){
        return client.canAfford(price) && isAvailable() && (!isSoldOut()) && (!isReservedPerVip());
    }

    public boolean isSoldOut() {
        if (shared == false && !soldPer.isEmpty()){
            return true;
        }
        else {
            return false;
        }
    }

    private boolean isReservedPerVip() throws IllegalStateException {

        if (reservedPer.isEmpty()){
            return false;
        }
        else {
            for (Client client : reservedPer){
                if (client.isVip()){
                    return true;
                }
            }
            throw new IllegalStateException("Zdjęcie jest już zarezerwowane");
        }
    }

    public void sellPer(Client client) throws IllegalStateException {

        if (canBeReservedBy(client)) {
            soldPer.add(client);
        }
        else {
            throw new IllegalStateException("Nie można sprzedać.");
        }
    }

}
