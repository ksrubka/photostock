package pl.com.bottega.photostock.sales.infrastructure.repositories;

import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.Reservation;

import java.util.Collection;

/**
 * Created by Beata Iłowiecka on 21.04.16.
 */
public interface ReservationRepository {

    Reservation load(String number);
    Reservation load(Client client);

    void save(Reservation reservation);

    Collection<Reservation> getReservations();

    Reservation getReservationByOwner(Client client);
}
