package pl.com.bottega.photostock.sales.infrastructure.repositories.real;

import org.junit.Before;
import org.junit.Test;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ClientRepository;
import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.ClientStatus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static org.junit.Assert.*;

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
        c.createStatement().executeUpdate("DROP TABLE Clients IF EXISTS");
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
        assertEquals("nr1", client.getNumber());
        assertEquals("Anna Makara", client.getName());
        assertEquals("Księżycowa 2/13", client.getAddress());
        assertTrue(client.isActive());
        assertEquals(2000, client.getSaldo().cents()/100);
        assertEquals("USD", String.valueOf(client.getSaldo().getCurrency()));
        assertEquals(ClientStatus.VIP, client.getStatus());
    }

    @Test
    public void shouldReturnNullWhenClientDoesntExist() throws Exception {
        //when - load client
        ClientRepository clientRepo = new JDBCClientRepository("jdbc:hsqldb:mem:stock", "SA", "");
        Client client = clientRepo.load("nr500");
        //then
        assertNull(client);
    }

    @Test
    public void shouldSaveClient() {
        //given
        Client client = new Client("Helena Szczęsna", "Akacjowa 2", ClientStatus.SILVER, 200, "EUR", true, "nr2");
        //when
        clientRepo.save(client);
        //then
        Client clientSaved = clientRepo.load("nr2");
        assertEquals("Helena Szczęsna", clientSaved.getName());
        assertEquals("Akacjowa 2", clientSaved.getAddress(), clientSaved.getAddress());
        assertEquals(ClientStatus.SILVER, clientSaved.getStatus());
        assertEquals(200, clientSaved.getSaldo().cents()/100);
        assertEquals("EUR", String.valueOf(clientSaved.getSaldo().getCurrency()));
        assertTrue(clientSaved.isActive());
        assertEquals("nr2", clientSaved.getNumber());
    }

    @Test
    public void shouldInsertClient() {
    }

    @Test
    public void shouldUpdateClient() {
        Client client = new Client("Helena Szczęsna", "Akacjowa 2", ClientStatus.SILVER, 200, "EUR", true, "nr2");
        Client clientChanged = new Client("Helena Nieszczęsna", "Akacjowa 4", ClientStatus.VIP, 2000, "PLN", false, "nr2");
        clientRepo.save(client);
        clientRepo.save(clientChanged);
        Client clientChangedLoaded = clientRepo.load("nr2");
        assertEquals("Helena Nieszczęsna", clientChangedLoaded.getName());
        assertEquals("Akacjowa 4", clientChangedLoaded.getAddress());
        assertEquals(ClientStatus.VIP, clientChangedLoaded.getStatus());
        assertEquals(2000, clientChangedLoaded.getSaldo().cents()/100);
        assertEquals("PLN", String.valueOf(clientChangedLoaded.getSaldo().getCurrency()));
        assertFalse(clientChangedLoaded.isActive());
        assertEquals("nr2", clientChangedLoaded.getNumber());
    }
}