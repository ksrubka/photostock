package pl.com.bottega.photostock.sales.infrastructure.repositories.file;

import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ClientRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.LightBoxRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ProductRepository;
import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.LightBox;
import pl.com.bottega.photostock.sales.model.Product;
import pl.com.bottega.photostock.sales.model.exceptions.DataAccessException;

import java.io.*;

/**
 * Created by Beata Iłowiecka on 24.05.16.
 */
public class FileLightBoxRepository implements LightBoxRepository {

    private final String path;
    ClientRepository clientRepository;
    ProductRepository productRepository;

    public FileLightBoxRepository(String path, ClientRepository clientRepository, ProductRepository productRepository) {
        this.path = path;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
    }

    @Override
    public LightBox load(String number) {
        try (BufferedReader br = new BufferedReader(new FileReader(path), 512)) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().length() == 0)
                    return null;
                LightBox lightBox = parseLightBox(line);
                if (lightBox.getNumber().equals(number))
                    return lightBox;
            }
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
        return null;
    }

    //[0]number,[1]ownerName,[2]ownerNumber,[3]active,[4]productsNumbers
    private LightBox parseLightBox(String line) {
        String[] components = line.split(",");
        String number = components[0];
        String ownerNr = components[2];
        boolean active = Boolean.valueOf(components[3]);
        String[] productsNrs = components[4].split(" ");

        LightBox lightBox = initLightBox(ownerNr);
        lightBox.setNumber(number);
        Product product;
        for (String nr : productsNrs) {
            product = productRepository.load(nr);
            lightBox.add(product);
        }
        if (!active)
            lightBox.close();
        return lightBox;
    }

    private LightBox initLightBox(String ownerNr) {
        Client owner = clientRepository.load(ownerNr);
        return new LightBox(owner);
    }

    @Override
    public void save(LightBox lightBox) {
        File file = new File(this.path);
        boolean newRepo = !file.exists();
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(path, true))) {
            if (newRepo)
                pw.write("number,ownerName,ownerNumber,active,productsNumbers\n");
            String[] lightBoxExported = lightBox.export();
            String csvLine = String.join(",", lightBoxExported) + "\n";
            pw.write(csvLine);
        } catch (Exception ex) {
            throw new DataAccessException(ex);
        }
    }
}
