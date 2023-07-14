package carsharing.db;

import carsharing.Main;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    private String dbName;

    public CustomerDAOImpl(String dbName) throws ClassNotFoundException {
        this.dbName = "jdbc:h2:./src/carsharing/db/" + dbName;
//        H2Driver = Class.forName("org.h2.Driver");
        createTable();
    }

    private boolean createTable() {
        try (Connection conn = DriverManager.getConnection(dbName)) {
            conn.setAutoCommit(true);
            Statement st = conn.createStatement();
            int answer = st.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS customer (
                        id INT NOT NULL AUTO_INCREMENT,
                        name VARCHAR NOT NULL UNIQUE,
                        rented_car_id INT,
                        PRIMARY KEY (id),
                        FOREIGN KEY (rented_car_id) REFERENCES car(id)
                    );""");
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> result = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(dbName)) {
            conn.setAutoCommit(true);
            Statement st = conn.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * FROM customer\n" +
                    "ORDER BY id;");
            while (resultSet.next()) {

                int carId = resultSet.getInt("rented_car_id");
                Car car = carId == 0 ? null : Main.carDAO.findById(carId);

                result.add(new Customer(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                car
                        )
                );
            }
        } catch (SQLException e) {
            return result;
        }
        return result;
    }

    @Override
    public boolean save(Customer customer) {
        try (Connection conn = DriverManager.getConnection(dbName)) {
            conn.setAutoCommit(true);
            Statement st = conn.createStatement();

            if (customer.getId() == null) {
                st.executeUpdate(
                        "INSERT INTO customer (name)\n" +
                                "VALUES ('" + customer.getName() + "');");
            } else if (customer.getCar() != null) {
                st.executeUpdate(
                        "UPDATE customer\n" +
                                "SET rented_car_id = " + customer.getCar().getId() + "\n" +
                                "WHERE id = " + customer.getId() + ";");
            } else {
                st.executeUpdate(
                        "UPDATE customer\n" +
                                "SET rented_car_id = NULL\n" +
                                "WHERE id = " + customer.getId() + ";");
            }
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Customer customer) {
        return false;
    }

    @Override
    public Customer findById(long id) {
        return null;
    }

    @Override
    public Customer findByName(String name) {
        return null;
    }
}
