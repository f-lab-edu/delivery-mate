package ksh.deliverymate.order.repository;

import jakarta.persistence.LockModeType;
import ksh.deliverymate.order.entity.Order;
import ksh.deliverymate.order.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderQueryRepository {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Order> findByIdAndStatusAndRiderIdIsNull(long id, OrderStatus status);
}
