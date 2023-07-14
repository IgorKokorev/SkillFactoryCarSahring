package carsharing;

import carsharing.db.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

import static carsharing.CustomerService.createCustomer;
import static carsharing.CustomerService.loginCustomer;
import static carsharing.ManagerService.loginManager;

public class Main {
    public static CompanyDAO companyDAO;
    public static CarDAO carDAO;
    public static CustomerDAO customerDAO;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String dbName = "carsharing";

        int index = Arrays.stream(args).toList().indexOf("-databaseFileName");
        if (index >= 0 && index != (args.length - 1)) {
            dbName = args[index + 1];
        }

        companyDAO = new CompanyDAOImpl(dbName);
        carDAO = new CarDAOImpl(dbName);
        customerDAO = new CustomerDAOImpl(dbName);

        startApp();
    }


    private static void startApp() {
        while (true) {
            System.out.println("\n1. Log in as a manager\n" +
                    "2. Log in as a customer\n" +
                    "3. Create a customer\n" +
                    "0. Exit");

            switch (Integer.parseInt(scanner.nextLine())) {
                case 0 -> {
                    return;
                }
                case 1 -> loginManager();
                case 2 -> loginCustomer();
                case 3 -> createCustomer();
                default -> System.out.println("Wrong input!");
            }
        }
    }
}