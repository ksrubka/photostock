package pl.com.bottega.photostock.sales.infrastructure.repositories.file;

import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.LightBoxRepository;
import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.LightBox;
import pl.com.bottega.photostock.sales.model.exceptions.DataAccessException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Beata Iłowiecka on 24.05.16.
 */
public class FileLightBoxRepository implements LightBoxRepository {

    private final String path;

    public FileLightBoxRepository(String path) {
        this.path = path;
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

    private LightBox parseLightBox(String line) {
        return null;
    }

    @Override
    public void save(LightBox lightBox) {

    }
}
