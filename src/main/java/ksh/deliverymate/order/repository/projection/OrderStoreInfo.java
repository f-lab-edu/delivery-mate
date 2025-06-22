package ksh.deliverymate.order.repository.projection;

import ksh.deliverymate.store.vo.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderStoreInfo {

    private long orderId;
    private String name;
    private Address address;
    private String phone;
    private double distance;
}
