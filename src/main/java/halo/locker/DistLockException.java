package halo.locker;

/**
 * 锁异常
 * Created by akwei on 6/22/15.
 */
public class DistLockException extends RuntimeException {

    public DistLockException(String message) {
        super(message);
    }
}