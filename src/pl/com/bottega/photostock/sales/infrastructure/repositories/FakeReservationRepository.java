package pl.com.bottega.photostock.sales.infrastructure.repositories;

import pl.com.bottega.photostock.sales.model.LightBox;
import pl.com.bottega.photostock.sales.model.Product;
import pl.com.bottega.photostock.sales.model.Reservation;

import java.util.*;

/**
 * Created by Beata Iłowiecka on 21.04.16.
 */
public class FakeReservationRepository implements ReservationRepository {

    private static Map<String, Reservation> fakeDatabase = new HashMap<>();

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
        if (reservation.getNumber() == null){
            reservation.setNumber(UUID.randomUUID().toString()); // symulacja generowania ID przez bazę danych
        }
        fakeDatabase.put(reservation.getNumber(), reservation);
    }

    @Override
    public Set<Reservation> getReservations() {
        Set<Reservation> reservations = new HashSet<>();
        for(Map.Entry<String, Reservation> entry : fakeDatabase.entrySet()){
            Reservation reservation = entry.getValue();
            reservations.add(reservation);
        }
        return reservations;
    }
}
