package pl.com.bottega.photostock.sales.model;

import java.util.ArrayList;

/**
 * Created by Beata Iłowiecka on 12.03.2016.
 */
public class Picture {

    private String number;
    private String[] tags;
    private double price;
    private boolean active;
    // dodane pola dla implementacji metod reserve, sold
    private ArrayList<Client> reservedPer;
    private Client sold;
    private boolean shared;

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
        return 0; //TODO do implementacji gdy będą dostępne rabaty i rozdzielczości i cena będzie zależeć od nich.
    }

    public void reservePer(Client client) throws IllegalArgumentException{

        boolean canReserve = client.canAfford(price) && active && ((sold == null) || (sold != null && !shared));

        if (canReserve && reservedPer.isEmpty()){
            reservedPer.add(client);
        }
        else if (canReserve && !reservedPer.isEmpty()){
            boolean reservedPerVip = reservedPer.get(0).isVip();
            if (reservedPerVip){
                throw new IllegalArgumentException("Nie można zarezerwować. Ktoś Cię uprzedził.");
            }
            else {
                reservedPer.add(client);
            }
        }
        else {
            throw new IllegalArgumentException("Nie można zarezerwować.");
        }
    }

    public void unreservePer(Client client) throws IllegalArgumentException{

        //TODO po co klient jako parametr? nie może być puste? (nie, bo czasem jest kilku klientów?)

        boolean removed = reservedPer.remove(client);

        if (!removed){
            throw new IllegalArgumentException("Klient " + client.getName() +" nie rezerwował tego zdjęcia");
        }
    }

    public void soldPer(Client client){

        sold = client;  //TODO zabezpieczyć zmienną przed zmianami? ;)
    }

    public String getNumber(){
        return number;
    }

    public double getPrice(){
        return price;
    }

    public boolean isAvailable(){
        return active;
    }

}
