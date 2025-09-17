package be.vdab.toys.orders.data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record OrderFullResponse(
        long id,
        LocalDate ordered,
        LocalDate required,
        String customerName,
        String countryName,
        BigDecimal value,
        Stream<OrderDetailResponse> details
) {
    public OrderFullResponse(Order order) {
        this(
                order.getId(),
                order.getOrdered(),
                order.getRequired(),
                order.getCustomer().getName(),
                order.getCustomer().getCountry().getName(),
                order.calculateTotal(),
                order.getOrderDetails().stream().map(OrderDetailResponse::new)
        );
    }
}
