package pl.com.bottega.photostock.sales.infrastructure.repositories.real;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ProductRepository;
import pl.com.bottega.photostock.sales.model.Money;
import pl.com.bottega.photostock.sales.model.Product;
import pl.com.bottega.photostock.sales.model.products.Picture;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Beata Iłowiecka on 29.05.2016.
 */

public class JDBCProductRepositoryTest {

    ProductRepository productRepo;

    @Before
    public void setUp() throws Exception {
        //"jdbc:hsqldb:file:/opt/db/testdb" - taki adress jak chcesz na serio a nie w pamięci
        Connection c = DriverManager.getConnection("jdbc:hsqldb:mem:stock", "SA", "");
        createProductsTable(c);
        insertTestProduct(c);
        productRepo = new JDBCProductRepository("jdbc:hsqldb:mem:stock", "SA", "");
        c.close();
    }

    private void createProductsTable(Connection c) throws Exception {
        c.createStatement().executeUpdate("DROP TABLE Products IF EXISTS");
        Statement statement = c.createStatement();
        statement.executeUpdate("CREATE TABLE Products (\n" +
                "  id INTEGER IDENTITY PRIMARY KEY,\n" +
                "  number VARCHAR(20) NOT NULL,\n" +
                "  type VARCHAR(20) NOT NULL,\n" +
                "  name VARCHAR(255) NOT NULL,\n" +
                "  available BOOLEAN DEFAULT true NOT NULL,\n" +
                "  priceCents INTEGER DEFAULT 0 NOT NULL,\n" +
                "  priceCurrency CHAR(3) DEFAULT 'PLN' NOT NULL,\n" +
                "  length BIGINT\n" +
                ");");
    }

    private void insertTestProduct(Connection c) throws Exception {
        Statement statement = c.createStatement();
        statement.executeUpdate(
                "INSERT INTO Products (number, name, available, priceCents, priceCurrency, length, type) " +
                        "VALUES ('nr1', 'Mazda 3', true, 200, 'USD', NULL, 'Picture');\n");
    }

    @Test
    public void shouldLoadProduct() throws Exception {
        //when - load product
        Product product = productRepo.load("nr1");
        //then - assertions
        assertEquals(product.getNumber(), "nr1");
        assertEquals(product.getClass(), Picture.class);
    }

    @Test
    public void shouldReturnNullWhenProductDoesntExist() throws Exception {
        //when - load product
        ProductRepository productRepo = new JDBCProductRepository("jdbc:hsqldb:mem:stock", "SA", "");
        Product product = productRepo.load("nr500");
        //then
        assertNull(product);
    }

    @Test
    public void shouldSaveProduct() {
        //given
        Product picture = new Picture("nr2", new Money(20.0), new String[] {"t1", "t2"}, false);
        //when
        productRepo.save(picture);
        //then
        Product picSaved = productRepo.load("nr2");
        assertEquals(picSaved.getNumber(), "nr2");
        assertEquals(picSaved.getPrice().cents(), new Money(20).cents());
    }
}
