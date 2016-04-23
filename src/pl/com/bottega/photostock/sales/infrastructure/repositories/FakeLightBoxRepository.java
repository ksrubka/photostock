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
import java.util.UUID;

/**
 * Created by Beata Iłowiecka on 21.04.16.
 */
public class FakeLightBoxRepository implements LightBoxRepository {

    private static Map<String, LightBox> fakeDatabase = new HashMap<>();

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
        if (lightBox.getNumber() == null){
            lightBox.setNumber(UUID.randomUUID().toString()); // symulacja generowania ID przez bazę danych
        }
        fakeDatabase.put(lightBox.getNumber(), lightBox);
    }
}
