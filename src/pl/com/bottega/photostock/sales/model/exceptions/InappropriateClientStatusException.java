package pl.com.bottega.photostock.sales.model.exceptions;

/**
 * Created by Beata Iłowiecka on 06.05.16.
 */
public class InappropriateClientStatusException extends RuntimeException {

    private String clientNumber;

    public InappropriateClientStatusException(String message, String number){
        super(message);
        this.clientNumber = number;
    }

    public InappropriateClientStatusException(String message){
        super(message);
    }

    public String getNumber(){
        return clientNumber;
    }
}
