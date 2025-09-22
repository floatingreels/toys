package be.vdab.toys.orders.data;

import be.vdab.toys.orders.error.OrderInsufficientStockException;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

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

    public Product(String name, String scale, String description, int inStock, int inOrder, BigDecimal price, ProductLine productLine) {
        this.id = 0;
        this.name = name;
        this.scale = scale;
        this.description = description;
        this.inStock = inStock;
        this.inOrder = inOrder;
        this.price = price;
        this.productLine = productLine;
    }

    protected Product() {}

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getScale() {
        return scale;
    }

    public String getDescription() {
        return description;
    }

    public int getInStock() {
        return inStock;
    }

    public int getInOrder() {
        return inOrder;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductLine getProductLine() {
        return productLine;
    }

    public void updateOrder(int ordered) {
        if (ordered > inOrder) {
            throw new OrderInsufficientStockException();
        } else {
            inOrder -= ordered;
        }
    }

    public void updateStock(int ordered) {
        if (ordered > inStock) {
            throw new OrderInsufficientStockException();
        } else {
            inStock -= ordered;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) && Objects.equals(description, product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }
}
