package kp.e_j_b.remote;

import jakarta.ejb.Stateful;
import jakarta.inject.Inject;
import kp.e_j_b.CommonImpl;

import java.io.Serial;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * The implementation of the <b>stateful</b> session bean interface {@link StaFu}.
 * <p>
 * This is the <b>remote</b> view enterprise bean.
 * </p>
 */
@Stateful
public class StaFuBean extends CommonImpl implements StaFu, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Parameterized constructor.
     *
     * @param logger the {@link Logger}
     */
    @Inject
    public StaFuBean(Logger logger) {
        super(logger);
    }
}