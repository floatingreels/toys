package be.vdab.toys.orders.data;

import jakarta.persistence.*;

import java.math.BigDecimal;

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
}
