package pl.com.bottega.photostock.sales.infrastructure.repositories;

import org.junit.Test;
import pl.com.bottega.photostock.sales.infrastructure.repositories.file.FileClientRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ClientRepository;
import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.Money;
import pl.com.bottega.photostock.sales.model.ClientStatus;
import pl.com.bottega.photostock.sales.model.exceptions.DataAccessException;

import java.io.File;

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
        //given
        ClientRepository clientRepository = new FileClientRepository("tmp/clients.csv");
        Client paniKasia = new Client("Pani Kasia", "Konwaliowa", ClientStatus.STANDARD, 20, true, "nr1");
        Client paniAgata = new Client("Pani Agata", "Akacjowa", 15, 30, "nr2");
        //when
        clientRepository.save(paniKasia);
        clientRepository.save(paniAgata);
        //then
        Client paniKasiaRead = clientRepository.load("nr1");
        Client paniAgataRead = clientRepository.load("nr2");

        assertEquals(paniKasiaRead.getName(), "Pani Kasia");
        assertEquals(paniAgataRead.getName(), "Pani Agata");

        assertEquals(paniKasiaRead.getAddress(), "Konwaliowa");
        assertEquals(paniAgataRead.getAddress(), "Akacjowa");

        assertEquals(paniKasiaRead.getStatus(), ClientStatus.STANDARD);
        assertEquals(paniAgataRead.getStatus(), ClientStatus.VIP);

        assertEquals(paniKasiaRead.getSaldo(), new Money(20));
        assertEquals(paniAgataRead.getSaldo(), new Money(15));

        assertTrue(paniKasiaRead.isActive());
        assertTrue(paniAgataRead.isActive());

        assertEquals(paniKasiaRead.getNumber(), "nr1");
        assertEquals(paniAgataRead.getNumber(), "nr2");

        File file = new File("tmp/clients.csv");
        file.delete();
    }
}