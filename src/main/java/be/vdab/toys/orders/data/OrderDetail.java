package be.vdab.toys.orders.data;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "orderdetails")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    private Order order;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Product product;
    private int ordered;
    private BigDecimal priceEach;
    @Version
    private int version;

    public OrderDetail(Order order, Product product, int ordered, BigDecimal priceEach) {
        this.id = 0;
        this.order = order;
        this.product = product;
        this.ordered = ordered;
        this.priceEach = priceEach;
    }

    protected OrderDetail() {}

    public long getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public Product getProduct() {
        return product;
    }

    public int getOrdered() {
        return ordered;
    }

    public BigDecimal getPriceEach() {
        return priceEach;
    }

    public BigDecimal calculateValue() {
        var ordered = BigDecimal.valueOf(getOrdered());
        return getPriceEach().multiply(ordered);
    }

    public void updateProductForShipment() {
        product.updateOrder(ordered);
        product.updateStock(ordered);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetail that = (OrderDetail) o;
        return Objects.equals(order, that.order) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, product);
    }
}
