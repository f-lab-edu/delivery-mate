package ksh.deliverymate.order.dto.response;

import ksh.deliverymate.order.repository.projection.OrderItemDetail;
import ksh.deliverymate.order.repository.projection.OrderWithStore;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class WaitingRiderOrderDto {

    private OrderWithStore orderStoreInfo;
    private List<OrderItemDetail> orderItemDetails;


    public static WaitingRiderOrderDto of(
        OrderWithStore orderStoreInfos,
        List<OrderItemDetail> orderItemDetails
    ) {
        return new WaitingRiderOrderDto(orderStoreInfos, orderItemDetails);
    }
}
