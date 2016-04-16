package pl.com.bottega.photostock.sales.model;

import pl.com.bottega.photostock.sales.model.products.Picture;
import pl.com.bottega.photostock.sales.model.products.Product;

import java.util.Date;
import java.util.List;

/**
 * Created by Beata Iłowiecka on 12.03.2016.
 */
public class Purchase {

    private Date PurchaseData;
    private Client owner;
    private Date createDate;
    private List<Product> items;

    public Purchase(Client owner, List<Product> items) {
        this.owner = owner;
        this.items = items;
        this.createDate = new Date();
    }
}