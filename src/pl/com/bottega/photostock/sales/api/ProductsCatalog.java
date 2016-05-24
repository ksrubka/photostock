package pl.com.bottega.photostock.sales.api;

import pl.com.bottega.photostock.sales.infrastructure.repositories.*;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ProductRepository;
import pl.com.bottega.photostock.sales.model.Money;
import pl.com.bottega.photostock.sales.model.Product;

import java.util.*;

/**
 * Created by Beata Iłowiecka on 23.04.2016.
 */
public class ProductsCatalog {

    private ProductRepository productRepository = new FakeProductRepository();

    public List<Product> find(String nameFragment, String[] tags, Money priceMin, Money priceMax, boolean isNotAvailable){
        List<Product> products = new LinkedList<>();
        return productRepository.find(nameFragment, tags, priceMin, priceMax, isNotAvailable);
    }

    /*private boolean hasRightPrice(Product product, Money priceMin, Money priceMax) {
        return product.getPrice().greaterOrEqual(priceMin)
                && product.getPrice().lowerOrEqual(priceMax);
    }

    private boolean containsTags(Product product, String[] tags){
        if (product instanceof Picture){
            Set<String> productTags = new HashSet<>(Arrays.asList((product).getTags()));
            Set<String> tagsToCompare = new HashSet<>(Arrays.asList(tags));
            return productTags.containsAll(tagsToCompare);
        }
        return
    }*/
}