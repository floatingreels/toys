package be.vdab.toys.orders.data;

import java.time.LocalDate;

public record OrderUnshippedResponse(
        long id,
        LocalDate ordered,
        LocalDate required,
        String name,
        Status status
) {
    public OrderUnshippedResponse(Order order) {
        this(
                order.getId(),
                order.getOrdered(),
                order.getRequired(),
                order.getCustomer().getName(),
                order.getStatus());
    }
}

