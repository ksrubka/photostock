package pl.com.bottega.photostock.sales.infrastructure.repositories;

import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.ClientStatus;
import pl.com.bottega.photostock.sales.model.LightBox;
import pl.com.bottega.photostock.sales.model.Product;
import pl.com.bottega.photostock.sales.model.products.Clip;
import pl.com.bottega.photostock.sales.model.products.Picture;
import pl.com.bottega.photostock.sales.model.products.Song;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Beata Iłowiecka on 21.04.16.
 */
public class FakeLightBoxRepository implements LightBoxRepository {

    private static Map<String, LightBox> fakeDatabase = new HashMap<>();

    // inicjalizator klasy ale nie dla obiektów tej klasy
    static {
        ClientRepository clientRepository = new FakeClientRepository();
        ProductRepository productRepository = new FakeProductRepository();

        LightBox lightBox1 = new LightBox(clientRepository.load("Pani Ela"), "nr1");
        LightBox lightBox2 = new LightBox(clientRepository.load("Pan Jan"), "nr2");
        LightBox lightBox3 = new LightBox(clientRepository.load("Pan Leszek"), "nr3");
        LightBox lightBox4 = new LightBox(clientRepository.load("Pani Gosia"), "nr4");
        LightBox lightBox5 = new LightBox(clientRepository.load("Pani Aniela"), "nr5");

        Product product1 = productRepository.load("nr1");
        Product product2 = productRepository.load("nr2");
        Product product3 = productRepository.load("nr3");
        Product product4 = productRepository.load("nr4");
        Product product5 = productRepository.load("nr5");
        Product product6 = productRepository.load("nr6");

        lightBox1.add(product1);
        lightBox2.add(product2);
        lightBox3.add(product3);
        lightBox4.add(product4);
        lightBox5.add(product5, product6);

        fakeDatabase.put(lightBox1.getNumber(), lightBox1);
        fakeDatabase.put(lightBox2.getNumber(), lightBox2);
        fakeDatabase.put(lightBox3.getNumber(), lightBox3);
        fakeDatabase.put(lightBox4.getNumber(), lightBox4);
        fakeDatabase.put(lightBox5.getNumber(), lightBox5);
    }
    @Override
    public LightBox load(String number) {
        LightBox lightBox = fakeDatabase.get(number);
        if  (lightBox == null){
            throw new RuntimeException("LightBox " + number + " does not exist");
        }
        return lightBox;
    }

    @Override
    public void save(LightBox lightBox) {
        fakeDatabase.put(lightBox.getNumber(), lightBox);
    }
}
