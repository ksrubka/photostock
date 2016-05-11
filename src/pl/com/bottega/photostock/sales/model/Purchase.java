package pl.com.bottega.photostock.sales.model;

import pl.com.bottega.photostock.sales.model.exceptions.InappropriateClientStatusException;

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
    private String number;

    public Purchase(Client owner, List<Product> items) {
        this.owner = owner;
        for (Product product : items)
            product.sellPer(owner);
        this.items = items;
        this.createDate = new Date();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Client getOwner() {
        return owner;
    }

}