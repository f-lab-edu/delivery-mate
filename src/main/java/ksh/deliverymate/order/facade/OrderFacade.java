package ksh.deliverymate.order.facade;

import ksh.deliverymate.global.dto.request.PageRequestDto;
import ksh.deliverymate.global.lock.LockKey;
import ksh.deliverymate.global.lock.service.LockService;
import ksh.deliverymate.order.dto.request.WaitingRiderOrderRequestDto;
import ksh.deliverymate.order.dto.response.WaitingRiderOrderDto;
import ksh.deliverymate.order.repository.projection.OrderItemDetail;
import ksh.deliverymate.order.repository.projection.OrderWithStore;
import ksh.deliverymate.order.service.OrderItemService;
import ksh.deliverymate.order.service.OrderService;
import ksh.deliverymate.order.vo.Position;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderFacade {

    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final LockService lockService;

    public Slice<WaitingRiderOrderDto> findOrderInfosWaitingForRider(
        WaitingRiderOrderRequestDto request,
        PageRequestDto pageRequest
    ) {
        Position center = request.getPosition();
        int radius = request.getRadius();
        Pageable pageable = pageRequest.getPageable();

        Slice<OrderWithStore> waitingOrders = orderService
            .findNearbyOrderWaitingForRider(center, radius, pageable);

        List<Long> orderIds = extractOrderIdsFrom(waitingOrders);
        List<OrderItemDetail> orderItemDetails
            = orderItemService.getDetailsByOrderIdIn(orderIds);

        Map<Long, List<OrderItemDetail>> mapByOrderId
            = groupOrderItemsByOrderId(orderItemDetails);

        return waitingOrders.map(
            info -> WaitingRiderOrderDto.of(
                info,
                mapByOrderId.get(info.getOrderId())
            )
        );
    }

    public void assignRider(long id, long riderId) {
        lockService.executeWithLock(
            LockKey.RIDER_ASSIGNMENT.makeKey(id),
            3000,
            3000,
            TimeUnit.MILLISECONDS,
            () -> {
                orderService.assignRider(id, riderId);
                return null;
            }
        );
    }

    private static List<Long> extractOrderIdsFrom(Slice<OrderWithStore> orderStoreInfos) {
        return orderStoreInfos.stream()
            .map(OrderWithStore::getOrderId)
            .toList();
    }

    private static Map<Long, List<OrderItemDetail>> groupOrderItemsByOrderId(List<OrderItemDetail> orderItemDetails) {
        return orderItemDetails
            .stream()
            .collect(Collectors.groupingBy(
                OrderItemDetail::getOrderId
            ));
    }
}
