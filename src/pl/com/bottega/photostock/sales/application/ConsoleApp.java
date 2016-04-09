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

        Picture programming = new Picture();
        Picture kitty = new Picture("02", 10, new String[] {"cute", "pud", "mrau"});
        Picture forest = new Picture("03", 14, new String[] {"moss", "anemone", "bud", "tree"});

        Client paniHelenka = new Client();
        LightBox lightBoxPaniHelenki = new LightBox(paniHelenka);

        lightBoxPaniHelenki.add(programming, kitty, forest);

        Reservation rezerwacjaPaniHelenki = new Reservation(paniHelenka);

        // TODO sensowne byłoby overloadowanie kontruktora tak żeby można było tworzyć rezerwację z lightboxa, o tak:
        // public Reservation(LightBox lbx)){ // tu idzie kodzik przerzucający itemsy}
        rezerwacjaPaniHelenki.add(programming, kitty, forest);

        Offer ofertaPaniHelenki = rezerwacjaPaniHelenki.generateOffer();

        System.out.println("Pani Helenka ma w ofercie " + ofertaPaniHelenki.getItemsCount() + " produkty.");
        System.out.println();

        // Zaraz nastąpi próba rezerwacji zdjęcia przez drugiego klienta
        // zdjęcie jest niepodzielne (shared == false) więc nie powinno być można zarezerwować
        // ale paniKasia jest vipem więc może.
        // ale czy powinna móc po wygenerowniu oferty dla kogos innego? chyba nie.
        // TODO należy dodać zmiany w generateOffer() w klasie Reservation
        // np dodać pole boolean hasOfferGenerated w klasie AbstractProduct?
        // i jeśli true to nie rezerwujemy już dla nikogo
        // (ale to trzeba wyłączyć po wygaśnięciu oferty dla każdego produktu) niezależnie od tego czy tworzymy Purchase czy rezygnujemy
        // - ale czy jest sens to robić? może można lepiej, inaczej?
        // TODO no właśnie - jak wygasić ofertę? i kiedy? i gdzie? w konstruktorze Purchase?
        // i tu znowu powraca temat usuwania obiektów. Można by odliczyć np 30 min i potem dać kodzik który by usuwał obiekt
        // jak usunąć obiekt? może przypisać zmienną do nulla, a wtedy tamten obiekt straci referencję i GarbageCollector go usunie ?
        // ale tu
        // a jeśli canBeBoughtByMany (do zmiany zamiast to enigmatyczne !shared) (zrobione!) to nie bierzemy pod uwage tego hasOfferGenerated

        ClientVIP paniKasia = new ClientVIP();
        Reservation rezerwacjaPaniKasi = new Reservation(paniKasia);
        rezerwacjaPaniKasi.add(programming);

        System.out.print("tyle pani Kasia ma produktów w rezerwacji: ");
        System.out.println(rezerwacjaPaniKasi.getItemsCount());
    }
}
