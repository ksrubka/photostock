package pl.com.bottega.photostock.sales.infrastructure.repositories;

import pl.com.bottega.photostock.sales.model.Reservation;

import java.util.Set;

/**
 * Created by Beata Iłowiecka on 21.04.16.
 */
public interface ReservationRepository {

    Reservation load(String number);
    void save(Reservation reservation);

    Set<Reservation> getReservations();
}
