package pl.com.bottega.photostock.sales.infrastructure.repository;

import pl.com.bottega.photostock.sales.infrastructure.repositories.ProductRepository;
import pl.com.bottega.photostock.sales.model.Money;
import pl.com.bottega.photostock.sales.model.Product;
import pl.com.bottega.photostock.sales.model.products.Clip;
import pl.com.bottega.photostock.sales.model.products.Picture;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.List;
import java.util.Set;

/**
 * Created by Beata Iłowiecka on 14.05.2016.
 */
public class FileProductRepository implements ProductRepository {


    private final String path;

    public FileProductRepository(String path) {
        this.path = path;
    }

    @Override
    public Product load(String nr) {
        InputStream is = null;
        try {
            is = new FileInputStream(path);
            readLine(is);
            String line;
            while ((line = readLine(is)) != null){
                String[] components = line.split(",");
                Product product;
                String number = components[0];
                if (number != nr)
                    continue;
                int priceCents = Integer.parseInt(components[1]);
                Money price = new Money(priceCents / 100, priceCents % 100, components[2]);
                String[] tags = components[4].split(" ");
                int duration = Integer.parseInt(components[3]);
                boolean active = Boolean.parseBoolean(components[5]);
                if (components[6] == "Picture")
                    product = new Picture("", number, priceCents, tags, active);
                else
                    product = new Clip("", number, priceCents, tags, Duration.ofSeconds(duration));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            try {
                is.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    private String readLine(InputStream is) {
        int ch;
        StringBuilder sb = new StringBuilder();
        try {
            while(((ch = is.read()) != '\n') && ch != -1) {
                sb.append((char)ch);
            }
            return sb.toString();
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void save(Product product) {

    }

    @Override
    public Set<Product> getProducts() {
        return null;
    }

    @Override
    public List<Product> find(String nameFragment, String[] tags, Money priceMin, Money priceMax, boolean acceptNotAvailable) {
        return null;
    }
}
