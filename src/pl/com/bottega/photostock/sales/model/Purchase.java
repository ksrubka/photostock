package pl.com.bottega.photostock.sales.model;

import java.util.Date;
import java.util.List;

/**
 * Created by Beata Iłowiecka on 12.03.2016.
 */
public class Purchase {

    private Date purchaseDate;
    private Client owner;
    private List<Product> items;
    private String number;

    public Purchase(Client owner, List<Product> items) {
        this.owner = owner;
        for (Product product : items)
            product.sellPer(owner);
        this.items = items;
        this.purchaseDate = new Date();
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

    public List<Product> getItems() {
        return items;
    }

    public void setDate(Date date) {
        this.purchaseDate = date;
    }

    //[0]number,[1]ownerName,[2]ownerNumber,[3]date,[4]productsNumbers
    public String[] export() {
        return new String[]{
                getNumber(),
                owner.getName(),
                owner.getNumber(),
                //todo data!
                getProductsNumbers()
        };
    }

    public String getProductsNumbers() {
        StringBuilder productsNumbers = new StringBuilder();
        int count = 0;
        for (Product item : items) {
            productsNumbers.append(item.getNumber());
            if (count < items.size()-1)
                productsNumbers.append(" ");
            count++;
        }
        return productsNumbers.toString();
    }
}