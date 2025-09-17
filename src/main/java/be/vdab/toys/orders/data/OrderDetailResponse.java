package be.vdab.toys.orders.data;

import java.math.BigDecimal;

public record OrderDetailResponse(
        int ordered,
        BigDecimal priceEach,
        BigDecimal value,
        String productName
) {
    public OrderDetailResponse(OrderDetail  orderDetail) {
        this(
                orderDetail.getOrdered(),
                orderDetail.getPriceEach(),
                orderDetail.calculateValue(),
                orderDetail.getProduct().getName()
        );
    }
}
