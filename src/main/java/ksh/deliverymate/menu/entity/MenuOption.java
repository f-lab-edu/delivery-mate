package ksh.deliverymate.menu.entity;

import jakarta.persistence.*;
import ksh.deliverymate.BaseEntity;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuOption extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer price;

    private Long menuId;
}
