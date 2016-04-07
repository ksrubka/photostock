package pl.com.bottega.photostock.sales.model.products;

import pl.com.bottega.photostock.sales.model.Client;

import java.util.ArrayList;

/**
 * Created by Beata Iłowiecka on 06.04.16.
 */
public abstract class AbstractProduct implements Product {

    private String number;
    private String[] tags;
    private double price;
    private boolean active;
    // dodane pola dla implementacji metod reserve, sold
    private ArrayList<Client> reservedPer;
    private ArrayList<Client> soldPer;
    private boolean shared;

    public boolean isAvailable(){
        return active;
    }

    public double calculatePrice() {
        return 0;
    }

    public double getPrice(){
        return price;
    }

    //make inactive
    public void cancel(){
        active = false;
    }

    public void reservePer(Client client){

    }

    public void unreservePer(Client client){

    }

    public boolean canBeReservedBy(Client client);

}
