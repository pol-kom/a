package kp.w_s;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.jws.WebService;

import java.io.Serial;
import java.util.List;
import java.util.logging.Logger;

/**
 * The web service endpoint {@link WebSe} implemented as a <b>stateless</b> session bean.
 */
@Stateless
@WebService
public class WebSeImplC implements WebSe {
    @Serial
    private static final long serialVersionUID = 1L;
    private transient Logger logger;
    /**
     * The report.
     */
    private List<String> report;

    /**
     * Default constructor.
     */
    public WebSeImplC() {
        // constructor is empty
    }

    /**
     * Parameterized constructor.
     *
     * @param logger the {@link Logger}
     * @param report the report
     */
    @Inject
    public WebSeImplC(Logger logger, List<String> report) {
        this();
        this.logger = logger;
        this.report = report;
    }

    /**
     * {@inheritDoc} Implemented.
     */
    @Override
    public String tryIt(String arg) {

        final String msg = "implementation[%s], arg[%s]".formatted(this.getClass().getSimpleName(), arg);
        report.add(msg);
        logger.info("tryIt():");
        return msg;
    }
}