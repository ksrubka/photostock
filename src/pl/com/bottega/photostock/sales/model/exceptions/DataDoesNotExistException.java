package pl.com.bottega.photostock.sales.model.exceptions;

/**
 * Created by Beata Iłowiecka on 28.04.16.
 */
public class DataDoesNotExistException extends RuntimeException {

    private String number;
    private Class clazz;

    public DataDoesNotExistException(String message, String number, Class clazz){
        super(message);
        this.number = number;
        this.clazz = clazz;
    }

    public Class getClazz(){
        return clazz;
    }

    public String getNumber() {
        return number;
    }
}
