package pl.com.bottega.photostock.sales.infrastructure.repositories;

import com.sun.org.apache.regexp.internal.RE;
import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.Reservation;
import pl.com.bottega.photostock.sales.model.exceptions.DataDoesNotExistException;

import java.util.*;

/**
 * Created by Beata Iłowiecka on 21.04.16.
 */
public class FakeReservationRepository implements ReservationRepository {

    private static Map<String, Reservation> fakeDatabase = new HashMap<>();

    @Override
    public Reservation load(String number) throws DataDoesNotExistException {
        Reservation reservation = fakeDatabase.get(number);
        if  (reservation == null)
            throw new DataDoesNotExistException("Reservation " + number +
                    " does not exist", number, reservation.getClass());
        return reservation;
    }

    @Override
    public Reservation load(Client client) throws DataDoesNotExistException {
        for (Reservation reservation : fakeDatabase.values()){
            if (reservation.getOwner().equals(client))
                return reservation;
        }
        throw new DataDoesNotExistException("Reservation for " + client.getName() +
                " does not exist", client.getNumber(), client.getClass());
    }

    @Override
    public void save(Reservation reservation) {
        if (reservation.getNumber() == null)
            reservation.setNumber(UUID.randomUUID().toString()); // symulacja generowania ID przez bazę danych
        fakeDatabase.put(reservation.getNumber(), reservation);
    }

    @Override
    public Collection<Reservation> getReservations() {
        return fakeDatabase.values();
    }
}
