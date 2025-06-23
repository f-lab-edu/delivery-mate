package ksh.deliverymate.order.entity;

import jakarta.persistence.*;
import ksh.deliverymate.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    private Integer menuPrice;

    private Integer optionPrice;

    private Long orderId;

    private Long menuId;

    private Long optionId;
}
