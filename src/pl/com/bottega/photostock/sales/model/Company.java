package pl.com.bottega.photostock.sales.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Beata Iłowiecka on 29.04.16.
 */
public class Company {

    private String name;
    private String number;
    private List<Client> employee = new ArrayList<>();


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        Company company = (Company) o;
        return number.equals(company.number);
    }
}