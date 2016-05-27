package pl.com.bottega.photostock.sales.infrastructure.repositories.file;

import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ClientRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ProductRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ReservationRepository;
import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.Reservation;
import pl.com.bottega.photostock.sales.model.exceptions.DataAccessException;

import java.io.BufferedReader;
import java.io.FileReader;
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
        try (BufferedReader br = new BufferedReader(new FileReader(path), 512)) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().length() == 0)
                    return null;
                Reservation reservation = parseReservation();
                if (reservation.getNumber().equals(number))
                    return reservation;
            }
        }
        catch (Exception e) {
                throw new DataAccessException(e);
        }
        return null;
    }

    private Reservation parseReservation() {

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
