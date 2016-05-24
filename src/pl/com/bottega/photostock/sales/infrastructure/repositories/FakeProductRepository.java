package pl.com.bottega.photostock.sales.infrastructure.repositories;

import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ProductRepository;
import pl.com.bottega.photostock.sales.model.Money;
import pl.com.bottega.photostock.sales.model.Product;
import pl.com.bottega.photostock.sales.model.exceptions.ProductNotAvailableException;
import pl.com.bottega.photostock.sales.model.products.*;

import java.util.*;

/**
 * Created by Beata Iłowiecka on 17.04.16.
 */
public class FakeProductRepository implements ProductRepository {

    private static Map<String, Product> fakeDatabase = new HashMap<>();

    // inicjalizator klasy ale nie dla obiektów tej klasy
    static {
        Product mustang = new Picture("nr1", new Money(10), new String[] {"ford", "mustang"});
        Product multipla = new Picture("nr2", new Money(10), new String[] {"fiat", "multipla"}, false);
        Product mazda = new Picture("nr3", new Money(10), new String[] {"mazda", "multipla"});
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
            throw new ProductNotAvailableException("Produkt " + number + " nie istnieje", number);
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

    @Override
    public List<Product> find(String nameFragment, String[] tags, Money priceMin, Money priceMax, boolean acceptNotAvailable) {
        List<Product> products = new LinkedList<>();
        if ((acceptNotAvailable && (tags == null || tags.length == 0) && priceMax == null && priceMin == null))
            return new ArrayList<>(fakeDatabase.values());
        for (Product product : fakeDatabase.values()){
            if(!(acceptNotAvailable || product.isAvailable()))
                continue;
            if (priceMin != null && product.getPrice().greaterThan(priceMin))
                products.add(product);
            if (priceMax != null && product.getPrice().lowerThan(priceMax))
                products.add(product);
            //// TODO: 14.05.2016
        }
        return products;
    }
}