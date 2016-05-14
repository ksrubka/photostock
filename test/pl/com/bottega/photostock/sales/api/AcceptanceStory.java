package pl.com.bottega.photostock.sales.api;

import org.junit.Test;
import pl.com.bottega.photostock.sales.model.Money;
import pl.com.bottega.photostock.sales.model.Product;
import pl.com.bottega.photostock.sales.model.products.ProductType;

import java.util.List;

/**
 * Created by Beata Iłowiecka on 14.05.2016.
 */
public class AcceptanceStory {

    private static final String STANDARD_USER_NR = "nr1";
    private static final String AVAILABLE_PRODUCT_NR = "nr1";
        private static final int INITIAL_MONEY = 100;

    private PurchaseProcess purchaseProcess = new PurchaseProcess();
    private ProductsCatalog productsCatalog = new ProductsCatalog();
    private AdminPanel adminPanel = new AdminPanel();
    private ClientManagement clientManagement = new ClientManagement();
    private LightBoxManagement lightBoxManagement = new LightBoxManagement();

    @Test
    public void story() {
        adminPanel.addProduct("pro", "nr12", 10, new String[] {"tag1"}, ProductType.CLIP);
        String clientNr = clientManagement.register("Imię", "Nazwisko", "spoko", "nr1");
        clientManagement.recharge(clientNr, new Money(INITIAL_MONEY));
        List<Product> products = productsCatalog.find(null, null, null, null, true);

        for (Product product : products) {
            if (product.isAvailable())
                purchaseProcess.add(clientNr, product.getNumber());
        }
        //dodanie do lightboxa i przeniesienie
        purchaseProcess.calculateOffer(clientNr);
        purchaseProcess.confirm(clientNr);
    }
}