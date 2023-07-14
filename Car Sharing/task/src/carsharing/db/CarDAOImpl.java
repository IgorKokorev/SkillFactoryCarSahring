package carsharing.db;

import carsharing.Main;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDAOImpl implements CarDAO {
    private Class H2Driver;

    private String dbName;

    public CarDAOImpl(String dbName) throws ClassNotFoundException {
        this.dbName = "jdbc:h2:./src/carsharing/db/" + dbName;
        H2Driver = Class.forName("org.h2.Driver");
        createTable();
    }

    private boolean createTable() {
        try (Connection conn = DriverManager.getConnection(dbName)) {
            conn.setAutoCommit(true);
            Statement st = conn.createStatement();
            int answer = st.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS car (
                        id INT NOT NULL AUTO_INCREMENT,
                        name VARCHAR NOT NULL UNIQUE,
                        company_id INT NOT NULL,
                        available BOOLEAN NOT NULL,
                        PRIMARY KEY (id),
                        FOREIGN KEY (company_id) REFERENCES company(id)
                    );""");
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public List<Car> findAll() {
        List<Car> result = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(dbName)) {
            conn.setAutoCommit(true);
            Statement st = conn.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * FROM car\n" +
                    "ORDER BY id;");
            while (resultSet.next()) {

                int companyId = resultSet.getInt("company_id");
                Company company = Main.companyDAO.findById(companyId);

                result.add(new Car(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                company,
                                resultSet.getBoolean("available")
                        )
                );
            }
        } catch (SQLException e) {
            return result;
        }
        return result;
    }

    @Override
    public List<Car> findAllByCompany(Company company) {

        List<Car> result = new ArrayList<>();
        int companyId = company.getId();

        try (Connection conn = DriverManager.getConnection(dbName)) {
            conn.setAutoCommit(true);
            Statement st = conn.createStatement();
            ResultSet resultSet = st.executeQuery(
                    "SELECT * FROM car\n" +
                            "WHERE company_id = " + companyId + " AND available\n" +
                            "ORDER BY id;");
            while (resultSet.next()) {

                result.add(new Car(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                company,
                                resultSet.getBoolean("available")
                        )
                );
            }
        } catch (SQLException e) {
            return result;
        }
        return result;
    }

    @Override
    public boolean save(Car car) {
        try (Connection conn = DriverManager.getConnection(dbName)) {
            conn.setAutoCommit(true);
            Statement st = conn.createStatement();
            st.executeUpdate("INSERT INTO car (name, company_id, available)\n" +
                    "VALUES ('" + car.getName() + "', '" + car.getCompany().getId() + "', " + car.isAvailable() + ");");
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Car car) {
        return false;
    }

    @Override
    public Car findById(long id) {
        return null;
    }

    @Override
    public Car findByName(String name) {
        return null;
    }

    @Override
    public void updateAvailability(Car car) {
        try (Connection conn = DriverManager.getConnection(dbName)) {
            conn.setAutoCommit(true);
            Statement st = conn.createStatement();
            st.executeUpdate(
                    "UPDATE car " +
                            "SET available = " + car.isAvailable() + "\n" +
                            "WHERE id = " + car.getId() + ";");
        } catch (SQLException e) {
            return;
        }
    }
}
