package pl.com.bottega.photostock.sales.infrastructure.repositories;

import pl.com.bottega.photostock.sales.model.Money;
import pl.com.bottega.photostock.sales.model.Product;

import java.util.List;
import java.util.Set;

/**
 * Created by Beata Iłowiecka on 17.04.16.
 */
public interface ProductRepository {

    Product load(String number);
    void save(Product product);

    Set<Product> getProducts();

    List<Product> find(String nameFragment, String[] tags, Money priceMin, Money priceMax, boolean acceptNotAvailable);
}
