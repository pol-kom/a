package kp.e_j_b.interceptors;

import jakarta.ejb.Stateless;
import jakarta.interceptor.Interceptors;

/**
 * The bean with method invocation Interceptor {@link TimeElapsedInterceptor}.
 */
@Stateless
public class TimeElapsedBean {
    private static final int PAUSE_NANO = 1;
    private static final int PAUSE_MILLI = 1_000_000;

    /**
     * Default constructor.
     */
    public TimeElapsedBean() {
        // constructor is empty
    }

    /**
     * No pause at all.
     */
    @Interceptors(TimeElapsedInterceptor.class)
    public void notPaused() {
        /*
         * do not pause
         */
    }

    /**
     * Pause for one nanosecond.
     */
    @Interceptors(TimeElapsedInterceptor.class)
    public void pausedNano() {
        long start = System.nanoTime();
        long diff;
        do {
            diff = System.nanoTime() - start;
        } while (diff < PAUSE_NANO);
    }

    /**
     * Pause for one millisecond.
     */
    @Interceptors(TimeElapsedInterceptor.class)
    public void pausedMilli() {
        long start = System.nanoTime();
        long diff;
        do {
            diff = System.nanoTime() - start;
        } while (diff < PAUSE_MILLI);
    }
}