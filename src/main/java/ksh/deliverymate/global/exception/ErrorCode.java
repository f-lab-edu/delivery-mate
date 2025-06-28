package ksh.deliverymate.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    ORDER_ALREADY_ASSIGNED_RIDER(400, "order.already.assigned.rider"),;

    private final int status;
    private final String messageKey;
}
