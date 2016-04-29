package pl.com.bottega.photostock.sales.model.exceptions;

/**
 * Created by Beata Iłowiecka on 29.04.16.
 */
public class ClientDoesNotExistException extends RuntimeException {

    private String number;

    public ClientDoesNotExistException(String message, String number){
        super(message);
        this.number = number;
    }

    public String getNumber() {
        return number;
    }
}
