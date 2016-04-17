package pl.com.bottega.photostock.sales.model.products;

/**
 * Created by Beata Iłowiecka on 17.04.16.
 */
public interface ProductRepository {

    Product load(String number);
    void save(Product product);
}
