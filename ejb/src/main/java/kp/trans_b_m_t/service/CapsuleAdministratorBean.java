package kp.trans_b_m_t.service;

import jakarta.ejb.Stateful;
import jakarta.ejb.TransactionManagement;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Status;
import jakarta.transaction.SystemException;
import jakarta.transaction.UserTransaction;
import kp.Constants;
import kp.trans_b_m_t.domain.Capsule;
import kp.util.Tools;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static jakarta.ejb.TransactionManagementType.BEAN;

/**
 * The {@link Capsule} administrator <b>stateful</b> session bean.
 * <p>
 * Using the <b>bean-managed</b> transaction management.
 * </p>
 */
@Stateful
@TransactionManagement(BEAN)
public class CapsuleAdministratorBean implements CapsuleAdministrator {
    @Serial
    private static final long serialVersionUID = 1L;
    private final transient Logger logger;
    /**
     * The report.
     */
    private final List<String> report;
    private final transient EntityManager entityManager;
    private final transient UserTransaction userTransaction;

    private static final List<String> MUTABLE_TEXT_LIST = new ArrayList<>(Tools.getTextList());

    /**
     * Parameterized constructor.
     *
     * @param logger          the  {@link Logger}
     * @param report          the report
     * @param entityManager   the {@link EntityManager}
     * @param userTransaction the {@link UserTransaction}
     */
    @Inject
    public CapsuleAdministratorBean(Logger logger, List<String> report, EntityManager entityManager,
                                    UserTransaction userTransaction) {
        this.logger = logger;
        this.report = report;
        this.entityManager = entityManager;
        this.userTransaction = userTransaction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void create() {
        try {
            if (userTransaction.getStatus() == Status.STATUS_NO_TRANSACTION) {
                userTransaction.begin();
                logger.info("create(): user transaction begin");
            }
            final String text = MUTABLE_TEXT_LIST.getFirst();
            Collections.rotate(MUTABLE_TEXT_LIST, -1);
            final Capsule capsule = new Capsule(text);
            entityManager.persist(capsule);
            report.add("Created capsule: id[%d], text[%s].".formatted(capsule.getId(), capsule.getText()));
            if (logger.isLoggable(Level.INFO)) {
                logger.info("create(): capsule: id[%d], text[%s].".formatted(capsule.getId(), capsule.getText()));
            }
        } catch (Exception e) {
            logger.severe("create(): exception[%s]".formatted(e.getMessage()));
            rollback();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void read() {

        try {
            if (userTransaction.getStatus() == Status.STATUS_NO_TRANSACTION) {
                userTransaction.begin();
                logger.info("read(): user transaction begin");
            }
            final List<Capsule> capsuleList = loadCapsuleList();
            if (capsuleList.isEmpty()) {
                report.add(Constants.EMPTY);
            } else {
                capsuleList.forEach(capsule -> report.add(
                        "capsule: id[%d], text[%s].".formatted(capsule.getId(), capsule.getText())));
            }
            if (logger.isLoggable(Level.INFO)) {
                logger.info("read(): capsuleList size[%d]".formatted(capsuleList.size()));
            }
        } catch (Exception e) {
            logger.severe("read(): exception[%s]".formatted(e.getMessage()));
            rollback();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete() {

        try {
            if (userTransaction.getStatus() == Status.STATUS_NO_TRANSACTION) {
                userTransaction.begin();
                logger.info("delete(): user transaction begin");
            }
            final List<Capsule> capsuleList = loadCapsuleList();
            if (capsuleList.isEmpty()) {
                report.add(Constants.EMPTY);
                logger.info("delete(): empty capsuleList");
                return;
            }
            // delete last in list
            final Capsule capsule = capsuleList.getLast();
            final int id = capsule.getId();
            final String text = capsule.getText();

            entityManager.remove(capsule);
            entityManager.flush();
            report.add("Deleted capsule: id[%d], text[%s].".formatted(id, text));
            if (logger.isLoggable(Level.INFO)) {
                logger.info("delete(): deleted capsule: id[%d], text[%s].".formatted(id, text));
            }
        } catch (Exception e) {
            logger.severe("delete(): exception[%s]".formatted(e.getMessage()));
            rollback();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void commit() {

        try {
            if (userTransaction.getStatus() == Status.STATUS_ACTIVE) {
                userTransaction.commit();
                report.add("%s COMMIT".formatted(Constants.LINE));
            }
        } catch (Exception e) {
            logger.severe("commit(): exception[%s]".formatted(e.getMessage()));
            return;
        }
        logger.info("commit():");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void rollback() {

        try {
            if (userTransaction.getStatus() == Status.STATUS_ACTIVE) {
                userTransaction.rollback();
                report.add("%s ROLLBACK".formatted(Constants.LINE));
            }
        } catch (SystemException e) {
            logger.severe("rollback(): exception[%s]".formatted(e.getMessage()));
            return;
        }
        logger.info("rollback():");
    }

    /**
     * Loads the {@link Capsule} list.
     *
     * @return the {@link Capsule} list
     */
    private List<Capsule> loadCapsuleList() {

        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Capsule> criteriaQuery = criteriaBuilder.createQuery(Capsule.class);
        final Root<Capsule> capsuleRoot = criteriaQuery.from(Capsule.class);
        final Order order = criteriaBuilder.asc(capsuleRoot.get("id"));
        criteriaQuery.select(capsuleRoot).orderBy(order);
        final TypedQuery<Capsule> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }
}
