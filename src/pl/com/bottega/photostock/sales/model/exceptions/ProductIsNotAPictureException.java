package pl.com.bottega.photostock.sales.model.exceptions;

/**
 * Created by Beata Iłowiecka on 29.04.16.
 */
public class ProductIsNotAPictureException extends RuntimeException {
    private String number;
    private Class clazz;

    public ProductIsNotAPictureException(String message, String number, Class clazz){
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
