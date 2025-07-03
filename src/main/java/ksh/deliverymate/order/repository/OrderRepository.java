package ksh.deliverymate.order.repository;

import ksh.deliverymate.order.entity.Order;
import ksh.deliverymate.order.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderQueryRepository {

    Optional<Order> findByIdAndStatusAndRiderIdIsNull(long id, OrderStatus status);
}
