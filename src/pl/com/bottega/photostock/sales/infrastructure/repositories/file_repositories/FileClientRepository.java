package pl.com.bottega.photostock.sales.infrastructure.repositories.file_repositories;

import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ClientRepository;
import pl.com.bottega.photostock.sales.model.Client;

/**
 * Created by Beata Iłowiecka on 24.05.16.
 */
public class FileClientRepository implements ClientRepository {

    private final String path;

    public FileClientRepository(String path) {
        this.path = path;
    }

    @Override
    public Client load(String number) {
        return null;
    }

    @Override
    public void save(Client client) {

    }
}
