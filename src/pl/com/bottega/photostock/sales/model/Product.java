package pl.com.bottega.photostock.sales.model;

import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.Money;
import pl.com.bottega.photostock.sales.model.exceptions.InappropriateClientStatusException;

/**
 * Created by Beata Iłowiecka on 06.04.16.
 */
public interface Product {

    boolean isAvailable();

    Money calculatePrice();

    Money getPrice();

    //make inactive
    void cancel();

    void reservePer(Client client);

    void unreservePer(Client client);

    void unreserve();

    boolean canBeReservedBy(Client client);

    void setShared(boolean bool);

    String getNumber();

    void setNumber(String nr);

    void sellPer(Client client);

    void undoPurchase();

    String[] export();

    String[] getTags();

    boolean hasSameCurrencyAs(Client client);
}