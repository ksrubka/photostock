package pl.com.bottega.photostock.sales.model;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import pl.com.bottega.photostock.sales.model.exceptions.DataDoesNotExistException;
import pl.com.bottega.photostock.sales.model.exceptions.ProductNotAvailableException;

import java.util.*;

/**
 * Created by Beata Iłowiecka on 12.03.2016.
 */
public class Reservation {

    private Client owner;
    private List<Product> items = new LinkedList<>();
    private String number;
    private boolean closed;

    public Reservation(Client owner) {
        this.owner = owner;
        this.closed = false;
    }

    public void add(Product product) throws ProductNotAvailableException {
        if (!product.canBeReservedBy(owner))
            throw new ProductNotAvailableException("Produkt " + product.getNumber() +
                    " jest niedostępny dla klienta " + owner.getNumber(), product.getNumber());
        if (items.contains(product))
            throw new ProductNotAvailableException("Produkt " + product.getNumber() +
                    " jest już zarezerwowany przez klienta " + owner.getName(), product.getNumber());
        items.add(product);
    }

    public void remove(Product product) throws DataDoesNotExistException{
        boolean removed = items.remove(product);
        if (!removed)
            throw new DataDoesNotExistException("Nie ma takiego produktu w rezerwacji: " + product.getNumber(),
                    product.getNumber(), product.getClass());
    }

    public Offer generateOffer() {
        List<Product> result = Lists.newLinkedList(
                Iterables.filter(
                        items, new Predicate<Product>() {
                            @Override
                            public boolean apply(Product product) {
                                return product.canBeReservedBy(owner);
                            }
        }));
        Comparator<Product> comparator = new PriceAndNameProductComparator();
        Collections.sort(result, comparator);
        return new Offer(owner, result);
    }

    public int getItemsCount() {
        return items.size();
    }

    public Client getOwner() {
        return owner;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isClosed() {
        return closed;
    }

    public void close() {
        this.closed = true;
    }

    public boolean checkWhetherReservationContainsProduct(Product productToCheck){
        for (Product product : items){
            if (product.equals(productToCheck))
                return true;
        }
        return false;
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

    public boolean isActive() {
        return !closed;
    }

    public String[] export() {
        return new String[]{
                getNumber(),
                owner.getName(),
                owner.getNumber(),
                String.valueOf(!closed),
                getProductsNumbers()
        };
    }
}
