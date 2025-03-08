package kp.e_j_b.interceptors;

import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;
import kp.util.Tools;

import java.util.List;
import java.util.logging.Logger;

/**
 * Time elapsed interceptor.
 * <p>
 * The interceptor functionality is defined in the Java Interceptors specification.
 * </p>
 * <p>
 * CDI enhances this functionality. But CDI-style interceptor is not
 * researched here because it needs enabling in the 'beans.xml'.
 * </p>
 */
public class TimeElapsedInterceptor {
    private Logger logger;
    private List<String> report;

    private static final int PAUSE = 1;

    /**
     * Default constructor.
     */
    public TimeElapsedInterceptor() {
        // constructor is empty
    }

    /**
     * Sets the logger.
     *
     * @param logger the logger
     */
    @Inject
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    /**
     * Sets the report.
     *
     * @param report the report
     */
    @Inject
    public void setReport(List<String> report) {
        this.report = report;
    }

    /**
     * Reports the elapsed time. The intercepting method.
     *
     * @param invocationContext the invocation context
     * @return the result
     */
    @AroundInvoke
    public Object reportTimeElapsed(InvocationContext invocationContext) {

        Object result = null;
        final long start = System.nanoTime();
        try {
            result = invocationContext.proceed();
        } catch (Exception e) {
            // ignore
        }
        final long diff = System.nanoTime() - start;
        final String msg = "Method[%s], time elapsed [%d]ns (reference), [%s]ns (after invoke)"
                .formatted(invocationContext.getMethod().getName(), getReference(), Tools.formatNumber(diff));
        report.add(msg);
        logger.info("reportTimeElapsed():");
        return result;
    }

    /**
     * Gets the reference measure.
     *
     * @return the message
     */
    private long getReference() {

        long start = System.nanoTime();
        long diff;
        do {
            diff = System.nanoTime() - start;
        } while (diff < PAUSE);
        return diff;
    }
}