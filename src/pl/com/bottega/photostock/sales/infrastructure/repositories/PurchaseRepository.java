package pl.com.bottega.photostock.sales.infrastructure.repositories;

import pl.com.bottega.photostock.sales.model.Purchase;
import java.util.Set;

/**
 * Created by Beata Iłowiecka on 23.04.2016.
 */
public interface PurchaseRepository {

    Purchase load(String number);
    void save(Purchase purchase);

    Set<Purchase> getPurchases();
}
