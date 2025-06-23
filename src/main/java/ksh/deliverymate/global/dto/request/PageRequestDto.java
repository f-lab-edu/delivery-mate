package ksh.deliverymate.global.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Getter
public class PageRequestDto {

    @NotNull(message = "페이지 번호는 필수입니다.")
    @Min(value = 0, message = "페이지 번호는 0부터 시작합니다.")
    private Integer page;

    @NotNull(message = "페이지 크기는 필수입니다.")
    @Positive(message = "페이지 크기는 양수입니다.")
    @Max(value = 20, message = "페이지의 최대 크기는 20입니다.")
    private Integer size;

    public Pageable getPageable() {
        return PageRequest.of(page, size);
    }
}
