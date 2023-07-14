package carsharing.db;

public class Customer {
    private Integer id;
    private String name;
    private Car car;

    public Customer(String name) {
        this.id = null;
        this.name = name;
        this.car = null;
    }

    public Customer(int id, String name, Car car) {
        this.id = id;
        this.name = name;
        this.car = car;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Car getCar() {
        return car;
    }
}
