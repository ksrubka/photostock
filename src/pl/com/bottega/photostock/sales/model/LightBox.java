package pl.com.bottega.photostock.sales.model;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Beata Iłowiecka on 12.03.2016.
 */
public class LightBox {

    private String name;
    private Client owner;
    private List<Picture> items = new ArrayList<>();
    //private ArrayList<Picture> items = new ArrayList<>();
    private boolean closed;

    public LightBox(Client owner) {
        this.owner = owner;
    }

    public void close() {
        this.closed = true;
    }

    /* public void add(Picture pictureToAdd) throws IllegalStateException , IllegalArgumentException {
        boolean added = false;
        int cursor = 0;
        for (Picture p : items){
            if (p != null){
                String nr1 = p.getNumber();
                String nr2 = pictureToAdd.getNumber();

                if (nr1.equals(nr2)) {
                    throw new IllegalArgumentException("LightBox already contains picture " + nr2);
                }
            }
            else {
                // p = pictureToAdd; // p jest innym miejscem niż kratka tablicy!
                items[cursor] = pictureToAdd;
                added = true;
                break;
                }
            }
            if (!added){
            new IllegalStateException("LightBox przepełniony!");
        }*/

        /*int cursor = 0;

        while (cursor < items.length){
            Picture pic = items[cursor];
            if (pic != null){
                String nr1 = pic.getNumber();
                String nr2 = pictureToAdd.getNumber();

                if (nr1.equals(nr2)){
                     throw new IllegalArgumentException("LightBox already contains picture " + nr2);
                }
            }
            cursor++;
        }*/

        /*boolean added = false;

        for (int i = 0; i < items.length; i++){
            Picture reference = items[i];
            if (reference == null){
              items[i] = pictureToAdd;
                added = true;
                break;
            }
        }
        if (!added){
            new IllegalStateException("LightBox przepełniony!");
        }
    }*/

    public void add(Picture... pictures)throws IllegalStateException, IllegalArgumentException {

        validate();

        for (Picture p : pictures) {
            if (closed) {
                throw new IllegalStateException("Closed!");
            }
            if (items.contains(p)) {
                throw new IllegalArgumentException("already contains");
            }
            items.add(p);
        }
    }

    public void remove(Picture pictureToRemove) throws IllegalArgumentException {

        validate();

        boolean removed = items.remove(pictureToRemove);

        if (!removed) {
            throw new IllegalArgumentException("Nie ma takiego zdjęcia");
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

    public List<Picture> getItems() {
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
            throw new IllegalStateException("Closed!");
        }
        if (!owner.isActive())
                throw new IllegalStateException("Użytkownik jest nieaktywny");
    }
}