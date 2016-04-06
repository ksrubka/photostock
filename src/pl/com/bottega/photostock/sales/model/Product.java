package pl.com.bottega.photostock.sales.model;

/**
 * Created by Beata Iłowiecka on 06.04.16.
 */
public interface Product {

    boolean isAvailable();

    double calculatePrice();

    //make inactive
    void cancel();

    void reservePer(Client client);

    void unreservePer(Client client);
}
