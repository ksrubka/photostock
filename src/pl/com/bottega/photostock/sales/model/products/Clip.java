package pl.com.bottega.photostock.sales.model.products;

import pl.com.bottega.photostock.sales.model.Client;

import java.time.Duration;
import java.util.ArrayList;


/**
 * Created by Beata Iłowiecka on 06.04.16.
 */
public class Clip implements Product{

    private String title;
    private Duration length;
    private String number;
    private String[] tags;
    private double price;
    private boolean active;
    private ArrayList<Client> reservedPer;
    private ArrayList<Client> soldPer;
    private boolean shared;


    @Override
    public boolean isAvailable() {
        return active;
    }

    @Override
    public double calculatePrice() {
        return 0;
    }

    @Override
    public double getPrice() {
        return 0;
    }

    @Override
    public void cancel() {

    }

    @Override
    public void reservePer(Client client) {

    }

    @Override
    public void unreservePer(Client client) {

    }

    @Override
    public boolean canBeReservedBy(Client client) {
        return false;
    }

}
