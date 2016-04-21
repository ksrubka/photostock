package pl.com.bottega.photostock.sales.infrastructure.repositories;

import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.ClientStatus;
import pl.com.bottega.photostock.sales.model.LightBox;
;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Beata Iłowiecka on 21.04.16.
 */
public class FakeClientRepository implements ClientRepository {

    private static Map<String, Client> fakeDatabase = new HashMap<>();

    static {
        Client paniEla = new Client("Pani Ela", "Saturn", ClientStatus.SILVER, 15, true);
        Client paniAniela = new Client("Pani Aniela", "Merkury",ClientStatus.STANDARD, 7, true);
        Client panLeszek = new Client("Pan Leszek", "Niebieski księżyc",ClientStatus.VIP, 500, true);
        Client paniGosia = new Client("Pani Gosia", "Ziemia",ClientStatus.VIP, 56, true);
        Client panKuba = new Client("Pan Kuba", "Mars",ClientStatus.PLATINUM, 85, false);
        Client panJan = new Client("Pan Jan", "Jowisz", 5);

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
        if  (client == null){
            throw new RuntimeException("Client " + name + " does not exist");
        }
        return client;
    }

    @Override
    public void save(Client client) {
        fakeDatabase.put(client.getName(), client);
    }
}
