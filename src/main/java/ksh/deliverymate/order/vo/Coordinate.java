package ksh.deliverymate.order.vo;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Coordinate {

    private double latitude;
    private double longitude;

    public static Coordinate of(double latitude, double longitude) {
        return Coordinate.builder()
            .latitude(latitude)
            .longitude(longitude)
            .build();
    }
}
