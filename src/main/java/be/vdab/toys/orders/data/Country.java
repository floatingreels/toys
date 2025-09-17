package be.vdab.toys.orders.data;

import jakarta.persistence.*;

@Entity
@Table(name = "countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Version
    private short version;

    public Country(String name) {
        this.id = 0;
        this.name = name;
    }

    protected  Country() {}

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
