package be.vdab.toys.orders.data;

import be.vdab.toys.orders.error.OrderAlreadyShippedException;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate ordered;
    private LocalDate required;
    private LocalDate shipped;
    private String comments;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId")
    private Customer customer;
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToMany(mappedBy = "order")
    private Set<OrderDetail> orderDetails;
    @Version
    private short version;

    public Order(LocalDate ordered, LocalDate required, LocalDate shipped, String comments, Customer customer, Status status) {
        this.id = 0;
        this.ordered = ordered;
        this.required = required;
        this.shipped = shipped;
        this.comments = comments;
        this.customer = customer;
        this.status = status;
        this.orderDetails = new LinkedHashSet<>();
    }

    protected Order() {
    }

    public Long getId() {
        return id;
    }

    public LocalDate getOrdered() {
        return ordered;
    }

    public LocalDate getRequired() {
        return required;
    }

    public LocalDate getShipped() {
        return shipped;
    }

    public String getComments() {
        return comments;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Status getStatus() {
        return status;
    }

    public Set<OrderDetail> getOrderDetails() {
        return Collections.unmodifiableSet(orderDetails);
    }

    public BigDecimal calculateTotal() {
        return getOrderDetails()
                .stream()
                .map(OrderDetail::calculateValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void ship() {
        updateStatusForShipment();
        updateOrderDetailsForShipment();
        updateShippingDate();
    }

    private void updateOrderDetailsForShipment() {
        for (OrderDetail orderDetail : orderDetails) {
            orderDetail.updateProductForShipment();
        }
    }

    private void updateStatusForShipment() {
        if (Objects.requireNonNull(getStatus()) == Status.SHIPPED) {
            throw new OrderAlreadyShippedException();
        } else {
            status = Status.SHIPPED;
        }
    }

    private void updateShippingDate() {
        shipped = LocalDate.now();
    }
}
