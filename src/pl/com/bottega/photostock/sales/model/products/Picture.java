package pl.com.bottega.photostock.sales.model.products;

import pl.com.bottega.photostock.sales.model.Client;

import java.util.ArrayList;

/**
 * Created by Beata Iłowiecka on 12.03.2016.
 */
public class Picture implements Product{

    private String number;
    private String[] tags;
    private double price;
    private boolean active;
    // dodane pola dla implementacji metod reserve, sold
    private ArrayList<Client> reservedPer;
    private ArrayList<Client> soldPer;
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

    public double getPrice(){
        return price;
    }

    @Override
    public void cancel() {

    }

    public void reservePer(Client client) throws IllegalArgumentException {

        if (!canBeReservedBy(client)) {
            throw new IllegalArgumentException("Nie można zarezerwować.");
        }
        else {
            reservedPer.add(client);
        }
    }

    public boolean canBeReservedBy(Client client){
        return client.canAfford(price) && isActive() && isSoldOut() && (!isReservedPerVip());
    }

    public boolean isActive() {

        return active;
    }

    public boolean isSoldOut() {

        if (shared == false && !soldPer.isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isReservedPerVip(){

        if (reservedPer.isEmpty()){
            return false;
        }
        else {
            for (Client client : reservedPer){
                if (client.isVip()){
                    return true;
                }
                continue;
            }
            return false;
        }
    }

    public void unreservePer(Client client) throws IllegalArgumentException{

        //TODO po co klient jako parametr? nie może być puste? (nie, bo czasem jest kilku klientów?)

        boolean removed = reservedPer.remove(client);

        if (!removed){
            throw new IllegalArgumentException("Klient " + client.getName() +" nie rezerwował tego zdjęcia");
        }
    }

    public void sellPer(Client client){

        soldPer.add(client);  //TODO zabezpieczyć zmienną przed zmianami? ;)
    }

    public String getNumber(){
        return number;
    }


    public boolean isAvailable(){
        return active;
    }

}
