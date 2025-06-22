package ksh.deliverymate.order.repository;

import ksh.deliverymate.order.entity.OrderStatus;
import ksh.deliverymate.order.repository.projection.OrderStoreInfo;
import ksh.deliverymate.order.vo.Coordinate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface OrderQueryRepository {

    Slice<OrderStoreInfo> findIdByStatusAndWithinRadius(OrderStatus status, Coordinate center, int radius, Pageable pageable);
}
