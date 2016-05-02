package pl.com.bottega.photostock.sales.model;

import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.Money;

/**
 * Created by Beata Iłowiecka on 06.04.16.
 */
public interface Product {

    boolean isAvailable();

    double calculatePrice();

    Money getPrice();

    //make inactive
    void cancel();

    void reservePer(Client client);

    void unreservePer(Client client);

    boolean canBeReservedBy(Client client);

    void setShared(boolean bool);

    String getNumber();

    void setNumber(String nr);

    String getName();

    String[] getTags();
}
