package pl.com.bottega.photostock.sales.model;

import pl.com.bottega.photostock.sales.model.exceptions.ProductNotAvailableException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Beata Iłowiecka on 12.03.2016.
 */
public class LightBox {

    private String name;
    private String number;
    private Client owner;
    private List<Client> owners = new ArrayList<>();
    private List<Product> items = new ArrayList<>();
    private boolean closed;

    public LightBox(Client owner) {
        this.owner = owner;
    }

    public void addOwner(Client owner){
        this.owners.add(owner);
    }

    public void close() {
        this.closed = true;
    }

    public void add(Product... products)throws IllegalStateException, IllegalArgumentException {
        validate();
        for (Product product : products) {
            if (items.contains(product))
                throw new IllegalArgumentException("LightBox już zawiera ten produkt.");
            if (!product.isAvailable())
                throw new ProductNotAvailableException("Produkt " + product.getNumber() + " jest nieaktywny ", product.getNumber());
            items.add(product);
        }
    }

    public void remove(Product productToRemove) throws IllegalArgumentException {
        validate();
        boolean removed = items.remove(productToRemove);
        if (!removed)
            throw new IllegalArgumentException("Nie ma takiego produktu w LightBoxie");
    }

    public void changeName(String newName) throws IllegalStateException {
        validate();
        this.name = newName;
    }

    private void validate() throws IllegalStateException {
        if (closed)
            throw new IllegalStateException("Przepraszamy, ten lightBox został zamknięty.");
        for (Client owner : owners) {
            if (!owner.isActive())
                throw new IllegalStateException("Użytkownik jest nieaktywny");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Client> getOwners() {
        return owners;
    }

    public List<Product> getItems() {
        return items;
    }

    public int getItemsCount() {
        return items.size();
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

    public boolean isActive() {
        return !closed;
    }

    //[0]number,[1]ownerName,[2]ownerNumber,[3]active,[4]productsNumbers
    public String[] export() {
        return new String[]{
                getNumber(),
                owner.getName(),
                owner.getNumber(),
                String.valueOf(!closed),
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