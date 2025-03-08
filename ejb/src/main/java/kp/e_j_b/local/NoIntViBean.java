package kp.e_j_b.local;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The stateless session bean.
 * <p>
 * This is <b>local</b>, no-interface view enterprise bean.
 * </p>
 * <p>
 * Because this enterprise bean class does not implement a business interface,
 * the enterprise bean exposes a <b>local</b>, no-interface view.
 * A business interface is not required if the enterprise bean exposes
 * a <b>local</b>, no-interface view.
 * </p>
 */
@Stateless
public class NoIntViBean {
    private Logger logger;

    /**
     * Default constructor.
     */
    public NoIntViBean() {
        // constructor is empty
    }

    /**
     * Parameterized constructor.
     *
     * @param logger the {@link Logger}
     */
    @Inject
    public NoIntViBean(Logger logger) {
        this.logger = logger;
    }

    /**
     * Checks the bean implementation.
     * <p>
     * Business method.
     * </p>
     *
     * @param stamp the stamp
     * @return message the message
     */
    public String check(String stamp) {

        final String message = String.format("implementation[%s], stamp[%s], no-interface view stateless bean",
                this.getClass().getSimpleName(), stamp);
        if (logger.isLoggable(Level.INFO)) {
            logger.info(String.format("check(): %s", message));
        }
        return message;
    }
}