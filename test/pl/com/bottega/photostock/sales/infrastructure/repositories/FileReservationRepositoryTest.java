package pl.com.bottega.photostock.sales.infrastructure.repositories;

import org.junit.Before;
import org.junit.Test;
import pl.com.bottega.photostock.sales.infrastructure.repositories.file.FileClientRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.file.FileProductRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.file.FileReservationRepository;
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
        assertEquals("nr2", reservation.getNumber());
        assertEquals("Pan Kuba", reservation.getOwner().getName());
        assertEquals("nr6", reservation.getOwner().getNumber());
        //assertEquals(client, reservation.getOwner());
        assertEquals(true, !reservation.isClosed());
        assertEquals("nr2 nr3", reservation.getProductsNumbers());
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
        Reservation reservation1Read = reservationRepository.load("nr1");
        Reservation reservation2Read = reservationRepository.load("nr2");
        //assertions
        assertEquals("nr1", reservation1Read.getNumber());
        assertEquals("nr2", reservation2Read.getNumber());
        assertEquals("Pani Ela", reservation1Read.getOwner().getName());
        assertEquals("Pan Leszek", reservation2Read.getOwner().getName());
        assertEquals("nr1", reservation1Read.getOwner().getNumber());
        assertEquals("nr2", reservation2Read.getOwner().getNumber());
        assertEquals(true, reservation1Read.isActive());
        assertEquals(true, reservation2Read.isActive());
        assertEquals("nr3 nr4 ", reservation1Read.getProductsNumbers());
        assertEquals("nr5 nr6 ", reservation2Read.getProductsNumbers());
        File file = new File("tmp/reservations.csv");
        file.delete();
    }

    private Reservation init1stReservation() {
        return null;
    }

    private Reservation init2ndReservation() {
        return null;
    }

   /*
    private LightBox create1stLightBox() {
        Client client = clientRepository.load("nr1");
        Product product1 = productRepository.load("nr3");
        Product product2 = productRepository.load("nr4");
        LightBox lightBox = new LightBox(client);
        lightBox.setNumber("nr11");
        lightBox.add(product1, product2);
        return lightBox;
    }

    private LightBox create2ndLightBox() {
        Client client = clientRepository.load("nr2");
        Product product1 = productRepository.load("nr5");
        Product product2 = productRepository.load("nr6");
        LightBox lightBox = new LightBox(client);
        lightBox.setNumber("nr7");
        lightBox.add(product1, product2);
        return lightBox;
    }*/
}
