package ksh.deliverymate.store.entity;

import jakarta.persistence.*;
import ksh.deliverymate.store.vo.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Embedded
    private Address address;

    @Column(columnDefinition = "point not null srid 4326")
    private Point coordinate;

    private String phone;

    @Enumerated(EnumType.STRING)
    private FoodCategory category;

    @Enumerated(EnumType.STRING)
    private StoreStatus status;

    private Long ownerId;
}
