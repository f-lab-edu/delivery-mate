package ksh.deliverymate.global.lock.service;

import ksh.deliverymate.global.exception.CustomException;
import ksh.deliverymate.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedissonLockService implements LockService {

    private final RedissonClient redissonClient;

    @Override
    public <T> T executeWithLock(
        String key,
        long waitTime,
        long leaseTime,
        TimeUnit unit,
        Callable<T> task
    ) {
        RLock lock = null;
        try {
            lock = acquireLock(key, waitTime, leaseTime, unit);
            return task.call();
        } catch (Exception e) {
            if (e instanceof CustomException) {
                throw (CustomException) e;
            }

            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        } finally {
            releaseLock(lock);
        }
    }

    private RLock acquireLock(
        String key,
        long waitTime,
        long leaseTime,
        TimeUnit unit
    ) {
        RLock lock = redissonClient.getLock(key);

        try {
            boolean acquired = lock.tryLock(waitTime, leaseTime, unit);

            if (!acquired) {
                throw new CustomException(ErrorCode.LOCK_TIMEOUT);
            }
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            throw new CustomException(ErrorCode.LOCK_ACQUIRE_INTERRUPTED);
        }

        return lock;
    }

    private  void releaseLock(RLock lock) {
        if (lock == null || !lock.isHeldByCurrentThread()) {
            return;
        }

        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCompletion(int status) {
                    lock.unlock();
                }
            });
        } else {
            lock.unlock();
        }
    }
}
