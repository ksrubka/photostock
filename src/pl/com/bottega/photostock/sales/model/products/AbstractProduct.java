package pl.com.bottega.photostock.sales.model.products;

import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.Money;
import pl.com.bottega.photostock.sales.model.Product;
import pl.com.bottega.photostock.sales.model.exceptions.ProductNotAvailableException;

import java.util.ArrayList;

/**
 * Created by Beata Iłowiecka on 06.04.16.
 */
public abstract class AbstractProduct implements Product {

    protected String name;

    protected String number;

    protected String[] tags;

    protected Money price;
    protected boolean active;
    protected ArrayList<Client> reservedPer = new ArrayList<>();
    protected ArrayList<Client> soldPer = new ArrayList<>();;
    protected boolean shared;

    public AbstractProduct(String name, String number, double price, String[] tags, boolean active) {
        this.name = name;
        this.number = number;
        this.price = new Money(price);
        this.tags = tags;
        this.active = active;
    }

    public  AbstractProduct(String name, String number, double price, String[] tags){
        this(name, number, price, tags, true);
    }

    public boolean isAvailable(){
        if (!active)
            throw new ProductNotAvailableException("Produkt jest niedostępny ", number, this.getClass());
        return active;
    }

    public void setShared(boolean bool){
        shared = bool;
    }

    public double calculatePrice() {
        return 0;
        //TODO do implementacji gdy będą dostępne rabaty i rozdzielczości i cena będzie zależeć od nich.
    }

    public Money getPrice(){
        return price;
    }

    public String getNumber(){
        return number;
    }

    //make inactive
    public void cancel(){
        active = false;
    }

    public void reservePer(Client client) throws IllegalArgumentException {
        if (!canBeReservedBy(client))
            throw new IllegalArgumentException("Nie można zarezerwować.");
        else
            reservedPer.add(client);
    }

    public void unreservePer(Client client) throws IllegalArgumentException {
        boolean removed = reservedPer.remove(client);
        if (!removed)
            throw new IllegalArgumentException("Klient " + client.getName() +" nie rezerwował tego produktu");
    }

    public boolean canBeReservedBy(Client client){
        return client.canAfford(price) && isAvailable() && (!isSoldOut()) && (!isReservedByVip());
        //todo w przypadku rozbudowy o różne modele rezerwacji w zależności od statusu klienta zastosować
        //dodatkowo client.canReserve(picture), i w tej metodzie w klasie Client odwołać się do metody
        //w klasie typu ApprovingStrategy i tam będą implementacje dla każdego z poszczególnych statusów
        //np.GoldApprovingStrategy klient gold nie może zarezerwować jeśli zarezerwował klient platinum
    }

    public boolean isSoldOut() throws IllegalStateException {
        if (!canBeBoughtByMany() && isSold())
            throw new IllegalStateException("Produkt został już sprzedany.");
        else
            return false;
    }

    private boolean canBeBoughtByMany(){
        return shared;
    }

    private boolean isSold() {
        return !soldPer.isEmpty();
    }

    private boolean isReservedByVip() throws IllegalStateException {
        for (Client client : reservedPer){
            if (client.isVip())
                return true;
        }
        throw new IllegalStateException("Zdjęcie jest zarezerwowane przez klienta o statusie VIP.");
    }

    public void sellPer(Client client) throws IllegalStateException {
        if (canBeReservedBy(client))
            soldPer.add(client);
        else
            throw new IllegalStateException("Nie można sprzedać.");
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String[] getTags() {
        return tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractProduct))
            return false;
        AbstractProduct product = (AbstractProduct) o;
        return number.equals(product.number);
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }
}
