package ksh.deliverymate.order.dto.response;

import ksh.deliverymate.order.repository.projection.OrderItemDetail;
import ksh.deliverymate.order.repository.projection.OrderStoreInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class WaitingRiderOrderDto {

    private OrderStoreInfo orderStoreInfo;
    private List<OrderItemDetail> orderItemDetails;


    public static WaitingRiderOrderDto of(
        OrderStoreInfo orderStoreInfos,
        List<OrderItemDetail> orderItemDetails
    ) {
        return new WaitingRiderOrderDto(orderStoreInfos, orderItemDetails);
    }
}
