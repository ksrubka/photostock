package pl.com.bottega.photostock.sales.infrastructure.repositories.file;

import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ProductRepository;
import pl.com.bottega.photostock.sales.model.Money;
import pl.com.bottega.photostock.sales.model.Product;
import pl.com.bottega.photostock.sales.model.exceptions.DataAccessException;
import pl.com.bottega.photostock.sales.model.products.Clip;
import pl.com.bottega.photostock.sales.model.products.Picture;
import java.io.*;
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
        try (InputStream is = new FileInputStream(path);) {
            readLine(is);
            String line;
            while ((line = readLine(is)) != null) {
                if (line.trim().length() == 0)
                    return null;
                Product product;
                product = parseProduct(line);
                if (product.getNumber().equals(nr))
                    return product;
            }
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
        return null;
    }

    // [0]number,[1]priceCents,[2]priceCurrency,[3]available,[4]length,[5]tags,[6]type
    private Product parseProduct(String line) {
        Product product;
        String[] components = line.split(",");
        String number = components[0];
        int priceCents = Integer.parseInt(components[1]);
        Money price = new Money(priceCents / 100, priceCents % 100, components[2]);
        boolean active = Boolean.parseBoolean(components[3]);
        if (components[6].startsWith("Picture")) {
            String[] tags = components[5].split(" ");
            product = new Picture(number, price, tags, active);
        }
        else {
            int length = Integer.parseInt(components[4]);
            product = new Clip(number, price, length, active);
        }
        return product;
    }

    private String readLine(InputStream is) throws IOException{
        int ch;
        StringBuilder sb = new StringBuilder();
        while(((ch = is.read()) != '\n') && ch != -1)
            sb.append((char)ch);
        return sb.toString();
    }

    @Override
    public void save(Product product) {
        File file = new File(this.path);
        boolean newRepo = !file.exists();
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(path, true))) {
            if (newRepo)
                pw.write("number,price,priceCurrency,available,length,tags,type\n");
            String[] productExported = product.export();
            String csvLine = String.join(",", productExported) + "\n";
            pw.write(csvLine);
        }
        catch (Exception ex) {
            throw new DataAccessException(ex);
        }
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