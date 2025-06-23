package ksh.deliverymate.order.dto.response;

import ksh.deliverymate.order.repository.projection.OrderStoreInfo;
import ksh.deliverymate.store.vo.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class WaitingRiderOrderResponseDto {

    private long id;
    private String storeName;
    private Address address;
    private double distance;
    private List<OrderItemDetailResponseDto> orderItems;

    public static WaitingRiderOrderResponseDto from(
        WaitingRiderOrderDto orderDto
    ) {
        OrderStoreInfo orderStoreInfo = orderDto.getOrderStoreInfo();

        List<OrderItemDetailResponseDto> orderItems = orderDto.getOrderItemDetails()
            .stream()
            .map(OrderItemDetailResponseDto::from)
            .toList();

        return WaitingRiderOrderResponseDto.builder()
            .id(orderStoreInfo.getOrderId())
            .storeName(orderStoreInfo.getName())
            .address(orderStoreInfo.getAddress())
            .distance(orderStoreInfo.getDistance())
            .orderItems(orderItems)
            .build();

    }
}
