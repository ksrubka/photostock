package pl.com.bottega.photostock.sales.infrastructure;

import pl.com.bottega.photostock.sales.model.products.Clip;
import pl.com.bottega.photostock.sales.model.products.Picture;
import pl.com.bottega.photostock.sales.model.products.Product;
import pl.com.bottega.photostock.sales.model.products.ProductRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Beata Iłowiecka on 17.04.16.
 */
public class FakeProductRepository implements ProductRepository {

    private static Map<String, Product> fakeDatabase = new HashMap<>();

    // inicjalizator klasy ale nie dla obiektów tej klasy
    static {
        Picture mustang = new Picture("nr1", 10, new String[] {"ford", "mustang"});
        Picture multipla = new Picture("nr2", 10, new String[] {"fiat", "multipla"});
        Picture mazda = new Picture("nr3", 10, new String[] {"mazda", "multipla"});
        Picture kotek = new Picture(); // nr: 01
        Clip clip = new Clip(); // 01a

        fakeDatabase.put(mustang.getNumber(), mustang);
        fakeDatabase.put(multipla.getNumber(), multipla);
        fakeDatabase.put(mazda.getNumber(), mazda);
        fakeDatabase.put(kotek.getNumber(), kotek);
        fakeDatabase.put(clip.getNumber(), clip);
    }
    @Override
    public Product load(String number) {
        Product product = fakeDatabase.get(number);
        if  (product == null){
            throw new RuntimeException("Product " + number + " does not exist");
        }
        return product;
    }

    @Override
    public void save(Product product) {
        fakeDatabase.put(product.getNumber(), product);
    }
}