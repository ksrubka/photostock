package pl.com.bottega.photostock.sales.application;

import pl.com.bottega.photostock.sales.model.*;
import pl.com.bottega.photostock.sales.model.products.Picture;

/**
 * Created by Beata Iłowiecka on 13.03.2016.
 */
public class ConsoleApp2 {

    public static void main(String[] args) {

        /*Client panJanusz = new Client("Janusz", "Księżycowa", 4);
        panJanusz.recharge(10);

        panJanusz.charge(12, "for the pictures");

        if (panJanusz.canAfford(12)){
            panJanusz.charge(12, "za coś");
        } else {
            System.out.println("can not afford");
        }*/



        //=================================================  TWO =======================================================
        //=========================================== CUSTOMERS AT A TIME ==============================================
        //================================================  ONE PICTURE ================================================


        //***************************************** 1st CASE **********************************************************

        //++++++ PICTURE ++++++++
        String[] var1 = {"tree", "grass", "big"};
        Picture pic1 = new Picture("01", 1, var1, true);

        //====== 1st client ======
        //paniZdzisia
        Client paniZdzisia = new Client("paniZdzisia", "kasandry", ClientStatus.GOLD, 40, true);
        LightBox lightBox1 = new LightBox(paniZdzisia);
        lightBox1.add(pic1);

        //====== 2nd client ======
        //panJanusz
        Client panJanusz = new Client("Janusz", "Księżycowa", 4);
        LightBox lightBox2 = new LightBox(panJanusz);
        lightBox2.add(pic1);
        //++++++++++ dodali to samo zdjęcie ++++++++++

        // ... ale paniZdzisia pierwsza zarezerwowała
        Reservation reservation1 = new Reservation(paniZdzisia);
        reservation1.generateOffer();

        // panJanusz się niestety spóźnił
        Reservation reservation2 = new Reservation(panJanusz);
        reservation2.generateOffer();

        // oferta nie powinna się wygenerować dla niego, ani rezerwacja zrobić
        // bo paniZdzisia była pierwsza z jej rezerwacją

        // ogólnie rezerwację można wygenerować konstruktorem ale pustą,lub z LightBoxa ze zdjęciami
        // nie można do niej dodać zarezerwowanego zdęcia

        System.out.println("\n\n");
        //******************************************* 2nd CASE ********************************************************

        // pic2 jest niedostępne
        //++++++ PICTURE ++++++++
        String[] var2 = {"tree", "dog", "big", "oak"};
        Picture pic2 = new Picture("02", 1, var2, false);

        //====== 1st client ======
        Client panAntek = new Client("panAntek", "Mars", ClientStatus.GOLD, 200, true);
        LightBox lightBox3 = new LightBox(panAntek);
        lightBox3.add(pic2);

        //====== 2nd client ======
        Client paniHeleka = new Client("paniHeleka", "Ziemia", ClientStatus.GOLD, 200, true);
        LightBox lightBox4 = new LightBox(paniHeleka);
        lightBox4.add(pic2);

        // testy metody charge
        paniHeleka.charge(100, "za coś tam");
        paniHeleka.charge(101, "");

        panAntek.charge(200, "za zdjęcia");
        panAntek.charge(1, "yhm");


        // +++++++++++++++++ oboje próbowali dodać niedostępne zdjęcie +++++++++++++++++
        // paniHelenka nawet próbowała ponownie
        // to nie powinno się udać

        System.out.println("\n\n");
        //*********************************************** 3rd CASE ***************************************************

        //++++++ PICTURE ++++++++
        String[] var3 = {"lines", "stripes", "green", "oak"};
        Picture pic3 = new Picture("03", 7, var3, true);

        //====== 1st client ======
        Client paniKazia = new Client("paniKazia", "Ziemia", ClientStatus.GOLD, 200, true);
        LightBox lightBox5 = new LightBox(paniKazia);
        lightBox5.add(pic3);

        //====== 2nd client ======
        Client panStach = new Client("panStach", "Ziemia", ClientStatus.GOLD, 200, true);
        LightBox lightBox6 = new LightBox(panStach);
        lightBox6.add(pic3);


        System.out.println("\n\n");


        //=================================================  THREE =====================================================
        //=========================================== CUSTOMERS AT A TIME ==============================================
        //============================================   Two PICTUREs   ================================================


        //************************************************ 1st CASE ************************************************

        // zdjęcie z pustą tabelą tagów nie powinno przejść
        //

        //++++++ PICTUREs ++++++++
        String[] var4 = {};
        Picture pic4 = new Picture("04", 1, var4, true);

        String[] var5 = {"wall", "call", "phone"};
        Picture pic5 = new Picture("05", 1, var5, true);

        //====== 1st client ======
        Client panFranek = new Client("panFranek", "Ziemia", 1);
        LightBox lightBox7 = new LightBox(panFranek);
        lightBox7.add(pic5);

        //====== 2nd client ======
        Client panKarol = new Client("panKarol", "krańcowa",ClientStatus.GOLD, 1, true);
        LightBox lightBox8 = new LightBox(panKarol);
        lightBox8.add(pic4);
        lightBox8.add(pic5);

        //====== 3rd client ======
        Client paniAsia = new Client("paniAsia", "kaczeńcowa",ClientStatus.GOLD, 5, true);
        LightBox lightBox9 = new LightBox(paniAsia);
        lightBox9.add(pic5);

        //************************************************** 2nd CASE **************************************************

        //jedno zdjęcie kosztuje 10tys creditów więc nie powinno być nikogo na nie stać
        // drugie zdjęcie paradosalnie może dodać zamiast odejmować credity

        //++++++ PICTURE ++++++++
        String[] var7 = {"mice", "grass", "small"};
        Picture pic7 = new Picture("07", 10000, var7, true);

        String[] var6 = {"rover", "road", "big"};
        Picture pic6 = new Picture("06", -90, var6, true);

        //====== 1st client ======
        Client paniEla = new Client("paniEla", "Saturn",ClientStatus.GOLD, 5, true);
        LightBox lightBox10 = new LightBox(paniEla);
        lightBox10.add(pic7);

        //====== 2nd client ======
        Client paniAniela = new Client("paniAniela", "Ziemia",ClientStatus.GOLD, 5, true);
        LightBox lightBox11 = new LightBox(paniAniela);
        lightBox11.add(pic7);
        lightBox11.add(pic6);

        //====== 3rd client ======
        Client panLeszek = new Client("panLeszek", "Ziemia",ClientStatus.GOLD, 5, true);
        LightBox lightBox12 = new LightBox(panLeszek);
        lightBox12.add(pic6);

        //************************************************ 3rd CASE ************************************************

        //++++++ PICTURE ++++++++
        String[] var8 = {"cloud", "elephant", "nice"};
        Picture pic8 = new Picture("08", 1, var8, true);

        String[] var9 = {"baloon", "card", "sky"};
        Picture pic9 = new Picture("09", 1, var9, true);

        //====== 1st client ======
        Client paniGosia = new Client("paniGosia", "Ziemia",ClientStatus.GOLD, 5, true);
        LightBox lightBox13 = new LightBox(paniGosia);
        lightBox13.add(pic8);

        //====== 2nd client ======
        Client panKuba = new Client("panKuba", "Ziemia",ClientStatus.GOLD, 5, true);
        LightBox lightBox14 = new LightBox(panKuba);
        lightBox14.add(pic8);
        lightBox14.remove(pic8);

        //====== 3rd client ======
        Client panJan = new Client("panJan", "Ziemia",ClientStatus.GOLD, 5, true);
        LightBox lightBox15 = new LightBox(panJan);
        lightBox15.add(pic8);
        lightBox14.add(pic9);
    }

}
