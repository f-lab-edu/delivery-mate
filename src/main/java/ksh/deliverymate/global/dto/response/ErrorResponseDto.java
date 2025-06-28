package ksh.deliverymate.global.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponseDto {

    private final int status;
    private final String code;
    private final String message;

    public static ErrorResponseDto of(int status, String code, String message) {
        return new ErrorResponseDto(status, code, message);
    }
}
