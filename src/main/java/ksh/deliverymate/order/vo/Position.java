package ksh.deliverymate.order.vo;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Position {

    private double latitude;
    private double longitude;

    public static Position of(double latitude, double longitude) {
        return Position.builder()
            .latitude(latitude)
            .longitude(longitude)
            .build();
    }
}
