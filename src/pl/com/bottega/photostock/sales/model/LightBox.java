package pl.com.bottega.photostock.sales.model;

import pl.com.bottega.photostock.sales.model.exceptions.ProductNotAvailableException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Beata Iłowiecka on 12.03.2016.
 */
public class LightBox {

    private String name;
    private Client owner;
    private List<Product> items = new ArrayList<>();
    private boolean closed;
    private String number;

    public LightBox(Client owner) {
        this.owner = owner;
    }

    public void close() {
        this.closed = true;
    }

    public void add(Product... products)throws IllegalStateException, IllegalArgumentException {

        validate();

        for (Product product : products) {
            if (items.contains(product)) {
                throw new IllegalArgumentException("LightBox już zawiera ten produkt.");
            }
            if (!product.isAvailable()){
                throw new ProductNotAvailableException("Produkt jest nieaktywny ", product.getNumber(), product.getClass());
            }
            items.add(product);
        }
    }

    public void remove(Product productToRemove) throws IllegalArgumentException {

        validate();

        boolean removed = items.remove(productToRemove);

        if (!removed) {
            throw new IllegalArgumentException("Nie ma takiego produktu w LightBoxie");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Client getOwner() {
        return owner;
    }

    public List<Product> getItems() {
        return items;
    }

    public int getItemsCount() {
        return items.size();
    }

    public void changeName(String newName) throws IllegalStateException {
        validate();
        this.name = newName;
    }

    private void validate() {
        if (closed) {
            throw new IllegalStateException("Przepraszamy, ten lightBox został zamknięty.");
        }
        if (!owner.isActive())
                throw new IllegalStateException("Użytkownik jest nieaktywny");
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}