package pl.com.bottega.photostock.sales.application;

import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.LightBox;
import pl.com.bottega.photostock.sales.model.products.Picture;

/**
 * Created by Beata Iłowiecka on 29.03.16.
 */
public class UberLightBox {

    /* Łączy wiele LightBoxów
    nie zawiera zdjęć: nieaktywnych, duplikatów*/

    public static void main(String[] args) {

        //********************************************* Pictures *********************************************

        String[] var1 = {"tree", "grass", "big"};
        String[] var2 = {"tree", "dog", "big", "oak"};
        String[] var3 = {"lines", "stripes", "green", "oak"};
        String[] var4 = {};
        String[] var5 = {"wall", "call", "phone"};
        String[] var6 = {"rover", "road", "big"};
        String[] var7 = {"mice", "grass", "small"};
        String[] var8 = {"cloud", "elephant", "nice"};
        String[] var9 = {"baloon", "card", "sky"};

        Picture pic1 = new Picture("01", 1, var1, true);
        Picture pic2 = new Picture("02", 1, var2, false);
        Picture pic3 = new Picture("03", 7, var3, true);
        Picture pic4 = new Picture("04", 1, var4, true);
        Picture pic5 = new Picture("05", 1, var5, true);
        Picture pic6 = new Picture("06", -90, var6, true);
        Picture pic7 = new Picture("07", 10000, var7, true);
        Picture pic8 = new Picture("08", 1, var8, true);
        Picture pic9 = new Picture("09", 1, var9, false);

        //********************************************* LightBoxes ********************************************

        Client paniHelenka = new Client();

        LightBox lightBox1 = new LightBox(paniHelenka);
        LightBox lightBox2 = new LightBox(paniHelenka);
        LightBox lightBox3 = new LightBox(paniHelenka);
        LightBox lightBox4 = new LightBox(paniHelenka);

        lightBox1.add(pic1, pic2);
        lightBox2.add(pic3, pic4);
        lightBox3.add(pic5, pic6);
        lightBox4.add(pic7, pic8, pic9);

        LightBox uberLightBox = createUberLightBox(paniHelenka, lightBox1, lightBox2, lightBox3, lightBox4);
        uberLightBox.setName("UberLightBox");
        displayLightBoxPics(uberLightBox);
    }

    public static LightBox createUberLightBox(Client client, LightBox... lbxs) {
        LightBox newLightBox = new LightBox(client);

        for (LightBox lb : lbxs) {
            for (Picture pic : lb.getItems()) {
                if (pic.isAvailable()) {
                    newLightBox.add(pic);
                }
            }
        }
        return newLightBox;
    }

    public static void displayLightBoxPics(LightBox lb){
        System.out.println();
        System.out.println(lb.getName() + " | " + lb.getOwner().getName() +
                "\n==========================");
        System.out.println("nr | price" +
                "\n==========================");
        for (Picture p : lb.getItems()) {
            System.out.println(p.getNumber() + " | " + p.getPrice());
        }
    }
}