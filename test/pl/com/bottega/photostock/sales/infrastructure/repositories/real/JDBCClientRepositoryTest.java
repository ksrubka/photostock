package pl.com.bottega.photostock.sales.infrastructure.repositories.real;

import org.junit.Before;
import org.junit.Test;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ClientRepository;
import pl.com.bottega.photostock.sales.model.Client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Beata Iłowiecka on 02.06.16.
 */
public class JDBCClientRepositoryTest {

    ClientRepository clientRepo;

    @Before
    public void setUp() throws Exception {
        Connection c = DriverManager.getConnection("jdbc:hsqldb:mem:stock", "SA", "");
        createClientsTable(c);
        insertTestClient(c);
        clientRepo = new JDBCClientRepository("jdbc:hsqldb:mem:stock", "SA", "");
        c.close();
    }

    private void createClientsTable(Connection c) throws Exception {
        c.createStatement().executeUpdate("DROP TABLE Products IF EXISTS");
        Statement statement = c.createStatement();
        statement.executeUpdate("CREATE TABLE Clients (\n" +
                "  id INTEGER IDENTITY PRIMARY KEY,\n" +
                "  number VARCHAR(20) NOT NULL,\n" +
                "  name VARCHAR(255) NOT NULL,\n" +
                "  address VARCHAR(255) NOT NULL,\n" +
                "  active BOOLEAN DEFAULT true NOT NULL,\n" +
                "  amount INTEGER DEFAULT 0 NOT NULL,\n" +
                "  currency CHAR(3) DEFAULT 'PLN' NOT NULL,\n" +
                "  status VARCHAR(20) DEFAULT 'standard' NOT NULL,\n" +
                ");");
    }

    private void insertTestClient(Connection c) throws Exception {
        Statement statement = c.createStatement();
        statement.executeUpdate(
                "INSERT INTO Clients (number, name, address, active, amount, currency, status) " +
                        "VALUES ('nr1', 'Anna Makara', 'Księżycowa 2/13', true, 2000, 'USD', 'vip');\n");
    }

    @Test
    public void shouldLoadClient() throws Exception {
        //when - load client
        Client client = clientRepo.load("nr1");
        //then - assertions
        assertEquals(client.getNumber(), "nr1");
        assertEquals(client.getName(), "Anna Makara");
        assertEquals(client.getAddress(), "Księżycowa 2/13");
        assertTrue(client.isActive());
        assertEquals(client.getSaldo().cents()/100, 2000);
        assertEquals(String.valueOf(client.getSaldo().getCurrency()), "USD");
        assertEquals(String.valueOf(client.getStatus()), "VIP");
    }
}

