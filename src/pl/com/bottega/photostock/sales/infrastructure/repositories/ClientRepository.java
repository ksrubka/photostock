package pl.com.bottega.photostock.sales.infrastructure.repositories;

import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.Product;

/**
 * Created by Beata Iłowiecka on 21.04.16.
 */
public interface ClientRepository {

    Client load(String name);
    void save(Client client);
}
