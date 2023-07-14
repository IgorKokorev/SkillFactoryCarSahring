package carsharing.db;

import java.util.List;

public interface CustomerDAO {
    public List<Customer> findAll();
    public boolean save(Customer customer);
    public boolean delete(Customer customer);
    public Customer findById(long id);
    public Customer findByName(String name);
}
