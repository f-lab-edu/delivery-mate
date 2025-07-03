package ksh.deliverymate.global.lock.service;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public interface LockService {

    <T> T executeWithLock(String key, long waitTime, long leaseTime, TimeUnit unit, Callable<T> task);
}
