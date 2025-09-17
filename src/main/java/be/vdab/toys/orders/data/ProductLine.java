package be.vdab.toys.orders.data;

import jakarta.persistence.*;

@Entity
@Table(name = "productlines")
public class ProductLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    @Version
    private short version;
}
