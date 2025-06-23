package ksh.deliverymate.store.vo;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@ToString
@EqualsAndHashCode
public class Address {

    private String city;
    private String district;
    private String subdistrict;
    private String street;
    private String building;

    public static Address of (String city, String district, String subdistrict, String street, String building) {
        return Address.builder()
            .city(city)
            .district(district)
            .subdistrict(subdistrict)
            .street(street)
            .building(building)
            .build();
    }
}

