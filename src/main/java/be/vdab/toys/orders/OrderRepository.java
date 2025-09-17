package be.vdab.toys.orders;

import be.vdab.toys.orders.data.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository  <Order, Long> {
}
