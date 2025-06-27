package ksh.deliverymate.order.repository;

import ksh.deliverymate.order.repository.projection.OrderItemDetail;

import java.util.List;

public interface OrderItemQueryRepository {

    List<OrderItemDetail> getDetailsByOrderIdIn(List<Long> orderIds);
}
