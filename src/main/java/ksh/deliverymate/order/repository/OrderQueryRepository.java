package ksh.deliverymate.order.repository;

import ksh.deliverymate.order.entity.OrderStatus;
import ksh.deliverymate.order.repository.projection.OrderStoreInfo;
import ksh.deliverymate.order.vo.Positoion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface OrderQueryRepository {

    Slice<OrderStoreInfo> findByStatusAndWithinRadius(OrderStatus status, Positoion center, int radius, Pageable pageable);
}
