package ksh.deliverymate.global.lock;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LockKey {
    RIDER_ASSIGNMENT("order:{orderId}:rider:assignment");

    private final String key;

    public String makeKey(Object... args) {
        return String.format(key, args);
    }
}
