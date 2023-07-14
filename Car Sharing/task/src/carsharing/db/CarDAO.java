package carsharing.db;

import java.util.List;

public interface CarDAO {
    public List<Car> findAll();
    public List<Car> findAllByCompany(Company company);
    public boolean save(Car car);
    public boolean delete(Car car);
    public Car findById(long id);
    public Car findByName(String name);
    public void updateAvailability(Car car);
}
