package kp.e_j_b.remote;

import jakarta.ejb.Singleton;
import jakarta.inject.Inject;
import kp.e_j_b.CommonImpl;

import java.io.Serial;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * The implementation of the <b>singleton</b> session bean interface {@link Sing}.
 * <p>
 * This is the <b>remote</b> view enterprise bean.
 * </p>
 */
@Singleton
public class SingBean extends CommonImpl implements Sing, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Parameterized constructor.
     *
     * @param logger the {@link Logger}
     */
    @Inject
    public SingBean(Logger logger) {
        super(logger);
    }
}