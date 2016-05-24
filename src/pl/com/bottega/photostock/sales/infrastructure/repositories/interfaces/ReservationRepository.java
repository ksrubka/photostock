package pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces;

import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.Reservation;

import java.util.Collection;
import java.util.List;

/**
 * Created by Beata Iłowiecka on 21.04.16.
 */
public interface ReservationRepository {

    Reservation load(String number);
    Reservation findOpenPer(Client client);

    void save(Reservation reservation);

    Collection<Reservation> getReservations();

    void destroyReservations();

    List<Reservation> find(String clientNr);
}
