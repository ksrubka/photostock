package pl.com.bottega.photostock.sales.infrastructure.repositories.file_repositories;

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
        //create input stream from a given path
        try (InputStream is = new FileInputStream(path);) {
            //read one line from input stream
            readLine(is);
            //init a string
            String line;
            //check if another line is not null - if there is anything returned by readLine()
            while ((line = readLine(is)) != null) {
                //check for whitespaces, if there are any trim them and
                // check if after trimming there's nothing left in that line
                if (line.trim().length() == 0)
                    // if there is nothing but whitespaces, return null
                    return null;
                // else create a product
                Product product;
                // from parsing it from current line
                product = parseProduct(line);
                //but check if number of that product equals nr from argument
                if (product.getNumber().equals(nr))
                    // if so - return that product
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
    //save given object into the file (located on path specified in path field)
    public void save(Product product) {
        //create new file in path
        //but what if that file already exist? - problem
        File file = new File(this.path);
        //see if file not exists
        boolean newRepo = !file.exists();
        // create OutputStream to write into a file
        try (OutputStream os = new FileOutputStream(this.path, true)) {
            // if file not exist
            if (newRepo)
                //add first line - header
                os.write("number,price,priceCurrency,available,length,tags,type\r\n".getBytes());
            //then create array from product characteristics
            String[] productExported = product.export();
            //use that array to create String separated by commas
            String csvLine = String.join(",", productExported) + "\r\n";
            //write that String into the OutputStream
            os.write(csvLine.getBytes());
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
