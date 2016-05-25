package pl.com.bottega.photostock.sales.infrastructure.repositories.fake;

import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ReservationRepository;
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
                    " does not exist", number);
        return reservation;
    }

    @Override
    public Reservation findOpenPer(Client client) {
        for (Reservation reservation : fakeDatabase.values()){
            if (reservation.getOwner().equals(client) && !reservation.isClosed())
                return reservation;
        }
        return null;
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

    public void destroyReservations(){
        fakeDatabase.clear();
    }

    @Override
    public List<Reservation> find(String clientNr) {
        List<Reservation> reservations = new ArrayList<>();
        for (Reservation reservation : fakeDatabase.values()){
            if (reservation.getOwner().getNumber().equals(clientNr))
                reservations.add(reservation);
        }
        return reservations;
    }
}
