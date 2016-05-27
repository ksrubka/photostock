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

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

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
        Client client = clientRepository.load("nr6");
        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        //then
        assertEquals("nr2", reservation.getNumber());
        assertEquals("Pan Kuba", reservation.getOwner().getName());
        assertEquals("nr6", reservation.getOwner().getNumber());
        assertEquals(client, reservation.getOwner());
        assertEquals(true, !reservation.isClosed());
        assertEquals("nr2 nr3 ", reservation.getProductsNumbers());
    }

    /*//[0]number,[1]ownerName,[2]ownerNumber,[3]active,[4]productsNumbers
    @Test
    public void shouldLoadLightBox() {
        //given
        LightBoxRepository lightBoxRepository = new FileLightBoxRepository(path, clientRepository, productRepository);
        //when
        LightBox lightBox = lightBoxRepository.load("nr1");
        Product product1 = productRepository.load("nr1");
        Product product2 = productRepository.load("nr2");
        //Client client = clientRepository.load("nr5");
        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        //then
        assertEquals("nr1", lightBox.getNumber());
        assertEquals("Pani Gosia", lightBox.getOwner().getName());
        //assertEquals(client, lightBox.getOwner());
        assertEquals("nr5", lightBox.getOwner().getNumber());
        assertEquals(products, lightBox.getItems());
        assertEquals(true, lightBox.isActive());
    }*/
}
