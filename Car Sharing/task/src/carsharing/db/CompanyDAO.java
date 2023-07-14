package carsharing.db;

import java.util.List;

public interface CompanyDAO {
    public List<Company> findAll();
    public boolean save(Company company);
    public boolean delete(Company company);
    public Company findById(long id);
    public Company findByName(String name);
}
