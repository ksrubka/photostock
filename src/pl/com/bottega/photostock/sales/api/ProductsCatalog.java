package pl.com.bottega.photostock.sales.api;

import pl.com.bottega.photostock.sales.infrastructure.repositories.*;
import pl.com.bottega.photostock.sales.model.Money;
import pl.com.bottega.photostock.sales.model.Product;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by Beata Iłowiecka on 23.04.2016.
 */
public class ProductsCatalog {

    private ProductRepository productRepository = new FakeProductRepository();

    public List<Product> find(String nameFragment, String[] tags, double priceMin, double priceMax){
        List<Product> products = new ArrayList<>(); //set?
        for (Product product : productRepository.getProducts()){
            if (hasRightPrice(product, priceMin, priceMax)
                    || product.getName().contains(nameFragment)
                    || containsTags(product, tags))
                products.add(product);
        }
        return products;
    }

    private boolean hasRightPrice(Product product, double priceMin, double priceMax) {
        return product.getPrice().greaterOrEqual(new Money(priceMin)) && product.getPrice().lowerOrEqual(new Money(priceMax));
    }

    private boolean containsTags(Product product, String[] tags){
        Set<String> productTags = new HashSet<>(Arrays.asList(product.getTags()));
        Set<String> tagsToCompare = new HashSet<>(Arrays.asList(tags));
        return productTags.containsAll(tagsToCompare);
    }
}