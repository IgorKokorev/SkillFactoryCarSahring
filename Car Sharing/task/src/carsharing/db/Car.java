package carsharing.db;

public class Car {
    private int id;
    private String name;
    private Company company;
    private boolean available;

    public Car(int id, String name, Company company, boolean available) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.available = available;
    }

    public Car(String name, Company company) {
        this.name = name;
        this.company = company;
        this.available = true;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Company getCompany() {
        return company;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isAvailable() {
        return available;
    }
}
