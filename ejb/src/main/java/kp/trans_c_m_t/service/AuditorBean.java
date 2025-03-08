package kp.trans_c_m_t.service;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.transaction.Transactional;
import kp.trans_c_m_t.domain.Audit;

import java.util.List;

import static jakarta.ejb.TransactionAttributeType.REQUIRES_NEW;

/**
 * The <b>stateless</b> session bean for the <b>auditor</b>.
 * <p>
 * With the 'RequiresNew' attribute the container suspends the current transaction and starts a new transaction.
 * Java EE transaction manager does not support nested transactions.
 * </p>
 */
@Stateless
public class AuditorBean {
    private List<String> report;
    private EntityManager entityManager;

    /**
     * Default constructor.
     */
    public AuditorBean() {
        // empty constructor
    }

    /**
     * Parameterized constructor.
     *
     * @param report        the report
     * @param entityManager the {@link EntityManager}
     */
    @Inject
    public AuditorBean(List<String> report, EntityManager entityManager) {
        this();
        this.report = report;
        this.entityManager = entityManager;
    }

    /**
     * Records the state 'created'.
     * <p>
     * The parcel status: created but not approved yet.
     * </p>
     */
    @TransactionAttribute(REQUIRES_NEW)
    public void recordCreated() {

        final Audit audit = loadAudit();
        audit.setCreated(audit.getCreated() + 1);
    }

    /**
     * Records the state 'approved'.
     * <p>
     * The parcel status: created and approved.
     * </p>
     */
    @TransactionAttribute(REQUIRES_NEW)
    public void recordApproved() {

        final Audit audit = loadAudit();
        audit.setApproved(audit.getApproved() + 1);
    }

    /**
     * Displays the {@link Audit}.
     */
    public void showAudit() {

        final Audit audit = loadAudit();
        report.add("Audit: parcels created[%d], parcels approved[%d]."
                .formatted(audit.getCreated(), audit.getApproved()));
    }

    /**
     * Loads the {@link Audit}.
     *
     * @return the {@link Audit}
     */
    @Transactional
    private Audit loadAudit() {

        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Audit> criteriaQuery = criteriaBuilder.createQuery(Audit.class);
        criteriaQuery.from(Audit.class);
        final TypedQuery<Audit> typedQuery = entityManager.createQuery(criteriaQuery);

        final List<Audit> auditList = typedQuery.getResultList();
        if (auditList.isEmpty()) {
            final Audit audit = new Audit();
            entityManager.persist(audit);
            return audit;
        }
        return auditList.getFirst();
    }
}