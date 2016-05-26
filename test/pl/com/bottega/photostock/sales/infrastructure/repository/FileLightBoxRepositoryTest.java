package pl.com.bottega.photostock.sales.infrastructure.repository;

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
import java.util.ArrayList;
import java.util.List;
import static junit.framework.TestCase.assertEquals;


/**
 * Created by Beata Iłowiecka on 24.05.16.
 */
public class FileLightBoxRepositoryTest {

    //[0]number,[1]ownerName,[2]ownerNumber,[3]clientsSharingNumbers,[4]active,[5]productsNumbers
    @Test
    public void shouldLoadLightBox() {
        //given
        ClientRepository clientRepository = new FileClientRepository("test/fixtures/clients.csv");
        ProductRepository productRepository = new FileProductRepository("test/fixtures/products.csv");
        LightBoxRepository lightBoxRepository = new FileLightBoxRepository("test/fixtures/lightboxes.csv");
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
}
