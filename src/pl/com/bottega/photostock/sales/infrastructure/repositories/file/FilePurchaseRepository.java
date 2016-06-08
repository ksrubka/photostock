package pl.com.bottega.photostock.sales.infrastructure.repositories.file;

import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ClientRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ProductRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.PurchaseRepository;
import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.Product;
import pl.com.bottega.photostock.sales.model.Purchase;
import pl.com.bottega.photostock.sales.model.exceptions.DataAccessException;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Beata Iłowiecka on 24.05.16.
 */
public class FilePurchaseRepository implements PurchaseRepository {

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
        try (BufferedReader br = new BufferedReader(new FileReader(path), 512)) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().length() == 0)
                    return null;
                Purchase purchase = parsePurchase(line);
                if (purchase.getNumber().equals(number))
                    return purchase;
            }
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
        return null;
    }

    //[0]number,[1]ownerName,[2]ownerNumber,[3]date,[4]productsNumbers
    private Purchase parsePurchase(String line) {
        String[] components = line.split(",");
        String number = components[0];
        String ownerNr = components[2];
        long milisecSince1970 = Long.valueOf(components[3]);
        String[] productsNrs = components[4].split(" ");
        Purchase purchase = initPurchase(ownerNr, productsNrs);
        purchase.setNumber(number);
        purchase.setDate(new Date(milisecSince1970));
        return purchase;
        }

    private Purchase initPurchase(String ownerNr, String[] productsNrs) {
        Client client = clientRepository.load(ownerNr);
        List<Product> items = initItems(productsNrs);
        return new Purchase(client, items);
    }

    private List<Product> initItems(String[] productsNrs) {
        List<Product> items = new ArrayList<>();
        Product product;
        for (String nr : productsNrs) {
            product = productRepository.load(nr);
            items.add(product);
        }
        return items;
    }

    @Override
    public void save(Purchase purchase) {
        File file = new File(path);
        boolean newRepo = !file.exists();
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(path, true))) {
            if (newRepo)
                pw.write("number,ownerName,ownerNumber,date,productsNumbers\n");
            String[] purchaseExported = purchase.export();
            String csvLine = String.join(",", purchaseExported) + "\n";
            pw.write(csvLine);
        }
        catch (Exception ex) {
            throw new DataAccessException(ex);
        }
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