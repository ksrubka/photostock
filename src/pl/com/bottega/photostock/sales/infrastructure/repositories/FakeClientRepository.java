package pl.com.bottega.photostock.sales.infrastructure.repositories;

import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.ClientStatus;
import pl.com.bottega.photostock.sales.model.LightBox;
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
        Client paniEla = new Client("Pani Ela", "Saturn", ClientStatus.SILVER, 15, true, "nr1");
        Client paniAniela = new Client("Pani Aniela", "Merkury", 7, "nr2");
        Client panLeszek = new Client("Pan Leszek", "Niebieski księżyc",ClientStatus.VIP, 500, true, "nr3");
        Client paniGosia = new Client("Pani Gosia", "Ziemia",ClientStatus.VIP, 56, true, "nr4");
        Client panKuba = new Client("Pan Kuba", "Mars",ClientStatus.PLATINUM, 85, false, "nr5");
        Client panJan = new Client("Pan Jan", "Jowisz", 5, "nr5");

        fakeDatabase.put(paniEla.getName(), paniEla);
        fakeDatabase.put(paniAniela.getName(), paniAniela);
        fakeDatabase.put(panLeszek.getName(), panLeszek);
        fakeDatabase.put(paniGosia.getName(), paniGosia);
        fakeDatabase.put(panKuba.getName(), panKuba);
        fakeDatabase.put(panJan.getName(), panJan);
    }

    @Override
    public Client load(String name) {
        Client client = fakeDatabase.get(name);
        if  (client == null)
            throw new RuntimeException("Client " + name + " does not exist");
        return client;
    }

    @Override
    public void save(Client client) {
        if (client.getNumber() == null)
            client.setNumber(UUID.randomUUID().toString()); // symulacja generowania ID przez bazę danych
        fakeDatabase.put(client.getName(), client);
    }
}
