package be.vdab.toys.orders.data;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String scale;
    private String description;
    private int inStock;
    private int inOrder;
    private BigDecimal price;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "productlineId")
    private ProductLine productLine;
    @Version
    private short version;
}
