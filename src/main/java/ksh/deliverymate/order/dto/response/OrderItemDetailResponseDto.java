package ksh.deliverymate.order.dto.response;

import ksh.deliverymate.order.repository.projection.OrderItemDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class OrderItemDetailResponseDto {

    private String name;
    private int quantity;

    public static OrderItemDetailResponseDto from(
        OrderItemDetail orderItemDetail
    ) {
        return OrderItemDetailResponseDto.builder()
            .name(orderItemDetail.getMenuName())
            .quantity(orderItemDetail.getQuantity())
            .build();
    }
}
