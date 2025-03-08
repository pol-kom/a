package kp.e_j_b.remote;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import kp.e_j_b.CommonImpl;

import java.io.Serial;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * The implementation of the <b>stateless</b> session bean interface {@link StaLe}.
 * <p>
 * This is the <b>remote</b> view enterprise bean.
 * </p>
 */
@Stateless
public class StaLeBean extends CommonImpl implements StaLe, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Parameterized constructor.
     *
     * @param logger the {@link Logger}
     */
    @Inject
    public StaLeBean(Logger logger) {
        super(logger);
    }
}