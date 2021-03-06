package pl.com.bottega.photostock.sales.model.client_factories;

import pl.com.bottega.photostock.sales.model.client_strategies.charging.ChargingStrategy;
import pl.com.bottega.photostock.sales.model.ClientStatus;
import pl.com.bottega.photostock.sales.model.client_strategies.charging.StandardChargingStrategy;
import pl.com.bottega.photostock.sales.model.client_strategies.charging.VIPChargingStrategy;

/**
 * Created by Beata Iłowiecka on 22.04.16.
 */
public class StrategyFactory {
    public static ChargingStrategy create(ClientStatus clientStatus){
        if (clientStatus == ClientStatus.VIP)
            return new VIPChargingStrategy();
        else
            return new StandardChargingStrategy();
    }
}
