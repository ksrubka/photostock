package pl.com.bottega.photostock.sales.application;

import pl.com.bottega.photostock.sales.model.*;
import pl.com.bottega.photostock.sales.model.products.Clip;
import pl.com.bottega.photostock.sales.model.products.Picture;
import pl.com.bottega.photostock.sales.model.Product;
import pl.com.bottega.photostock.sales.model.products.Song;

/**
 * Created by Beata Iłowiecka on 08.04.16.
 */
public class ConsoleApp {

    public static void main(String[] args) {

       /* Picture programming = new Picture();
        Product goldOnTheCeiling = new Song();
        Product littleNumbers = new Clip();

        Client paniHelenka = new Client();
        LightBox lightBoxPaniHelenki = new LightBox(paniHelenka, "nr1");

        lightBoxPaniHelenki.add(programming, goldOnTheCeiling, littleNumbers);

        Reservation rezerwacjaPaniHelenki = new Reservation(paniHelenka, "nr1");

        // TODO sensowne byłoby overloadowanie kontruktora tak żeby można było tworzyć rezerwację z lightboxa, o tak:
        // public Reservation(LightBox lbx)){ // tu idzie kodzik przerzucający itemsy}
        rezerwacjaPaniHelenki.add(programming,  goldOnTheCeiling, littleNumbers);

        Offer ofertaPaniHelenki = rezerwacjaPaniHelenki.generateOffer();

        System.out.println("Pani Helenka ma w ofercie " + ofertaPaniHelenki.getItemsCount() + " produkty.");
        System.out.println();*/

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
        // ale wtedy cała aplikacja musi czekać pół godziny bez sensu. I tu chyba przydały by się wątki?
        // wracając do AbstractProduct - jeśli canBeBoughtByMany == true (do zmiany zamiast to enigmatyczne !shared) (zrobione!)
        // to nie bierzemy pod uwage tego hasOfferGenerated bo wtedy i tak można rezerwować

        /*ClientVIP paniKasia = new ClientVIP();
        Reservation rezerwacjaPaniKasi = new Reservation(paniKasia, "nr1");
        rezerwacjaPaniKasi.add(programming);

        System.out.print("Tyle pani Kasia ma produktów w rezerwacji: ");
        System.out.println(rezerwacjaPaniKasi.getItemsCount());*/
    }
}
