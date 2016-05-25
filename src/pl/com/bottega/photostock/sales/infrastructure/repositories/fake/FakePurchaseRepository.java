package pl.com.bottega.photostock.sales.infrastructure.repositories.fake;

import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.PurchaseRepository;
import pl.com.bottega.photostock.sales.model.Purchase;

import java.util.*;

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
        if (purchase.getNumber() == null){
            purchase.setNumber(UUID.randomUUID().toString()); // symulacja generowania ID przez bazę danych
        }
        fakeDatabase.put(purchase.getNumber(), purchase);
    }

    @Override
    public Set<Purchase> getPurchases() {
        Set<Purchase> purchases = new HashSet<>();
        for(Map.Entry<String, Purchase> entry : fakeDatabase.entrySet()){
            Purchase purchase = entry.getValue();
            purchases.add(purchase);
        }
        return purchases;
    }

    @Override
    public List<Purchase> find(String clientNr) {
        List<Purchase> purchases = new ArrayList<>();
        for (Purchase purchase : fakeDatabase.values()){
            if (purchase.getOwner().equals(clientNr))
                purchases.add(purchase);
        }
        return purchases;
    }
}
