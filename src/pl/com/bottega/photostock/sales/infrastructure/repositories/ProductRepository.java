package pl.com.bottega.photostock.sales.infrastructure.repositories;

import pl.com.bottega.photostock.sales.model.Product;

import java.util.Set;

/**
 * Created by Beata Iłowiecka on 17.04.16.
 */
public interface ProductRepository {

    Product load(String number);
    void save(Product product);

    Set<Product> getProducts();
}
