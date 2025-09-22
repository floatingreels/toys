package be.vdab.toys.orders.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class OrderCurrentlyBeingModifiedException extends RuntimeException {
    public OrderCurrentlyBeingModifiedException() {
        super("The order you are trying to ship has been modified");
    }
}
