package kp.cdi.interceptors;

/**
 * The elapsed time bean.
 */
@Elapsed
public class ElapsedBean {
    private static final int PAUSE_NANO = 1;
    private static final int PAUSE_MICRO = 1_000;
    private static final int PAUSE_MILLI = 1_000_000;

    /**
     * Do not pause at all.
     */
    public void notPaused() {
		/*-
		 * do not pause
		 */
    }

    /**
     * Pause one nanosecond.
     */
    public void pausedNano() {

        long start = System.nanoTime();
        long diff;
        do {
            diff = System.nanoTime() - start;
        } while (diff < PAUSE_NANO);
    }

    /**
     * Pause one microsecond.
     */
    public void pausedMicro() {

        long start = System.nanoTime();
        long diff;
        do {
            diff = System.nanoTime() - start;
        } while (diff < PAUSE_MICRO);
    }

    /**
     * Pause one millisecond.
     */
    public void pausedMilli() {

        long start = System.nanoTime();
        long diff;
        do {
            diff = System.nanoTime() - start;
        } while (diff < PAUSE_MILLI);
    }
}