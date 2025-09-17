package be.vdab.toys.orders;

import be.vdab.toys.orders.data.Order;
import be.vdab.toys.orders.data.Status;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface OrderRepository extends JpaRepository  <Order, Long> {
    @EntityGraph(attributePaths = "customer")
    List<Order> findAllByStatusNotIn(Set<Status> statuses);

    @EntityGraph(attributePaths = {"customer", "customer.country", "orderDetails", "orderDetails.product"})
    Optional<Order> findById(long id);
}
