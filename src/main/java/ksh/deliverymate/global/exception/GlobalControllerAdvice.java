package ksh.deliverymate.global.exception;

import ksh.deliverymate.global.dto.response.ErrorResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {

    private final MessageSource messageSource;

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomException(CustomException e, Locale locale) {
        ErrorCode errorCode = e.getErrorCode();

        String errorMessage = messageSource.getMessage(
            errorCode.getMessageKey(),
            e.getMessageArgs().toArray(),
            locale
        );

        ErrorResponseDto response = ErrorResponseDto.of(
            errorCode.getStatus(),
            errorCode.name(),
            errorMessage
        );

        return ResponseEntity
            .status(errorCode.getStatus())
            .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception e, Locale locale) {
        ErrorResponseDto response = ErrorResponseDto.error();

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(response);
    }
}
