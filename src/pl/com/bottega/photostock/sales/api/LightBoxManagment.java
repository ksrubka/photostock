package pl.com.bottega.photostock.sales.api;

import pl.com.bottega.photostock.sales.infrastructure.repositories.*;
import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.exceptions.ClientDoesNotExistException;
import pl.com.bottega.photostock.sales.model.LightBox;
import pl.com.bottega.photostock.sales.model.Product;
import pl.com.bottega.photostock.sales.model.exceptions.DataDoesNotExistException;
import pl.com.bottega.photostock.sales.model.products.Picture;

/**
 * Created by Beata Iłowiecka on 23.04.2016.
 */
public class LightBoxManagment {

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

    public void add(String lightBoxNr, String productNr) throws IllegalArgumentException{
        LightBox lightBox = lightBoxRepository.load(lightBoxNr);
        Product product = productRepository.load(productNr);
        if (product instanceof Picture) {
            lightBox.add((Picture) product);
            lightBoxRepository.save(lightBox);
            productRepository.save(product);
        }
        else {
            throw new IllegalArgumentException("Produkt nie jest zdjęciem, nie można dodać do LightBoxa.");
        }
    }

    public void share(String lightBoxNr, String clientNr){
        LightBox lbx = lightBoxRepository.load(lightBoxNr);
        Client lbxOwner = lbx.getOwner();
        Client client = clientRepository.load(clientNr);
        if (lbxOwner.getCompany().equals(client.getCompany())){
            lbx.addOwner(client);
            lightBoxRepository.save(lbx);
        }
        else
            throw new IllegalStateException("Klient z którym chcesz dzielić LightBox nie należy do tej samej firmy");
    }

    public void reserve(String lbxNr, String reservationNr, String ...pictureNrs){

    }
}
