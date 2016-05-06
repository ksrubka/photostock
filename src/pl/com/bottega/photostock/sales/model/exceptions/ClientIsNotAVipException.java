package pl.com.bottega.photostock.sales.model.exceptions;

/**
 * Created by Beata Iłowiecka on 06.05.16.
 */
public class ClientIsNotAVipException extends Throwable {

    private String clientNumber;

    public ClientIsNotAVipException(String message, String number){
        super(message);
        this.clientNumber = number;
    }

    public String getNumber(){
        return clientNumber;
    }
}
