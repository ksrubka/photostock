package pl.com.bottega.photostock.sales.model.exceptions;

/**
 * Created by Beata Iłowiecka on 16.04.16.
 */
public class ProductNotAvailableException extends RuntimeException {

    // powinien nieść info o błędzie
    // komunikat - opis błędu
    // nr i typ obiektu biznesowego

    private String number;
    private Class clazz;

    public ProductNotAvailableException(String message, String number, Class clazz){
        super(message);
        this.number = number;
        this.clazz = clazz;
    }

    public ProductNotAvailableException(String message, String number){
        super(message);
        this.number = number;
    }

    /*public Class getClazz(){
        return clazz;
    }*/

    public String getNumber() {
        return number;
    }
}
