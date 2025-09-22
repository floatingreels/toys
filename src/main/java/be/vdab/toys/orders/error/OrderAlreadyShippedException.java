package be.vdab.toys.orders.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class OrderAlreadyShippedException extends RuntimeException {
    public OrderAlreadyShippedException() {
        super("Order is already shipped");
    }
}
