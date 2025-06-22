package ksh.deliverymate.order.vo;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Positoion {

    private double latitude;
    private double longitude;

    public static Positoion of(double latitude, double longitude) {
        return Positoion.builder()
            .latitude(latitude)
            .longitude(longitude)
            .build();
    }
}
