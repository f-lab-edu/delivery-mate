package ksh.deliverymate.order.repository;

import ksh.deliverymate.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>, OrderItemQueryRepository {
}
