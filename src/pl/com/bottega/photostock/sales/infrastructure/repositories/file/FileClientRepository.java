package pl.com.bottega.photostock.sales.infrastructure.repositories.file;

import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ClientRepository;
import pl.com.bottega.photostock.sales.model.ClientStatus;
import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.exceptions.DataAccessException;

import java.io.*;

/**
 * Created by Beata Iłowiecka on 24.05.16.
 */
public class FileClientRepository implements ClientRepository {

    private final String path;

    public FileClientRepository(String path) {
        this.path = path;
    }

    @Override
    public Client load(String nr){
        try (BufferedReader br = new BufferedReader(new FileReader(path), 512)) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().length() == 0)
                    return null;
                Client client = parseClient(line);
                if (client.getNumber().equals(nr))
                    return client;
            }
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
        return null;
    }

    //[0]name,[1]address,[2]status,[3]amount,[4]debt,[5]creditLimit,[6]active,[7]number
    private Client parseClient(String line) {
        Client client;
        String[] components = line.split(",");
        String name = components[0];
        String address = components[1];
        ClientStatus status = ClientStatus.valueOf(components[2].toUpperCase());
        double amount = Double.valueOf(components[3]);
        boolean active = Boolean.parseBoolean(components[6]);
        String number = components[7];
        if (components[5].contentEquals("0.0")) {
            client = new Client(name, address, status, amount, active, number);
            return client;
        }
        else {
            double creditLimit = Double.valueOf(components[5]);
            client = new Client(name, address, amount, creditLimit, number);
            return client;
        }
    }

    @Override
    public void save(Client client) {
        File file = new File(this.path);
        boolean newRepo = !file.exists();
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(path, true))) {
            if (newRepo)
                pw.write("name,address,status,amount,debt,creditLimit,active,number\n");
            String[] clientExported = client.export();
            String csvLine = String.join(",", clientExported) + "\n";
            pw.write(csvLine);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }
}