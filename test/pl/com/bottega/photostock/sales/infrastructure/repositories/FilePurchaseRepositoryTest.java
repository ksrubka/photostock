package pl.com.bottega.photostock.sales.infrastructure.repositories;

import org.junit.Before;
import org.junit.Test;
import pl.com.bottega.photostock.sales.infrastructure.repositories.file.FileClientRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.file.FileProductRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.file.FilePurchaseRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ClientRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ProductRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.PurchaseRepository;
import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.Product;
import pl.com.bottega.photostock.sales.model.Purchase;
import pl.com.bottega.photostock.sales.model.exceptions.DataAccessException;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * Created by Beata Iłowiecka on 24.05.16.
 */
public class FilePurchaseRepositoryTest {

    String path;
    ClientRepository clientRepository;
    ProductRepository productRepository;

    @Before
    public void initVariables() {
        path = "test/fixtures/purchases.csv";
        clientRepository = new FileClientRepository("test/fixtures/clients.csv");
        productRepository = new FileProductRepository("test/fixtures/products.csv");
    }

    //[0]number,[1]ownerName,[2]ownerNumber,[3]date,[4]productsNumbers
    @Test
    public void shouldLoadPurchase() {
        //given
        PurchaseRepository purchaseRepository =
                new FilePurchaseRepository(path, clientRepository, productRepository);
        //when
        Purchase purchase = purchaseRepository.load("nr1");
        Product product1 = productRepository.load("nr1");
        Product product2 = productRepository.load("nr2");
        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        //then
        assertEquals("nr1", purchase.getNumber());
        assertEquals("Pani Gosia", purchase.getOwner().getName());
        //todo data?
        assertEquals("nr5", purchase.getOwner().getNumber());
        assertEquals(products, purchase.getItems());
    }

    @Test
    public void shouldThrowDataAccessExceptionWhenFileNotFound() {
        //given
        PurchaseRepository purchaseRepository =
                new FilePurchaseRepository("Fake path", clientRepository, productRepository);
        //when
        DataAccessException ex = null;
        try {
            purchaseRepository.load("nr2");
        }
        catch (DataAccessException dae) {
            ex = dae;
        }
        //then
        assertNotNull(ex);
    }

    @Test
    public void shouldWritePurchases() {
        //given
        String path = "tmp/purchases.csv";
        PurchaseRepository purchaseRepository =
                new FilePurchaseRepository(path, clientRepository, productRepository);
        Purchase purchase1 = create1stPurchase();
        Purchase purchase2 = create2ndPurchase();
        //when
        purchaseRepository.save(purchase1);
        purchaseRepository.save(purchase2);
        //then
        Purchase purchase1Read = purchaseRepository.load("nr1");
        Purchase purchase2Read = purchaseRepository.load("nr2");
        //assertions
        assertEquals(purchase1Read.getNumber(), "nr1");
        assertEquals(purchase2Read.getNumber(), "nr2");
        assertEquals(purchase1Read.getOwner().getName(), "Pani Gosia");
        assertEquals(purchase2Read.getOwner().getName(), "Pan Kuba");
        assertEquals(purchase1Read.getOwner().getNumber(), "nr5");
        assertEquals(purchase2Read.getOwner().getNumber(), "nr6");
        assertEquals(purchase1Read.getProductsNumbers(), "nr1 nr2");
        assertEquals(purchase2Read.getProductsNumbers(), "nr3");
        File file = new File("tmp/purchases.csv");
        file.delete();
    }

    private Purchase create1stPurchase() {
        Client client = clientRepository.load("nr5");
        Product product1 = productRepository.load("nr1");
        Product product2 = productRepository.load("nr2");
        List<Product> items= new ArrayList<>();
        items.add(product1);
        items.add(product2);
        Purchase purchase = new Purchase(client, items);
        purchase.setNumber("nr1");
        //Date date = createDate();
        //purchase.setDate(date);
        return purchase;
    }

    private Purchase create2ndPurchase() {
        Client client = clientRepository.load("nr6");
        Product product1 = productRepository.load("nr3");
        List<Product> items= new ArrayList<>();
        items.add(product1);
        Purchase purchase = new Purchase(client, items);
        purchase.setNumber("nr2");
        //Date date = createDate();
        //purchase.setDate(date);
        return purchase;
    }
}
