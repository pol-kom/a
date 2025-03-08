package kp.e_j_b;

import kp.util.Tools;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The abstract implementation of the {@link Common} interface.
 */
public abstract class CommonImpl implements Common {
    private int previous = 0;
    private Logger logger;

    /**
     * Default constructor.
     */
    protected CommonImpl() {
        // constructor is empty
    }

    /**
     * Parameterized constructor.
     *
     * @param logger the {@link Logger}
     */
    protected CommonImpl(Logger logger) {
        this.logger = logger;
    }

    /**
     * {@inheritDoc} Implemented.
     */
    @Override
    public String check(String stamp) {

        final String message = "implementation[%s], stamp[%s]".formatted(this.getClass().getSimpleName(), stamp);
        if (logger.isLoggable(Level.INFO)) {
            logger.info("check(): %s".formatted(message));
        }
        return message;
    }

    /**
     * {@inheritDoc} Implemented.
     */
    @Override
    public String change(String stamp, int number) {

        final String message = ("stamp[%s], current number[%s], previous number[%s], " +
                                "current equals previous[%b], object hash code[%s]").formatted(
                stamp, number, previous, number == previous, Tools.hashCodeFormatted(this));
        previous = number;
        if (logger.isLoggable(Level.INFO)) {
            logger.info("change():%n\t%s".formatted(message));
        }
        return message;
    }
}