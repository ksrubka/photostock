package pl.com.bottega.photostock.sales.api;

import pl.com.bottega.photostock.sales.infrastructure.repositories.fake.FakeClientRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.fake.FakeProductRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.fake.FakePurchaseRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.fake.FakeReservationRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ClientRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ProductRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.PurchaseRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ReservationRepository;
import pl.com.bottega.photostock.sales.model.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Beata Iłowiecka on 23.04.2016.
 */
public class PurchaseProcess {

    public ClientRepository clientRepository = new FakeClientRepository();
    public ReservationRepository reservationRepository = new FakeReservationRepository();
    public ProductRepository productRepository = new FakeProductRepository();
    public PurchaseRepository purchaseRepository = new FakePurchaseRepository();

    //add product to the reservation
    public void add(String clientNr, String productNr) {
        //if (security)
        Client client = clientRepository.load(clientNr);
        Reservation reservation = reservationRepository.findOpenPer(client);
        if (reservation == null)
            reservation = createReservation(clientNr);
        Product product = productRepository.load(productNr);
        reservation.add(product);
        product.reservePer(client);
        reservationRepository.save(reservation);
        productRepository.save(product);
    }
    private Reservation createReservation(String clientNr){
        Client client = clientRepository.load(clientNr);
        Reservation reservation = new Reservation(client);
        reservationRepository.save(reservation);
        return  reservation;
    }

    public Offer calculateOffer(String clientNr) {
        Client client = clientRepository.load(clientNr);
        Reservation reservation = reservationRepository.findOpenPer(client);
        checkNotNull(reservation, "%s client does not have opened reservation", clientNr);
        return reservation.generateOffer();
    }

    //Jeśli dany klient jest płatnikiem
    public void confirm(String clientNr) {
        Client client = clientRepository.load(clientNr);
        Reservation reservation = reservationRepository.findOpenPer(client);
        checkNotNull(reservation, "%s client does not have opened reservation", clientNr);
        confirm(client, reservation);
    }

    //Jeśli ktoś inny jest płatnikiem
    /*public void confirm(String clientNr, ) {
        Reservation reservation = reservationRepository.findOpenPer(reservationNr);
        Client client = clientRepository.load(payerNr);
        confirm(client, reservation);
    }*/

    private void confirm(Client client, Reservation reservation){
        Offer offer = reservation.generateOffer();
        client.charge(offer.calculateTotalCost(), "");
        Purchase purchase = new Purchase(client, offer.getItems());
        clientRepository.save(client);
        reservation.close();
        purchaseRepository.save(purchase);
        reservationRepository.save(reservation);
    }
}