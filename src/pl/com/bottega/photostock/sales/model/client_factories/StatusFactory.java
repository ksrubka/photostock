package pl.com.bottega.photostock.sales.model.client_factories;

import pl.com.bottega.photostock.sales.model.ClientStatus;
import pl.com.bottega.photostock.sales.model.exceptions.InappropriateClientStatusException;

/**
 * Created by Beata Iłowiecka on 24.05.16.
 */
public class StatusFactory {

    public static ClientStatus create(String clientStatus) {
        switch (clientStatus) {
            case "standard":
                return ClientStatus.STANDARD;
            case "silver":
                return ClientStatus.SILVER;
            case "gold":
                return ClientStatus.GOLD;
            case "platinum":
                return ClientStatus.PLATINUM;
            case "vip":
                return ClientStatus.VIP;
            default:
                throw new InappropriateClientStatusException("Niepoprawny status: " + clientStatus);
        }
    }
}
