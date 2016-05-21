package pl.com.bottega.photostock.sales.infrastructure.repository;

import org.junit.Assert;
import org.junit.Test;
import pl.com.bottega.photostock.sales.infrastructure.repositories.FileProductRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.ProductRepository;
import pl.com.bottega.photostock.sales.model.Money;
import pl.com.bottega.photostock.sales.model.Product;
import pl.com.bottega.photostock.sales.model.products.Picture;

import static junit.framework.TestCase.assertEquals;
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
        assertEquals(new String[] {"tag1", "tag2"}, ((Picture) product).getTags());
    }
}
