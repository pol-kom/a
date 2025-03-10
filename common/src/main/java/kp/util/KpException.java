package kp.util;

import java.io.Serial;

/**
 * Customized exception.
 */
public class KpException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public KpException() {
        // constructor is empty
    }

    /**
     * Parameterized constructor.
     *
     * @param message the message
     */
    public KpException(String message) {
        super(message);
    }
}
