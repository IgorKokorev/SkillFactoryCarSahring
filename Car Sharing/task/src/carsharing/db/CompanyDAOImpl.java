package carsharing.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDAOImpl implements CompanyDAO {
    private Class H2Driver;

    private String dbName;

    public CompanyDAOImpl(String dbName) throws ClassNotFoundException {
        this.dbName = "jdbc:h2:./src/carsharing/db/" + dbName;
        H2Driver = Class.forName("org.h2.Driver");
        createTable();
    }

    private boolean createTable() {
        try (Connection conn = DriverManager.getConnection(dbName)) {
            conn.setAutoCommit(true);
            Statement st = conn.createStatement();
            int answer = st.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS company (
                        id INT NOT NULL AUTO_INCREMENT,
                        name VARCHAR NOT NULL UNIQUE,
                        PRIMARY KEY (id)
                    );""");
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public List<Company> findAll() {
        List<Company> result = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(dbName)) {
            conn.setAutoCommit(true);
            Statement st = conn.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * FROM company\n" +
                    "ORDER BY id;");
            while (resultSet.next()) {
                result.add(new Company(
                                resultSet.getInt("id"),
                                resultSet.getString("name")
                        )
                );
            }
        } catch (SQLException e) {
            return result;
        }
        return result;
    }

    @Override
    public boolean save(Company company) {

        try (Connection conn = DriverManager.getConnection(dbName)) {
            conn.setAutoCommit(true);
            Statement st = conn.createStatement();
            st.executeUpdate("INSERT INTO company (name)\n" +
                    "VALUES ('" + company.getName() + "');");
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Company company) {
        return false;
    }

    @Override
    public Company findById(long id) {
        return null;
    }

    @Override
    public Company findByName(String name) {
        return null;
    }
}
