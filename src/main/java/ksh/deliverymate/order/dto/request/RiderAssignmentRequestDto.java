package ksh.deliverymate.order.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RiderAssignmentRequestDto {

    @NotNull(message = "주문 id는 필수입니다.")
    private Long id;

    @NotNull(message = "주문에 배정할 라이더의 id는 필수입니다.")
    private Long riderId;
}
