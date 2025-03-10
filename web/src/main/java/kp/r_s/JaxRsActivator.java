package kp.r_s;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * A class extending {@link Application} and annotated with {@link ApplicationPath} annotation
 * is the Java EE "no XML" approach to activating JAX-RS.
 * <p>
 * Resources are served relative to the servlet path specified in the {@link ApplicationPath} annotation.
 * </p>
 */
@ApplicationPath("/rs")
public class JaxRsActivator extends Application {
    // class body intentionally left blank
}