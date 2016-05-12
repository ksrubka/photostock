package pl.com.bottega.photostock.sales.model;

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
        List<Product> result = new ArrayList<>();
        for (Product product : items){
            if (product.canBeReservedBy(owner))
                result.add(product);
        }
        Comparator<Product> comparator = new PriceAndNameProductComparator();
        Collections.sort(result, comparator);
        Offer offer = new Offer(owner, result);
        for (Product product : offer.getItems())
            System.out.println("Nr produktu: " + product.getNumber() + ", cena: " + product.getPrice());
        return offer;
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
}
