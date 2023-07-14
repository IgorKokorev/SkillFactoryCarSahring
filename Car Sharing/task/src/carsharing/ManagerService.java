package carsharing;

import carsharing.db.Car;
import carsharing.db.Company;

import java.util.List;

public class ManagerService {
    static void loginManager() {
        while (true) {
            System.out.println("\n1. Company list\n" +
                    "2. Create a company\n" +
                    "0. Back");
            switch (Integer.parseInt(Main.scanner.nextLine())) {
                case 0 -> {
                    return;
                }
                case 1 -> companyList();
                case 2 -> createCompany();
                default -> System.out.println("Wrong input!");
            }
        }
    }

    private static void createCompany() {
        System.out.println("\nEnter the company name:");
        String name = Main.scanner.nextLine();
        Company company = new Company(name);
        Main.companyDAO.save(company);
        System.out.println("The company was created!");
    }

    private static void companyList() {
        List<Company> companies = Main.companyDAO.findAll();
        if (companies.isEmpty()) {
            System.out.println("\nThe company list is empty!");
        } else {
            System.out.println("\nChoose the company:");
            for (int i = 1; i <= companies.size(); i++) {
                System.out.println("" + i + ". " + companies.get(i - 1).getName());
            }
            System.out.println("0. Back");
            int indx = Integer.parseInt(Main.scanner.nextLine());
            if (indx == 0) return;
            if (indx <= 0 || indx > companies.size()) {
                System.out.println("Wrong input!");
            } else {
                companyMenu(companies.get(indx - 1));
            }
        }
    }

    private static void companyMenu(Company company) {
        System.out.println("\n'" + company.getName() + "' company:");
        while (true) {
            System.out.println("\n1. Car list\n" +
                    "2. Create a car\n" +
                    "0. Back");

            switch (Integer.parseInt(Main.scanner.nextLine())) {
                case 0 -> {
                    return;
                }
                case 1 -> carList(company);
                case 2 -> createCar(company);
                default -> System.out.println("Wrong input!");
            }
        }
    }

    private static void createCar(Company company) {
        System.out.println("\nEnter the car name:");
        String name = Main.scanner.nextLine();
        Car car = new Car(name, company);
        Main.carDAO.save(car);
        System.out.println("The car was added!");
    }

    private static void carList(Company company) {
        List<Car> cars = Main.carDAO.findAllByCompany(company);
        if (cars.isEmpty()) {
            System.out.println("\nThe car list is empty!");
        } else {
            System.out.println("\nCar list:");
            for (int i = 1; i <= cars.size(); i++) {
                System.out.println("" + i + ". " + cars.get(i - 1).getName());
            }
        }
    }
}
