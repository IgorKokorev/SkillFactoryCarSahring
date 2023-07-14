package carsharing;

import carsharing.db.Car;
import carsharing.db.Company;
import carsharing.db.Customer;

import java.util.List;

import static carsharing.Main.*;

public class CustomerService {
    static void loginCustomer() {
        List<Customer> customers = customerDAO.findAll();

        if (customers.isEmpty()) {
            System.out.println("\nThe customer list is empty!");
        } else {
            System.out.println("\nCustomer list:");
            for (int i = 1; i <= customers.size(); i++) {
                System.out.println("" + i + ". " + customers.get(i - 1).getName());
            }
            System.out.println("0. Back");

            int indx = Integer.parseInt(scanner.nextLine());
            if (indx == 0) return;
            if (indx <= 0 || indx > customers.size()) {
                System.out.println("Wrong input!");
            } else {
                customerMenu(customers.get(indx - 1));
            }
        }
    }

    private static void customerMenu(Customer customer) {
        while (true) {
            System.out.println("\n1. Rent a car\n" +
                    "2. Return a rented car\n" +
                    "3. My rented car\n" +
                    "0. Back");

            switch (Integer.parseInt(scanner.nextLine())) {
                case 0 -> {
                    return;
                }
                case 1 -> rentACar(customer);
                case 2 -> returnACar(customer);
                case 3 -> showRentedCar(customer);
                default -> System.out.println("Wrong input!");
            }
        }
    }

    private static void showRentedCar(Customer customer) {
        if (customer.getCar() == null) {
            System.out.println("You didn't rent a car!");
        } else {
            System.out.println("\nYour rented car:");
            System.out.println(customer.getCar().getName());
            System.out.println("Company:");
            System.out.println(customer.getCar().getCompany().getName());
        }
    }

    private static void returnACar(Customer customer) {
        if (customer.getCar() == null) {
            System.out.println("You didn't rent a car!");
        } else {
            customer.getCar().setAvailable(true);
            carDAO.updateAvailability(customer.getCar());
            customer.setCar(null);
            customerDAO.save(customer);
            System.out.println("\nYou've returned a rented car!");
        }
    }

    private static void rentACar(Customer customer) {
        if (customer.getCar() != null) {
            System.out.println("\nYou've already rented a car!");
            return;
        }
        List<Company> companies = companyDAO.findAll();
        if (companies.isEmpty()) {
            System.out.println("\nThe company list is empty!");
        } else {
            System.out.println("\nChoose a company:");
            for (int i = 1; i <= companies.size(); i++) {
                System.out.println("" + i + ". " + companies.get(i - 1).getName());
            }
            System.out.println("0. Back");
            int indx = Integer.parseInt(scanner.nextLine());
            if (indx == 0) return;
            if (indx <= 0 || indx > companies.size()) {
                System.out.println("Wrong input!");
            } else {
                companyMenu(customer, companies.get(indx - 1));
            }
        }
    }

    static void companyMenu(Customer customer, Company company) {
        List<Car> cars = carDAO.findAllByCompany(company);
        if (cars.isEmpty()) {
            System.out.println("\nNo available cars in the '" + company.getName() + "' company");
        } else {
            System.out.println("\nChoose a car:");
            for (int i = 1; i <= cars.size(); i++) {
                System.out.println("" + i + ". " + cars.get(i - 1).getName());
            }
            System.out.println("0. Back");
            int indx = Integer.parseInt(scanner.nextLine());
            if (indx == 0) return;
            if (indx <= 0 || indx > cars.size()) {
                System.out.println("Wrong input!");
            } else {
                customer.setCar(cars.get(indx - 1));
                customerDAO.save(customer);
                customer.getCar().setAvailable(false);
                carDAO.updateAvailability(customer.getCar());

                System.out.println("\nYou rented '" + customer.getCar().getName() + "'");
            }
        }
    }

    static void createCustomer() {
        System.out.println("\nEnter the customer name:");
        String name = scanner.nextLine();
        customerDAO.save(new Customer(name));
        System.out.println("The customer was added!");
    }
}
