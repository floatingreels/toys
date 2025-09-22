package be.vdab.toys.orders;

import be.vdab.toys.orders.data.Order;
import be.vdab.toys.orders.data.Status;
import be.vdab.toys.orders.error.OrderNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    List<Order> findAllUnshipped() {
        Set<Status> statuses = Set.of(Status.CANCELLED, Status.SHIPPED);
        return orderRepository.findAllByStatusNotIn(statuses);
    }

    Optional<Order> findByNumber(long number) {
        return orderRepository.findById(number);
    }

    @Transactional
    void shipOrder(long number) {
        orderRepository.findAndLockById(number)
                .orElseThrow(() -> new OrderNotFoundException(number))
                .ship();
    }
}
