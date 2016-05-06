package pl.com.bottega.photostock.sales.api;

import pl.com.bottega.photostock.sales.infrastructure.repositories.ClientRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.FakeClientRepository;
import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.Purchase;
import pl.com.bottega.photostock.sales.model.Reservation;
import pl.com.bottega.photostock.sales.model.ClientStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Beata Iłowiecka on 02.05.16.
 */
public class ClientManagement {

    private ClientRepository clientRepository = new FakeClientRepository();

    public void register(String name, String surname, String address){
        String fullName = name + " " + surname;
        Client client = new Client(fullName, address, 0, null);
        clientRepository.save(client);
    }

    public List<Reservation> findReservations(String clientNr){
        List<Reservation> reservations = new ArrayList<>();
        Client client = clientRepository.load(clientNr);

        return reservations;
    }

    public List<Purchase> findPurrchases(String clientNr){
        List<Purchase> purchases = new ArrayList<>();
        return purchases;
    }
}
