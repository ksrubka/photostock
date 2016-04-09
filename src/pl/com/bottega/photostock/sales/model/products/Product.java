package pl.com.bottega.photostock.sales.model.products;

import pl.com.bottega.photostock.sales.model.Client;

/**
 * Created by Beata Iłowiecka on 06.04.16.
 */
public interface Product {

    boolean isAvailable();

    double calculatePrice();

    double getPrice();

    //make inactive
    void cancel();

    void reservePer(Client client);

    void unreservePer(Client client);

    boolean canBeReservedBy(Client client);

    public void setShared(boolean bool);
}
