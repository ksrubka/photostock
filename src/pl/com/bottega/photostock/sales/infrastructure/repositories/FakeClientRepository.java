package pl.com.bottega.photostock.sales.infrastructure.repositories;

import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.ClientStatus;
import pl.com.bottega.photostock.sales.model.LightBox;
import pl.com.bottega.photostock.sales.model.exceptions.ClientDoesNotExistException;
;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Beata Iłowiecka on 21.04.16.
 */
public class FakeClientRepository implements ClientRepository {

    private static Map<String, Client> fakeDatabase = new HashMap<>();

    static {
        Client paniEla = new Client("Pani Ela", "Saturn", ClientStatus.STANDARD, 15, true, "nr1");
        Client panLeszek = new Client("Pan Leszek", "Niebieski księżyc",ClientStatus.VIP, 500, true, "nr2");
        Client paniAniela = new Client("Pani Aniela", "Merkury", ClientStatus.GOLD, 50, true, "nr3");
        Client panJan = new Client("Pan Jan", "Jowisz", 0, "nr4");
        Client paniGosia = new Client("Pani Gosia", "Ziemia", ClientStatus.VIP, 56, true, "nr5");
        Client panKuba = new Client("Pan Kuba", "Mars",ClientStatus.PLATINUM, 85, false, "nr6");

        fakeDatabase.put(paniEla.getNumber(), paniEla);
        fakeDatabase.put(paniAniela.getNumber(), paniAniela);
        fakeDatabase.put(panLeszek.getNumber(), panLeszek);
        fakeDatabase.put(paniGosia.getNumber(), paniGosia);
        fakeDatabase.put(panKuba.getNumber(), panKuba);
        fakeDatabase.put(panJan.getNumber(), panJan);
    }

    @Override
    public Client load(String number) throws ClientDoesNotExistException {
        Client client = fakeDatabase.get(number);
        if  (client == null)
            throw new ClientDoesNotExistException("Client " + number + " does not exist", number);
        return client;
    }

    @Override
    public void save(Client client) {
        if (client.getNumber() == null)
            client.setNumber(UUID.randomUUID().toString()); // symulacja generowania ID przez bazę danych
        fakeDatabase.put(client.getName(), client);
    }
}
