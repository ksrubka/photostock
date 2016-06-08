package pl.com.bottega.photostock.sales.model;

import com.google.common.base.Objects;

import java.util.Date;
import java.util.List;

/**
 * Created by Beata Iłowiecka on 12.03.2016.
 */
public class Purchase {

    private String number;
    private Client owner;
    private Date purchaseDate;
    private List<Product> items;

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
                String.valueOf(getDate().getTime()),
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return Objects.equal(number, purchase.number) &&
                Objects.equal(owner, purchase.owner) &&
                Objects.equal(purchaseDate, purchase.purchaseDate) &&
                Objects.equal(items, purchase.items);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(number, owner, purchaseDate, items);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("number", number)
                .add("owner", owner)
                .add("purchaseDate", purchaseDate)
                .add("items", items)
                .toString();
    }

    public Date getDate() {
        return purchaseDate;
    }
}