package pl.com.bottega.photostock.sales.model.exceptions;

/**
 * Created by Beata Iłowiecka on 22.05.16.
 */
public class DataAccessException extends RuntimeException {
    public DataAccessException(Exception e) {
        super(e);
    }
}