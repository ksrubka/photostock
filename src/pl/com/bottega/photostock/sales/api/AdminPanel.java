package pl.com.bottega.photostock.sales.api;

import pl.com.bottega.photostock.sales.infrastructure.repositories.ClientRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.FakeClientRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.FakeProductRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.ProductRepository;
import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.Money;
import pl.com.bottega.photostock.sales.model.Product;
import pl.com.bottega.photostock.sales.model.client_factories.ProductFactory;
import pl.com.bottega.photostock.sales.model.exceptions.InappropriateClientStatusException;
import pl.com.bottega.photostock.sales.model.products.ProductType;

/**
 * Created by Beata Iłowiecka on 02.05.16.
 */
public class AdminPanel {

    private ProductRepository productRepository = new FakeProductRepository();
    private ClientRepository clientRepository = new FakeClientRepository();

    public void addProduct(String name, double price, String[] tags, ProductType productType){
        Product product = ProductFactory.create(name, null, price, tags, productType);
        productRepository.save(product);
    }

    public void promoteClient(String clientNr){
        Client client = clientRepository.load(clientNr);
        client.setVipStatus();
        clientRepository.save(client);
    }

    public void changeCreditLimit(String clientNr, double amount) throws InappropriateClientStatusException {
        Client client = clientRepository.load(clientNr);
        if (client.isVip()){
            client.getChargingStrategy().setCreditLimit(new Money(amount));
            clientRepository.save(client);;
        }
        else
            throw new InappropriateClientStatusException("This client is not a VIP: ", clientNr);
    }
}
