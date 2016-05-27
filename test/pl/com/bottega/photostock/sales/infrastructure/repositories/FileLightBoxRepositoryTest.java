package pl.com.bottega.photostock.sales.infrastructure.repositories;

import org.junit.Before;
import org.junit.Test;
import pl.com.bottega.photostock.sales.infrastructure.repositories.file.FileClientRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.file.FileLightBoxRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.file.FileProductRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ClientRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.LightBoxRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ProductRepository;
import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.LightBox;
import pl.com.bottega.photostock.sales.model.Product;
import pl.com.bottega.photostock.sales.model.exceptions.DataAccessException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * Created by Beata Iłowiecka on 24.05.16.
 */
public class FileLightBoxRepositoryTest {

    String path;
    ClientRepository clientRepository;
    ProductRepository productRepository;

    @Before
    public void initVariables() {
        path = "test/fixtures/lightboxes.csv";
        clientRepository = new FileClientRepository("test/fixtures/clients.csv");
        productRepository = new FileProductRepository("test/fixtures/products.csv");
    }

    //[0]number,[1]ownerName,[2]ownerNumber,[3]active,[4]productsNumbers
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
    }

    @Test
    public void shouldLoadNullLightBox() {
        //given
        LightBoxRepository lightBoxRepository = new FileLightBoxRepository(path, clientRepository, productRepository);
        //when
        LightBox lightBox = lightBoxRepository.load("nr00");
        //then
        assertNull(lightBox);
    }

    @Test
    public void shouldThrowDataAccessExceptionWhenFileNotFound() {
        //given
        LightBoxRepository lightBoxRepository = new FileLightBoxRepository("Fake path", clientRepository, productRepository);
        //when
        DataAccessException ex = null;
        try {
            lightBoxRepository.load("nr2");
        }
        catch (DataAccessException dae) {
            ex = dae;
        }
        //then
        assertNotNull(ex);
    }

    @Test
    public void shouldWriteLightBoxes() {
        //given
        String path = "tmp/lightboxes.csv";
        LightBoxRepository lightBoxRepository = new FileLightBoxRepository(path, clientRepository, productRepository);
        LightBox lightBox1 = create1stLightBox();
        LightBox lightBox2 = create2ndLightBox();
        //when
        lightBoxRepository.save(lightBox1);
        lightBoxRepository.save(lightBox2);
        //then
        LightBox lightBox1Read = lightBoxRepository.load("nr11");
        LightBox lightBox2Read = lightBoxRepository.load("nr7");
        //assertions
        assertEquals("nr11", lightBox1Read.getNumber());
        assertEquals("nr7", lightBox2Read.getNumber());
        assertEquals("Pani Ela", lightBox1Read.getOwner().getName());
        assertEquals("Pan Leszek", lightBox2Read.getOwner().getName());
        assertEquals("nr1", lightBox1Read.getOwner().getNumber());
        assertEquals("nr2", lightBox2Read.getOwner().getNumber());
        assertEquals(true, lightBox1Read.isActive());
        assertEquals(true, lightBox2Read.isActive());
        assertEquals("nr3 nr4 ", lightBox1Read.getProductsNumbers());
        assertEquals("nr5 nr6 ", lightBox2Read.getProductsNumbers());
        File file = new File("tmp/lightBoxes.csv");
        file.delete();
    }

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
    }
}