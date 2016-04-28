package pl.com.bottega.photostock.sales.model.client_strategies.charging;

import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.Money;

/**
 * Created by Beata Iłowiecka on 28.04.16.
 */
public interface Charging {

    Money getAmount();
    void setAmount(Money amount);
}
