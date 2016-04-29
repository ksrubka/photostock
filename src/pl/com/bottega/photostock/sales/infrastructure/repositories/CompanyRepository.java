package pl.com.bottega.photostock.sales.infrastructure.repositories;

import pl.com.bottega.photostock.sales.model.Company;

/**
 * Created by Beata Iłowiecka on 29.04.16.
 */
public interface CompanyRepository {

    Company load(String number);
    void save(Company company);
}
