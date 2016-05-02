package pl.com.bottega.photostock.sales.api;

import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.Purchase;
import pl.com.bottega.photostock.sales.model.Reservation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Beata Iłowiecka on 02.05.16.
 */
public class ClientManagement {

    public void register(String name, String surname, String address){

    }

    public List<Reservation> findReservations(String clientNr){
        List<Reservation> reservations = new ArrayList<>();
        return reservations;
    }

    public List<Purchase> findPurrchases(String clientNr){
        List<Purchase> purchases = new ArrayList<>();
        return purchases;
    }
}
