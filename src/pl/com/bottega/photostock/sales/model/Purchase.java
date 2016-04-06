package pl.com.bottega.photostock.sales.model;

import pl.com.bottega.photostock.sales.model.products.Picture;

import java.util.Date;

/**
 * Created by Beata Iłowiecka on 12.03.2016.
 */
public class Purchase {

    private Date PurchaseData;
    private Client owner;
    private Picture[] items;

    public Purchase(Client owner, Picture[] items) {
        this.owner = owner;
        this.items = items;
    }
}
