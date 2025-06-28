package ksh.deliverymate.order.entity;

import jakarta.persistence.*;
import ksh.deliverymate.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer totalPrice;

    private Integer discountAmount;

    private Integer usedPoint;

    private Integer finalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Long userId;

    private Long storeId;

    private Long riderId;

    @SQLDelete(sql = "update order set is_deleted = true where id = ?")
    @Where(clause = "isDeleted = 0")
    private boolean isDeleted;

    public void assignRider(long riderId) {
        this.riderId = riderId;
        this.status = OrderStatus.ASSIGNED;
    }
}
