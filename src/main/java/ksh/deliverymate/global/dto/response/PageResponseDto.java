package ksh.deliverymate.global.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Slice;

import java.util.List;

@Getter
@AllArgsConstructor
public class PageResponseDto<T> {

    private boolean hasNext;
    private List<T> content;

    public static <T> PageResponseDto<T> of(Slice<T> slice) {
        return new PageResponseDto<>(slice.hasNext(), slice.getContent());
    }
}
