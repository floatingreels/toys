package be.vdab.toys.orders;

import be.vdab.toys.orders.data.OrderFullResponse;
import be.vdab.toys.orders.data.OrderUnshippedResponse;
import be.vdab.toys.orders.error.OrderNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
@RequestMapping("orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("unshipped")
    public Stream<OrderUnshippedResponse> findAllUnshipped() {
        return orderService.findAllUnshipped()
                .stream()
                .map(OrderUnshippedResponse::new);
    }

    @GetMapping("{number}")
    public OrderFullResponse findById(@PathVariable long number) {
        return orderService.findByNumber(number)
                .map(OrderFullResponse::new)
                .orElseThrow(() -> new OrderNotFoundException(number));
    }
}
