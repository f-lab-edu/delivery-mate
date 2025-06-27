package ksh.deliverymate.order.repository;

import jakarta.persistence.LockModeType;
import ksh.deliverymate.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderQueryRepository {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Order> findByIdAndRiderIdIsNull(long id);
}
