package ksh.deliverymate.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    ORDER_ALREADY_ASSIGNED_RIDER(400, "order.already.assigned.rider"),

    LOCK_TIMEOUT(400, "lock.timeout"),
    LOCK_ACQUIRE_INTERRUPTED(503, "lock.acquire.interrupted"),

    INTERNAL_SERVER_ERROR(500, "internal.server.error"),;

    private final int status;
    private final String messageKey;
}
