package ksh.deliverymate.order.dto.response;

import ksh.deliverymate.order.repository.projection.OrderItemDetail;
import ksh.deliverymate.order.repository.projection.OrderStoreInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Slice;

import java.util.List;

@Getter
@AllArgsConstructor
public class WaitingForRiderOrderDto {

    private Slice<OrderStoreInfo> orderStoreInfos;
    private List<OrderItemDetail> orderItemDetails;


    public static WaitingForRiderOrderDto of(
        Slice<OrderStoreInfo> orderStoreInfos,
        List<OrderItemDetail> orderItemDetails
    ) {
        return new WaitingForRiderOrderDto(orderStoreInfos, orderItemDetails);
    }
}
