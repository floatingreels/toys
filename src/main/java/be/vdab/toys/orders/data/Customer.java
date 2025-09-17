package be.vdab.toys.orders.data;

import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String streetAndNumber;
    private String city;
    private String state;
    private String postalCode;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "countryId")
    private Country country;
    @Version
    private short version;
}

