package pl.com.bottega.photostock.sales.infrastructure.repositories;

import pl.com.bottega.photostock.sales.model.LightBox;
import pl.com.bottega.photostock.sales.model.Reservation;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Beata Iłowiecka on 21.04.16.
 */
public class FakeReservationRepository implements ReservationRepository {

    private static Map<String, Reservation> fakeDatabase = new HashMap<>();

    // inicjalizator klasy ale nie dla obiektów tej klasy
    static {
        ClientRepository clientRepository = new FakeClientRepository();
        ProductRepository productRepository = new FakeProductRepository();

        Reservation reservation1 = new Reservation(clientRepository.load("Pani Ela"), "nr1");
        Reservation reservation2 = new Reservation(clientRepository.load("Pan Jan"), "nr2");
        Reservation reservation3 = new Reservation(clientRepository.load("Pan Leszek"), "nr3");
        Reservation reservation4 = new Reservation(clientRepository.load("Pani Gosia"), "nr4");
        Reservation reservation5 = new Reservation(clientRepository.load("Pani Aniela"), "nr5");

        fakeDatabase.put(reservation1.getNumber(), reservation1);
        fakeDatabase.put(reservation2.getNumber(), reservation2);
        fakeDatabase.put(reservation3.getNumber(), reservation3);
        fakeDatabase.put(reservation4.getNumber(), reservation4);
        fakeDatabase.put(reservation5.getNumber(), reservation5);
    }
    @Override
    public Reservation load(String number) {
        Reservation reservation = fakeDatabase.get(number);
        if  (reservation == null){
            throw new RuntimeException("Reservation " + number + " does not exist");
        }
        return reservation;
    }

    @Override
    public void save(Reservation reservation) {
        fakeDatabase.put(reservation.getNumber(), reservation);
    }
}
