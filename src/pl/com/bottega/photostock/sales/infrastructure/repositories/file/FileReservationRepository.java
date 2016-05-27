package pl.com.bottega.photostock.sales.infrastructure.repositories.file;

import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ClientRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ProductRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ReservationRepository;
import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.Reservation;

import java.util.Collection;
import java.util.List;

/**
 * Created by Beata Iłowiecka on 24.05.16.
 */
public class FileReservationRepository implements ReservationRepository {

    private final String path;
    ClientRepository clientRepository;
    ProductRepository productRepository;

    public FileReservationRepository(String path, ClientRepository clientRepository, ProductRepository productRepository) {
        this.path = path;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Reservation load(String number) {
        return null;
    }

    @Override
    public Reservation findOpenPer(Client client) {
        return null;
    }

    @Override
    public void save(Reservation reservation) {

    }

    @Override
    public Collection<Reservation> getReservations() {
        return null;
    }

    @Override
    public void destroyReservations() {

    }

    @Override
    public List<Reservation> find(String clientNr) {
        return null;
    }
}
