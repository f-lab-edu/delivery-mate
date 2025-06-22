package ksh.deliverymate.order.facade;

import ksh.deliverymate.order.dto.response.WaitingForRiderOrderDto;
import ksh.deliverymate.order.repository.OrderItemRepository;
import ksh.deliverymate.order.repository.projection.OrderItemDetail;
import ksh.deliverymate.order.repository.projection.OrderStoreInfo;
import ksh.deliverymate.order.service.OrderItemService;
import ksh.deliverymate.order.service.OrderService;
import ksh.deliverymate.order.vo.Coordinate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderFacade {

    private final OrderService orderService;
    private final OrderItemService orderItemService;

    public WaitingForRiderOrderDto findOrderInfosWaitingForRider(
        Coordinate center,
        int radius,
        Pageable pageable
    ) {
        Slice<OrderStoreInfo> orderStoreInfos = orderService.findNearbyOrderIdsWaitingForRiderAssignment(
            center,
            radius,
            pageable
        );

        List<Long> orderIds = orderStoreInfos.stream()
            .map(OrderStoreInfo::getOrderId)
            .toList();

        List<OrderItemDetail> orderItemDetails = orderItemService.getDetailsByOrderIdIn(orderIds);

        return WaitingForRiderOrderDto.of(orderStoreInfos, orderItemDetails);
    }
}
