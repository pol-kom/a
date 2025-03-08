package kp.e_j_b.local;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import kp.e_j_b.CommonImpl;

import java.io.Serial;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * The implementation of the <b>stateless</b> session bean interface
 * {@link StaLeLocal}.
 * <p>
 * This is the <b>local</b> view enterprise bean.
 * </p>
 */
@Stateless
public class StaLeLocalBean extends CommonImpl implements StaLeLocal, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Parameterized constructor.
     *
     * @param logger the {@link Logger}
     */
    @Inject
    public StaLeLocalBean(Logger logger) {
        super(logger);
    }
}