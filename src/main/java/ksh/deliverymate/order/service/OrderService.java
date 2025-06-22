package ksh.deliverymate.order.service;

import ksh.deliverymate.order.entity.OrderStatus;
import ksh.deliverymate.order.repository.OrderRepository;
import ksh.deliverymate.order.repository.projection.OrderStoreInfo;
import ksh.deliverymate.order.vo.Coordinate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public Slice<OrderStoreInfo> findNearbyOrderIdsWaitingForRiderAssignment(
        Coordinate center,
        int radius,
        Pageable pageable
    ) {
        return orderRepository.findIdByStatusAndWithinRadius(
            OrderStatus.ACCEPTED,
            center,
            radius,
            pageable
        );
    }
}
