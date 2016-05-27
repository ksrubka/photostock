package pl.com.bottega.photostock.sales.infrastructure.repositories.file;

import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ClientRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ProductRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ReservationRepository;
import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.Product;
import pl.com.bottega.photostock.sales.model.Reservation;
import pl.com.bottega.photostock.sales.model.exceptions.DataAccessException;

import java.io.*;
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
                Reservation reservation = parseReservation(line);
                if (reservation.getNumber().equals(number))
                    return reservation;
            }
        }
        catch (Exception e) {
                throw new DataAccessException(e);
        }
        return null;
    }

    //[0]number,[1]ownerName,[2]ownerNumber,[3]active,[4]productsNumbers
    private Reservation parseReservation(String line) {
        String[] components = line.split(",");
        String number = components[0];
        String ownerNr = components[2];
        boolean active = Boolean.valueOf(components[3]);
        String[] productsNrs = components[4].split(" ");

        Reservation reservation = initReservation(ownerNr);
        reservation.setNumber(number);
        Product product;
        for (String nr : productsNrs) {
            product = productRepository.load(nr);
            reservation.add(product);
        }
        if (!active)
            reservation.close();
        return reservation;
    }

    private Reservation initReservation(String ownerNr) {
        Client client = clientRepository.load(ownerNr);
        return  new Reservation(client);
    }

    @Override
    public void save(Reservation reservation) {
        File file = new File(this.path);
        boolean newRepo = !file.exists();
        try (OutputStream os = new FileOutputStream(this.path, true)) {
            if (newRepo)
                os.write("number,ownerName,ownerNumber,active,productsNumbers\n".getBytes());
            String[] reservationExported = reservation.export();
            String csvLine = String.join(",", reservationExported) + "\n";
            os.write(csvLine.getBytes());
        } catch (Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    @Override
    public Reservation findOpenPer(Client client) {
        return null;
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
