package pl.com.bottega.photostock.sales.infrastructure.repository;

import org.junit.Test;
import pl.com.bottega.photostock.sales.infrastructure.repositories.file_repositories.FileClientRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.file_repositories.FileProductRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ClientRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ProductRepository;
import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.Money;
import pl.com.bottega.photostock.sales.model.ClientStatus;
import pl.com.bottega.photostock.sales.model.exceptions.DataAccessException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Beata Iłowiecka on 24.05.16.
 */
public class FileClientRepositoryTest {

    @Test
    public void shouldLoadClient() {
        //given
        ClientRepository clientRepository = new FileClientRepository("test/fixtures/clients.csv");
        //when
        Client client = clientRepository.load("nr2");
        //then
        assertEquals("Pan Leszek", client.getName());
        assertEquals("blue moon", client.getAddress());
        assertEquals(ClientStatus.VIP, client.getStatus());
        assertEquals(new Money(500.0), client.getSaldo());
        assertTrue(client.isActive());
        assertEquals("nr2", client.getNumber());
    }

    @Test
    public void shouldThrowDataAccessExceptionWhenFileNotFound() {
        //given
        ClientRepository clientRepository = new FileClientRepository("Fake path");
        //when
        DataAccessException ex = null;
        try {
            clientRepository.load("nr2");
        }
        catch (DataAccessException dae) {
            ex = dae;
        }
        //then
        assertNotNull(ex);
    }

    @Test
    public void shouldWriteClients() {

    }
}
