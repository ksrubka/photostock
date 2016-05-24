package pl.com.bottega.photostock.sales.infrastructure.repository;

import org.junit.Assert;
import org.junit.Test;
import pl.com.bottega.photostock.sales.infrastructure.repositories.DataAccessException;
import pl.com.bottega.photostock.sales.infrastructure.repositories.FileProductRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ProductRepository;
import pl.com.bottega.photostock.sales.model.Money;
import pl.com.bottega.photostock.sales.model.Product;
import pl.com.bottega.photostock.sales.model.products.Clip;
import pl.com.bottega.photostock.sales.model.products.Picture;
import java.io.File;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Beata Iłowiecka on 14.05.2016.
 */
public class FileProductRepositoryTest {

    @Test
    public void shouldLoadProduct() {
        //given
        ProductRepository productRepository =
                new FileProductRepository(getClass().getResource("/fixtures/products.csv").getPath());
        //when
        Product product = productRepository.load("nr2");
        //then
        assertEquals("nr2", product.getNumber());
        assertEquals(Picture.class, product.getClass());
        assertEquals(new Money(5.0, "USD"), product.getPrice());
        assertTrue(product.isAvailable());
        assertArrayEquals(new String[] {"t1", "t2"}, ((Picture)product).getTags());
    }

    @Test
    public void shouldThrowDataAccessExceptionWhenFileNotFound() {
        //given
        ProductRepository productRepository = new FileProductRepository("Fake path");
        //when
        DataAccessException ex = null;
        try {
            productRepository.load("nr2");
        }
        catch (DataAccessException dae) {
            ex = dae;
        }
        //then
        assertNotNull(ex);
    }

    @Test
    public void shouldWriteProducts() throws InterruptedException {
        //given
        ProductRepository productRepository = new FileProductRepository("tmp/prducts.csv");
        Product clip = new Clip("nr1", new Money(500.0, "USD"), 200);
        Product picture = new Picture("nr2", new Money(20.0), new String[] {"t1", "t2"}, false);
        //when
        productRepository.save(clip);
        productRepository.save(picture);
        //then
        Product clipRead = productRepository.load("nr1");
        Product pictureRead = productRepository.load("nr2");
        Assert.assertEquals("nr1", clipRead.getNumber());
        Assert.assertEquals("nr2", pictureRead.getNumber());
        assertArrayEquals(new String[] {"t1", "t2"}, ((Picture) pictureRead).getTags());
        assertEquals(200, ((Clip) clipRead).getLength());
        assertEquals(false, pictureRead.isAvailable());
        // dlaczego nie kasuje się to co jest w środku? czy ja nie nadpisuję pustym plikiem?
        //(w przypadku gdy nie kasuję pliku)
        //dlaczego nie pojawia się plik w tmp w trakcie stworzenia go przez metodę, tylko dopiero po całym wykonaniu?
        File file = new File("tmp/prducts.csv");
        //Thread.sleep(10000);
        file.delete();
    }
}