package ksh.deliverymate.order.repository;

import ksh.deliverymate.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderQueryRepository {
}
