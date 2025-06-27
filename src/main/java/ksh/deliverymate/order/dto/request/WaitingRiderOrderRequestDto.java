package ksh.deliverymate.order.dto.request;

import jakarta.validation.constraints.*;
import ksh.deliverymate.order.vo.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WaitingRiderOrderRequestDto {

    @NotNull(message = "라이더의 현재 위도는 필수입니다.")
    @DecimalMax(value = " 90.0", message = "위도는  90.0 이하이어야 합니다.")
    @DecimalMin(value = "-90.0", message = "위도는 -90.0 이상이어야 합니다.")
    private Double latitude;

    @NotNull(message = "라이더의 현재 경도는 필수입니다.")
    @DecimalMax(value = " 180.0", message = "경도는  180.0 이하이어야 합니다.")
    @DecimalMin(value = "-180.0", message = "경도는 -180.0 이상이어야 합니다.")
    private Double longitude;

    @NotNull(message = "탐색 반경은 필수입니다.")
    @Max(value = 1000, message = "탐색 반경은 최대 1000m입니다.")
    @Positive(message = "탐색 반경은 0 보다 커야 합니다.")
    private Integer radius;

    public Position getPosition() {
        return Position.of(getLatitude(), getLongitude());
    }
}
