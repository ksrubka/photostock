package pl.com.bottega.photostock.sales.api;

import pl.com.bottega.photostock.sales.infrastructure.repositories.FakeProductRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.ProductRepository;
import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.Product;
import pl.com.bottega.photostock.sales.model.client_factories.ProductFactory;
import pl.com.bottega.photostock.sales.model.products.PRODUCT_TYPE;

import java.util.UUID;

/**
 * Created by Beata Iłowiecka on 02.05.16.
 */
public class AdminPanel {

    private ProductRepository productRepository = new FakeProductRepository();

    public void addProduct(String name, double price, String[] tags, PRODUCT_TYPE productType){
        String productNumber = UUID.randomUUID().toString();
        Product product = ProductFactory.create(name, productNumber, price, tags, productType);
        productRepository.save(product);
    }

    public void promoteClient(String clientNr){

    }

    public void changeCreditLimit(Client client){
        if (client.isVip()){

        }
    }
}
