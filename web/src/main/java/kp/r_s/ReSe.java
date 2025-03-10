package kp.r_s;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import java.io.Serial;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * The RESTful Web Services implemented as a stateless session bean.
 */
@Stateless
@Path("text")
public class ReSe implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private transient Logger logger;

    /**
     * Default constructor.
     */
    public ReSe() {
        // constructor is empty
    }

    /**
     * Parameterized constructor.
     *
     * @param logger the {@link Logger}
     */
    @Inject
    public ReSe(Logger logger) {
        this();
        this.logger = logger;
    }

    /**
     * Gets the text.
     *
     * @return the text
     */
    @GET
    public String getText() {
        logger.info("getText():");
        return "text from 'ReSe' (no-interface view stateless session bean)";
    }
}