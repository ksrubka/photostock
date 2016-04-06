package pl.com.bottega.photostock.sales.application;

import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.LightBox;
import pl.com.bottega.photostock.sales.model.products.Picture;

/**
 * Created by Beata Iłowiecka on 19.03.2016.
 */
public class LightBoxTest {

    public static void main(String[] args) {

        Client panJanusz = new Client("panJanusz", "Ziemia", true, 5, 0, 200);

        LightBox lightBoxJanusza = new LightBox(panJanusz);

        String[] var1 = {"tree", "grass", "big"};
        String[] var2 = {"tree", "dog", "big", "oak"};


        Picture lumberjack = new Picture("01", 1, var1, true);
        Picture pic2 = new Picture("02", 1, var2, false);
        Picture pic3 = new Picture("02", 1, var2, false);



        //niedozwolona opercja
        try {
            lightBoxJanusza.add(lumberjack);
            lightBoxJanusza.close();//!!!!!
            lightBoxJanusza.add(pic2);
            lightBoxJanusza.add(pic3);
        }
        catch (IllegalArgumentException | IllegalStateException ex){
            System.out.println("Nie udało się, " + ex);
        }
        finally{
            System.out.println("Lub życie :)");
        }

        int count = lightBoxJanusza.getItemsCount();
        System.out.println("Ilość elementów w lightboxie: " + count);

    }
}