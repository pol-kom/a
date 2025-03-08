package kp.trans_c_m_t.service;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.transaction.Transactional;
import kp.trans_c_m_t.domain.Parcel;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The <b>stateless</b> session bean for the {@link Parcel} administrator.
 * <p>
 * Using Container-Managed Transactions.
 * </p>
 * <p>
 * Transaction Attributes: the 'Required' attribute is the implicit transaction attribute
 * for all enterprise bean methods running with container-managed transaction demarcation.
 * </p>
 */
@Stateless
public class ParcelAdministratorBean {
    private Logger logger;
    private List<String> report;
    private EntityManager entityManager;
    private AuditorBean auditorBean;

    /**
     * Default constructor.
     */
    public ParcelAdministratorBean() {
        // empty constructor
    }

    /**
     * Parameterized constructor.
     *
     * @param logger        the {@link Logger}
     * @param report        the report
     * @param entityManager the {@link EntityManager}
     * @param auditorBean   the {@link AuditorBean}
     */
    @Inject
    public ParcelAdministratorBean(Logger logger, List<String> report,
                                   EntityManager entityManager, AuditorBean auditorBean) {
        this();
        this.logger = logger;
        this.report = report;
        this.entityManager = entityManager;
        this.auditorBean = auditorBean;
    }

    /**
     * Creates the {@link Parcel}.
     *
     * @param text the text
     * @return the {@link Parcel} id
     */
    @Transactional
    public int create(String text) {

        final Parcel parcel = new Parcel(text);
        entityManager.persist(parcel);
        report.add("Created parcel: id[%d], text[%s].".formatted(parcel.getId(), parcel.getText()));
        auditorBean.recordCreated();
        if (logger.isLoggable(Level.INFO)) {
            logger.info("create(): parcel: id[%d], text[%s].".formatted(parcel.getId(), parcel.getText()));
        }
        return parcel.getId();
    }

    /**
     * Reads the {@link Parcel} list.
     */
    public void read() {

        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Parcel> criteriaQuery = criteriaBuilder.createQuery(Parcel.class);
        criteriaQuery.from(Parcel.class);
        final TypedQuery<Parcel> typedQuery = entityManager.createQuery(criteriaQuery);
        final List<Parcel> parcelList = typedQuery.getResultList();
        for (Parcel parcel : parcelList) {
            report.add("parcel: id[%d], text[%s].".formatted(parcel.getId(), parcel.getText()));
        }
        if (logger.isLoggable(Level.INFO)) {
            logger.info("read(): parcelList size[%d]".formatted(parcelList.size()));
        }
    }
}