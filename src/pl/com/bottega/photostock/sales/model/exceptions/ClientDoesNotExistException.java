package pl.com.bottega.photostock.sales.model.exceptions;

/**
 * Created by Beata Iłowiecka on 29.04.16.
 */
public class ClientDoesNotExistException extends RuntimeException {

    private String name;

    public ClientDoesNotExistException(String message, String name){
        super(message);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
