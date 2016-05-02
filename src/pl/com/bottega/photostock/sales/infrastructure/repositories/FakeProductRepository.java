package pl.com.bottega.photostock.sales.infrastructure.repositories;

import pl.com.bottega.photostock.sales.model.Product;
import pl.com.bottega.photostock.sales.model.products.*;

import java.util.*;

/**
 * Created by Beata Iłowiecka on 17.04.16.
 */
public class FakeProductRepository implements ProductRepository {

    private static Map<String, Product> fakeDatabase = new HashMap<>();

    // inicjalizator klasy ale nie dla obiektów tej klasy
    static {
        Product mustang = new Picture("mustang", "nr1", 10, new String[] {"ford", "mustang"});
        Product multipla = new Picture("multipla", "nr2", 10, new String[] {"fiat", "multipla"});
        Product mazda = new Picture("mazda", "nr3", 10, new String[] {"mazda", "multipla"});
        Product programming = new Picture(); // nr4
        Product boy = new Clip(); // nr5
        Product ceiling = new Song(); //nr6


        fakeDatabase.put(mustang.getNumber(), mustang);
        fakeDatabase.put(multipla.getNumber(), multipla);
        fakeDatabase.put(mazda.getNumber(), mazda);
        fakeDatabase.put(programming.getNumber(), programming);
        fakeDatabase.put(boy.getNumber(), boy);
        fakeDatabase.put(ceiling.getNumber(), ceiling);
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
        if (product.getNumber() == null){
            product.setNumber(UUID.randomUUID().toString()); // symulacja generowania ID przez bazę danych
        }
        fakeDatabase.put(product.getNumber(), product);
    }

    public Set<Product> getProducts(){
        Set<Product> products = new HashSet<>();
        for(Map.Entry<String, Product> entry : fakeDatabase.entrySet()){
            Product product = entry.getValue();
            products.add(product);
        }
        return products;
    }
}