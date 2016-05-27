package pl.com.bottega.photostock.sales.infrastructure.repositories;

import org.junit.Before;
import org.junit.Test;
import pl.com.bottega.photostock.sales.infrastructure.repositories.file.FileClientRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.file.FileProductRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.file.FileReservationRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ClientRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ProductRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ReservationRepository;

/**
 * Created by Beata Iłowiecka on 24.05.16.
 */
public class FileReservationRepositoryTest {

    String path;
    ClientRepository clientRepository;
    ProductRepository productRepository;

    @Before
    public void initVariables() {
        path = "test/fixtures/lightboxes.csv";
        clientRepository = new FileClientRepository("test/fixtures/clients.csv");
        productRepository = new FileProductRepository("test/fixtures/products.csv");
    }

    @Test
    public void shouldLoadReservation() {
        //given
        ReservationRepository reservationRepository = new FileReservationRepository(path, clientRepository, productRepository);
    }
}
