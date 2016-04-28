package pl.com.bottega.photostock.sales.model.client_factories;

import pl.com.bottega.photostock.sales.model.ClientStatus;
import pl.com.bottega.photostock.sales.model.client_strategies.ApprovingStrategy;
import pl.com.bottega.photostock.sales.model.client_strategies.StandardApprovingStrategy;
import pl.com.bottega.photostock.sales.model.client_strategies.VIPApprovigStrategy;

/**
 * Created by Beata Iłowiecka on 22.04.16.
 */
public class ApprovingFactory {

    public static ApprovingStrategy create(ClientStatus status){
        if (status == ClientStatus.VIP)
            return new VIPApprovigStrategy();
        else
            return  new StandardApprovingStrategy();
    }
}
