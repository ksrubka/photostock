package pl.com.bottega.photostock.sales.api;

import pl.com.bottega.photostock.sales.infrastructure.repositories.*;
import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.Purchase;
import pl.com.bottega.photostock.sales.model.Reservation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Beata Iłowiecka on 02.05.16.
 */
public class ClientManagement {

    private ClientRepository clientRepository = new FakeClientRepository();
    private ReservationRepository reservationRepository = new FakeReservationRepository();
    private PurchaseRepository purchaseRepository = new FakePurchaseRepository();

    public void register(String name, String surname, String address){
        String fullName = name + " " + surname;
        Client client = new Client(fullName, address, 0, null);
        clientRepository.save(client);
    }

    public List<Reservation> findReservations(String clientNr){
        List<Reservation> reservations = new ArrayList<>();
        Client client = clientRepository.load(clientNr);
        for (Reservation reservation : reservationRepository.getReservations()){
            if (reservation.getOwner() == client){
                reservations.add(reservation);
            }
        }
        return reservations;
    }

    public List<Purchase> findPurchases(String clientNr){
        List<Purchase> purchases = new ArrayList<>();
        Client client = clientRepository.load(clientNr);
        for (Purchase purchase : purchaseRepository.getPurchases()){
            if (purchase.getOwner() == client){
                purchases.add(purchase);
            }
        }
        return purchases;
    }
}
