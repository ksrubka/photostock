package pl.com.bottega.photostock.sales.infrastructure.repositories.fake_repositories;

import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.CompanyRepository;
import pl.com.bottega.photostock.sales.model.Company;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Beata Iłowiecka on 29.04.16.
 */
public class FakeCompanyRepository implements CompanyRepository {

    private static Map<String, Company> fakeDatabase = new HashMap<>();

    @Override
    public Company load(String number) {
        Company company = fakeDatabase.get(number);
        if  (company == null)
            throw new RuntimeException("Company " + number + " does not exist");
        return company;
    }

    @Override
    public void save(Company company) {
        if (company.getNumber() == null)
            company.setNumber(UUID.randomUUID().toString()); // symulacja generowania ID przez bazę danych
        fakeDatabase.put(company.getName(), company);
    }
}
