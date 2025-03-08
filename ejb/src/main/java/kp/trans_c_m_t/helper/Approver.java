package kp.trans_c_m_t.helper;

import jakarta.annotation.Resource;
import jakarta.ejb.EJBContext;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import kp.trans_c_m_t.service.AuditorBean;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The parcel approver.
 */
@Stateless
public class Approver {
    private Logger logger;
    private List<String> report;
    private AuditorBean auditorBean;

    @Resource
    private EJBContext ejbContext;

    /**
     * Default constructor.
     */
    public Approver() {
        // empty constructor
    }

    /**
     * Parameterized constructor.
     *
     * @param logger the {@link Logger}
     * @param report the report
     * @param auditorBean the {@link AuditorBean}
     */
    @Inject
    public Approver(Logger logger, List<String> report,
                    AuditorBean auditorBean) {
        this();
        this.logger = logger;
        this.report = report;
        this.auditorBean = auditorBean;
    }

    /**
     * Approves the parcels.
     * <ul>
     * <li>Approves only parcels with the id equal to <b>even</b> value.</li>
     * <li>Rollbacks all parcels with the id equal to <b>odd</b> value.</li>
     * </ul>
     *
     * @param parcelId the parcel id
     */
    @Transactional
    public void approve(int parcelId) {

        final String msg;
        if (parcelId % 2 == 1) {
            ejbContext.setRollbackOnly();
            msg = "The current transaction is marked for rollback, parcelId[%d].".formatted(parcelId);
            report.add(msg);
        } else {
            auditorBean.recordApproved();
            msg = "OK";
        }
        if (logger.isLoggable(Level.INFO)) {
            logger.info("approve(): %s".formatted(msg));
        }
    }
}