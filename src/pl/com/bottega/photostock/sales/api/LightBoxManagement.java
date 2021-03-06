package pl.com.bottega.photostock.sales.api;

import com.google.common.base.Preconditions;
import pl.com.bottega.photostock.sales.infrastructure.repositories.fake.FakeClientRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.fake.FakeLightBoxRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.fake.FakeProductRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.fake.FakeReservationRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ClientRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.LightBoxRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ProductRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ReservationRepository;
import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.Reservation;
import pl.com.bottega.photostock.sales.model.exceptions.ClientDoesNotExistException;
import pl.com.bottega.photostock.sales.model.LightBox;
import pl.com.bottega.photostock.sales.model.Product;
import pl.com.bottega.photostock.sales.model.products.Picture;

import java.io.IOException;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Beata Iłowiecka on 23.04.2016.
 */
public class LightBoxManagement {

    private ClientRepository clientRepository = new FakeClientRepository();
    private ReservationRepository reservationRepository = new FakeReservationRepository();
    private ProductRepository productRepository = new FakeProductRepository();
    private LightBoxRepository lightBoxRepository = new FakeLightBoxRepository();

    public String create(String clientNr) throws ClientDoesNotExistException {
        try {
            Client client = clientRepository.load(clientNr);
            LightBox lightBox = new LightBox(client);
            lightBoxRepository.save(lightBox);
            return lightBox.getNumber();
        }
        catch(ClientDoesNotExistException ex) {
            throw new ClientDoesNotExistException("Nie mogę odnaleźć klienta w bazie: ", ex.getNumber());
        }
    }

    public void add(String lightBoxNr, String productNr) throws IllegalArgumentException, IOException {
        LightBox lightBox = lightBoxRepository.load(lightBoxNr);
        Product product = productRepository.load(productNr);
        Preconditions.checkState(product instanceof Picture, "%s nie jest zdjęciem, nie można dodać do LightBoxa.", productNr);
        lightBox.add((Picture) product);
        lightBoxRepository.save(lightBox);
        productRepository.save(product);
    }

    public void share(String lightBoxNr, String clientNr) throws IllegalArgumentException {
        LightBox lbx = lightBoxRepository.load(lightBoxNr);
        Client lbxOwner = lbx.getOwner();
        Client client = clientRepository.load(clientNr);
        if (lbxOwner.getCompany().equals(client.getCompany())){
            lbx.addOwner(client);
            lightBoxRepository.save(lbx);
        }
        else
            throw new IllegalArgumentException("Klient z którym chcesz dzielić LightBox nie należy do tej samej firmy");
    }

    public void reserveAll(String lbxNr, String reservationNr, List<String> pictureNrs) throws IOException {
        LightBox lbx = lightBoxRepository.load(lbxNr);
        Reservation reservation = reservationRepository.load(reservationNr);
        for (String pictureNr : pictureNrs) {
            for (Product product : lbx.getItems()) {
                if (product instanceof Picture && product.getNumber().equals(pictureNr))
                    reservation.add(productRepository.load(pictureNr));
            }
        } //TODO dodaj wyjątki
    }

    public void reserve(String lbxNr, String pictureNr) {
        LightBox lbx = lightBoxRepository.load(lbxNr);
        String clientNr = lbx.getOwner().getNumber();
        //guava
        Picture picture = (Picture) lbx.getItems().stream().filter(picture1 -> picture1.getNumber().equals(pictureNr)).findFirst().get();
        checkNotNull(picture, "%s does not contain %s", lbxNr, pictureNr);
        lightBoxRepository.save(lbx);
    }

    public String clone(String lbxNr){
        LightBox lbx1 = lightBoxRepository.load(lbxNr);
        LightBox lbx2 = new LightBox(lbx1.getOwner());
        for (Product product : lbx1.getItems())
            lbx2.add(product);
        lightBoxRepository.save(lbx2);
        return lbx2.getNumber();
    } //TODO dodaj wyjątki
}