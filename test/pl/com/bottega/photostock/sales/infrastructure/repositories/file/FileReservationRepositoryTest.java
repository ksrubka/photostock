package pl.com.bottega.photostock.sales.infrastructure.repositories.file;

import org.junit.Before;
import org.junit.Test;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ClientRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ProductRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ReservationRepository;
import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.Product;
import pl.com.bottega.photostock.sales.model.Reservation;
import pl.com.bottega.photostock.sales.model.exceptions.DataAccessException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Beata Iłowiecka on 24.05.16.
 */
public class FileReservationRepositoryTest {

    String path;
    ClientRepository clientRepository;
    ProductRepository productRepository;

    @Before
    public void initVariables() {
        path = "test/fixtures/reservations.csv";
        clientRepository = new FileClientRepository("test/fixtures/clients.csv");
        productRepository = new FileProductRepository("test/fixtures/products.csv");
    }

    //[0]number,[1]ownerName,[2]ownerNumber,[3]active,[4]productsNumbers
    @Test
    public void shouldLoadReservation() {
        //given
        ReservationRepository reservationRepository =
                new FileReservationRepository(path, clientRepository, productRepository);
        //when
        Reservation reservation = reservationRepository.load("nr2");
        Product product1 = productRepository.load("nr2");
        Product product2 = productRepository.load("nr3");
        //Client client = clientRepository.load("nr6");
        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        //then
        assertEquals(reservation.getNumber(), "nr2");
        assertEquals(reservation.getOwner().getName(), "Pan Kuba");
        assertEquals(reservation.getOwner().getNumber(), "nr6");
        //assertEquals(client, reservation.getOwner());
        assertTrue(!reservation.isClosed());
        assertEquals(reservation.getProductsNumbers(), "nr2 nr3");
    }

    @Test
    public void shouldThrowDataAccessExceptionWhenFileNotFound() {
        //given
        ReservationRepository reservationRepository =
                new FileReservationRepository("Fake path", clientRepository, productRepository);
        //when
        DataAccessException ex = null;
        try {
            reservationRepository.load("nr2");
        }
        catch (DataAccessException dae) {
            ex = dae;
        }
        //then
        assertNotNull(ex);
    }

    @Test
    public void shouldWriteReservations() {
        //given
        String path = "tmp/reservations.csv";
        ReservationRepository reservationRepository =
                new FileReservationRepository(path,  clientRepository, productRepository);
        Reservation reservation1 = init1stReservation();
        Reservation reservation2 = init2ndReservation();
        //when
        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);
        //then
        Reservation reservation1Read = reservationRepository.load("nr7");
        Reservation reservation2Read = reservationRepository.load("nr8");
        //assertions
        assertEquals(reservation1Read.getNumber(), "nr7");
        assertEquals(reservation2Read.getNumber(), "nr8");
        assertEquals(reservation1Read.getOwner().getName(), "Pani Ela");
        assertEquals(reservation2Read.getOwner().getName(), "Pan Leszek");
        assertEquals(reservation1Read.getOwner().getNumber(), "nr1");
        assertEquals(reservation2Read.getOwner().getNumber(), "nr2");
        assertTrue(reservation1Read.isActive());
        assertEquals(false, reservation2Read.isActive());
        assertEquals(reservation1Read.getProductsNumbers(), "nr3 nr4");
        assertEquals(reservation2Read.getProductsNumbers(), "nr5 nr6");
        File file = new File("tmp/reservations.csv");
        file.delete();
    }

    private Reservation init1stReservation() {
        Client client = clientRepository.load("nr1");
        Product product1 = productRepository.load("nr3");
        Product product2 = productRepository.load("nr4");
        Reservation reservation = new Reservation(client);
        reservation.setNumber("nr7");
        reservation.add(product1);
        reservation.add(product2);
        return reservation;
    }

    private Reservation init2ndReservation() {
        Client client = clientRepository.load("nr2");
        Product product1 = productRepository.load("nr5");
        Product product2 = productRepository.load("nr6");
        Reservation reservation = new Reservation(client);
        reservation.setNumber("nr8");
        reservation.add(product1);
        reservation.add(product2);
        reservation.close();
        return reservation;
    }
}