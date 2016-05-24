package pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces;

import pl.com.bottega.photostock.sales.model.LightBox;
import pl.com.bottega.photostock.sales.model.Product;

/**
 * Created by Beata Iłowiecka on 21.04.16.
 */
public interface LightBoxRepository {

    LightBox load(String number);
    void save(LightBox lightBox);
}
