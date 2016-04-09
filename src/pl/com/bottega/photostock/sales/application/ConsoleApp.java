package pl.com.bottega.photostock.sales.application;

import pl.com.bottega.photostock.sales.model.*;
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

        // próba rezerwacji zdjęcia przez drugiego klienta
        // zdjęcie jest niepodzielne (shared == false) więc nie powinno być można zarezerwować
        // ale paniKasia jest vipem więc może.
        // ale czy powinna móc po wygenerowniu oferty dla kogos innego? chyba nie.
        // TODO należy dodać zmiany w generateOffer() w klasie Reservation
        // np dodać pole boolean hasOfferGenerated w klasie AbstractProduct?
        // i jeśli true to nie rezerwujemy już dla nikogo
        // (ale to trzeba wyłączyć po wygaśnięciu oferty dla każdego produktu) - czy jest sens to robić? może można lepiej, inaczej?
        // TODO no właśnie - jak wygasić ofertę? i kiedy? i gdzie? w konstruktorze Purchase?
        // a jeśli canBeBoughtByMany (do zmiany zamiast to enigmatyczne !shared) to nie bierzemy pod uwage tego hasOfferGenerated
        // a tak wogóle to mi się nie podoba bo zmiana jednej klasy przez drugą, nie wiem czy nie za dużo mieszania?

        Client paniKasia = new Client("Kasia", "tajny", ClientStatus.VIP, 122, true);
        Reservation rezerwacjaPaniKasi = new Reservation(paniKasia);
        rezerwacjaPaniKasi.add(pic);

        System.out.println("tyle pani Kasia ma produktów w rezerwacji:");
        System.out.println(rezerwacjaPaniKasi.getItemsCount());
    }
}
