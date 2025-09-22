package be.vdab.toys.orders.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class OrderInsufficientStockException extends RuntimeException {
    public OrderInsufficientStockException() {
        super("Insufficient stock for this order");
    }
}
