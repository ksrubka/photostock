package pl.com.bottega.photostock.sales.infrastructure.repositories;

import pl.com.bottega.photostock.sales.model.Purchase;
import pl.com.bottega.photostock.sales.model.Reservation;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Beata Iłowiecka on 23.04.2016.
 */
public class FakePurchaseRepository implements PurchaseRepository {

    private static Map<String, Purchase> fakeDatabase = new HashMap<>();

    @Override
    public Purchase load(String number) {
        Purchase purchase = fakeDatabase.get(number);
        if  (purchase == null){
            throw new RuntimeException("Purchase " + number + " does not exist");
        }
        return purchase;
    }

    @Override
    public void save(Purchase purchase) {
        if (Purchase.getNumber() == null){
            purchase.setNumber(UUID.randomUUID().toString()); // symulacja generowania ID przez bazę danych
        }
        fakeDatabase.put(Purchase.getNumber(), purchase);
    }
}
