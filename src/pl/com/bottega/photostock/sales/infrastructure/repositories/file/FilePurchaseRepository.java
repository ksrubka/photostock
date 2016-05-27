package pl.com.bottega.photostock.sales.infrastructure.repositories.file;

import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ClientRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ProductRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.PurchaseRepository;
import pl.com.bottega.photostock.sales.model.Purchase;

import java.util.List;
import java.util.Set;

/**
 * Created by Beata Iłowiecka on 24.05.16.
 */
public class FilePurchaseRepository implements PurchaseRepository{

    private final String path;
    ClientRepository clientRepository;
    ProductRepository productRepository;

    public FilePurchaseRepository(String path, ClientRepository clientRepository, ProductRepository productRepository) {
        this.path = path;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Purchase load(String number) {
        return null;
    }

    @Override
    public void save(Purchase purchase) {

    }

    @Override
    public Set<Purchase> getPurchases() {
        return null;
    }

    @Override
    public List<Purchase> find(String clientNr) {
        return null;
    }
}
