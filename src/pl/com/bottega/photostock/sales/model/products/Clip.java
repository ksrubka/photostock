package pl.com.bottega.photostock.sales.model.products;

import pl.com.bottega.photostock.sales.model.Client;

/**
 * Created by Beata Iłowiecka on 06.04.16.
 */
public class Clip implements Product{



    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public double calculatePrice() {
        return 0;
    }

    @Override
    public void cancel() {

    }

    @Override
    public void reservePer(Client client) {

    }

    @Override
    public void unreservePer(Client client) {

    }

}
