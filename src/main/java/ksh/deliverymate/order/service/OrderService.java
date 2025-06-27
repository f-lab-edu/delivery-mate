package ksh.deliverymate.order.service;

import ksh.deliverymate.global.exception.CustomException;
import ksh.deliverymate.global.exception.ErrorCode;
import ksh.deliverymate.order.entity.OrderStatus;
import ksh.deliverymate.order.repository.OrderRepository;
import ksh.deliverymate.order.repository.projection.OrderWithStore;
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
    public Slice<OrderWithStore> findNearbyOrderWaitingForRider(
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

    @Transactional
    public void assignRider(long id, long riderId) {
        orderRepository.findByIdAndRiderIdIsNull(id)
            .orElseThrow(() -> new CustomException(ErrorCode.ORDER_ALREADY_ASSIGNED_RIDER))
            .assignRider(riderId);
    }
}
