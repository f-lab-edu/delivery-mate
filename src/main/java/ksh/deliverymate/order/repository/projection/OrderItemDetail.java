package ksh.deliverymate.order.repository.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderItemDetail {

    private long orderId;
    private String menuName;
    private int quantity;
}
