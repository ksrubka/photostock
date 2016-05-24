package pl.com.bottega.photostock.sales.infrastructure.repositories.file_repositories;

import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ClientRepository;
import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.exceptions.DataAccessException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Beata Iłowiecka on 24.05.16.
 */
public class FileClientRepository implements ClientRepository {

    private final String path;

    public FileClientRepository(String path) {
        this.path = path;
    }

    //name,address,status,amount,debt,creditLimit,active,number
    @Override
    public Client load(String nr){
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().length() == 0)
                    return null;
                Client client = parseClient(line);
                if (client.getNumber().equals(nr))
                    return client;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + path);
        } catch (IOException e) {
            System.out.println("Unable to read file: " + path);
            throw new DataAccessException(e);
        }
        return null;
    }

    private Client parseClient(String line) {
        return null;
    }

    @Override
    public void save(Client client) {

    }
}
