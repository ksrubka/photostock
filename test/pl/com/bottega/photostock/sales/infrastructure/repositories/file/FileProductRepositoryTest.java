package pl.com.bottega.photostock.sales.infrastructure.repositories.file;

import org.junit.Test;
import pl.com.bottega.photostock.sales.model.exceptions.DataAccessException;
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
        assertEquals(product.getNumber(), "nr2");
        assertEquals(product.getClass(), Picture.class);
        assertEquals(product.getPrice(), new Money(5.0, "USD"));
        assertTrue(product.isAvailable());
        assertArrayEquals(product.getTags(), new String[] {"t1", "t2"});
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
        ProductRepository productRepository = new FileProductRepository("tmp/products.csv");
        Product clip = new Clip("nr1", new Money(500.0, "USD"), 200);
        Product picture = new Picture("nr2", new Money(20.0), new String[] {"t1", "t2"}, false);
        //when
        productRepository.save(clip);
        productRepository.save(picture);
        //then
        Product clipRead = productRepository.load("nr1");
        Product pictureRead = productRepository.load("nr2");
        //assertions
        assertEquals(clipRead.getNumber(), "nr1");
        assertEquals(pictureRead.getNumber(), "nr2");
        assertArrayEquals(pictureRead.getTags(), new String[] {"t1", "t2"});
        assertEquals(((Clip) clipRead).getLength(), 200);
        assertEquals(pictureRead.isAvailable(), false);
        // dlaczego nie kasuje się to co jest w środku? czy ja nie nadpisuję pustym plikiem?
        //(w przypadku gdy nie kasuję pliku)
        //dlaczego nie pojawia się plik w tmp w trakcie stworzenia go przez metodę, tylko dopiero po całym wykonaniu?
        File file = new File("tmp/products.csv");
        //Thread.sleep(10000);
        file.delete();
    }
}