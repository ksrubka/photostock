package pl.com.bottega.photostock.sales.api;

import pl.com.bottega.photostock.sales.infrastructure.repositories.fake.FakeClientRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.fake.FakePurchaseRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.fake.FakeReservationRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ClientRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.PurchaseRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ReservationRepository;
import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.Money;
import pl.com.bottega.photostock.sales.model.Purchase;
import pl.com.bottega.photostock.sales.model.Reservation;

import java.util.List;

/**
 * Created by Beata Iłowiecka on 02.05.16.
 */
public class ClientManagement {

    private ClientRepository clientRepository = new FakeClientRepository();
    private ReservationRepository reservationRepository = new FakeReservationRepository();
    private PurchaseRepository purchaseRepository = new FakePurchaseRepository();

    public String register(String name, String surname, String address, String nr){
        String fullName = name + " " + surname;
        Client client = new Client(fullName, address, 0, "nr1");
        clientRepository.save(client);
        return client.getNumber();
    }

    public List<Reservation> findReservations(String clientNr){
        return reservationRepository.find(clientNr);
    }

    public List<Purchase> findPurchases(String clientNr){
        return purchaseRepository.find(clientNr);
    }

    public void recharge(String clientNr, Money amount){
        Client client = clientRepository.load(clientNr);
        client.recharge(amount);
    }
}
