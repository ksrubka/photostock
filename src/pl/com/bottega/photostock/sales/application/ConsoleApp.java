package pl.com.bottega.photostock.sales.application;

import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.LightBox;
import pl.com.bottega.photostock.sales.model.Offer;
import pl.com.bottega.photostock.sales.model.Reservation;
import pl.com.bottega.photostock.sales.model.products.Picture;
import pl.com.bottega.photostock.sales.model.products.Product;

import java.util.ArrayList;

/**
 * Created by Beata Iłowiecka on 08.04.16.
 */
public class ConsoleApp {

    public static void main(String[] args) {

        Picture pic = new Picture();

        Client paniHelenka = new Client();
        LightBox lightBoxPaniHelenki = new LightBox(paniHelenka);

        lightBoxPaniHelenki.add(pic);

        Reservation rezerwacjaPaniHelenki = new Reservation(paniHelenka);

        rezerwacjaPaniHelenki.add(pic);

        Offer ofertaPaniHelenki = rezerwacjaPaniHelenki.generateOffer();

    }
}
