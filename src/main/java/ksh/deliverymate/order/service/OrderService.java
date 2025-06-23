package ksh.deliverymate.order.service;

import ksh.deliverymate.order.entity.OrderStatus;
import ksh.deliverymate.order.repository.OrderRepository;
import ksh.deliverymate.order.repository.projection.OrderStoreInfo;
import ksh.deliverymate.order.vo.Position;
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
    public Slice<OrderStoreInfo> findNearbyOrderWaitingForRider(
        Position center,
        int radius,
        Pageable pageable
    ) {
        return orderRepository.findByStatusAndWithinRadius(
            OrderStatus.ACCEPTED,
            center,
            radius,
            pageable
        );
    }
}
