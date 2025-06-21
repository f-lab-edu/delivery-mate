package ksh.deliverymate.menu.entity;

import jakarta.persistence.*;
import ksh.deliverymate.BaseEntity;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private MenuStatus status;

    private Integer price;

    private String image;

    private Long storeId;
}
