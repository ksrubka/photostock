package pl.com.bottega.photostock.sales.api;

import pl.com.bottega.photostock.sales.infrastructure.repositories.*;
import pl.com.bottega.photostock.sales.model.*;

/**
 * Created by Beata Iłowiecka on 23.04.2016.
 */
public class PurchaseProcess {

    private ClientRepository clientRepository = new FakeClientRepository();
    private ReservationRepository reservationRepository = new FakeReservationRepository();
    private ProductRepository productRepository = new FakeProductRepository();
    private PurchaseRepository purchaseRepository = new FakePurchaseRepository() ;

    public String create(String clientNr){
        Client client = clientRepository.load(clientNr);
        Reservation reservation = new Reservation(client);
        reservationRepository.save(reservation);
        return  reservation.getNumber();
    }

    public void add(String reservationNr, String productNr){
        Reservation reservation = reservationRepository.load(reservationNr);
        Product product = productRepository.load(productNr);

        reservation.add(product);

        reservationRepository.save(reservation);
        productRepository.save(product);
    }

    public Offer calculateOffer(String reservationNr) {
        Reservation reservation = reservationRepository.load(reservationNr);
        return reservation.generateOffer();
    }

    public void confirm(String reservationNr) {
        Reservation reservation = reservationRepository.load(reservationNr);
        Client client = reservation.getOwner();
        confirm(client, reservation);
    }

    public void confirm(String reservationNr, String payerNr) {
        Reservation reservation = reservationRepository.load(payerNr);
        Client client = clientRepository.load(payerNr);
        confirm(client, reservation);

    }

    private void confirm(Client client, Reservation reservation){
        Offer offer = reservation.generateOffer();
        client.charge(offer.calculateTotalCost(), "");
        Purchase purchase = new Purchase(client, offer.getItems());
        clientRepository.save(client);
        reservationRepository.save(reservation);
        purchaseRepository.save(purchase);
    }
}