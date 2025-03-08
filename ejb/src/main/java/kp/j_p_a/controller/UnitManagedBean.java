package kp.j_p_a.controller;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.transaction.Transactional;
import kp.j_p_a.domain.units.Side;
import kp.j_p_a.domain.units.Unit;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

/**
 * The CDI managed bean for the {@link Unit}.
 */
@Named
@RequestScoped
public class UnitManagedBean {
    private Logger logger;
    private List<String> report;
    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;

    /**
     * Default constructor.
     */
    public UnitManagedBean() {
        // constructor is empty
    }

    /**
     * Parameterized constructor.
     *
     * @param logger        the {@link Logger}
     * @param report        the report
     * @param entityManager the {@link EntityManager}
     */
    @Inject
    public UnitManagedBean(Logger logger, List<String> report, EntityManager entityManager) {
        this();
        this.logger = logger;
        this.report = report;
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    /**
     * Creates, reads, and deletes the {@link Unit}s.
     *
     * @return the result
     */
    @Transactional
    public String createReadDeleteUnits() {

        report.clear();
        final long count = reportCounts();
        if (count == 0) {
            createUnits();
        } else {
            readAndDeleteUnits();
        }
        reportCounts();
        return "";
    }

    /**
     * Reports the counts of the {@link Unit}s.
     *
     * @return the count
     */
    private long reportCounts() {

        final CriteriaQuery<Tuple> tupleCriteria = criteriaBuilder.createTupleQuery();
        tupleCriteria.select(criteriaBuilder.tuple(
                criteriaBuilder.countDistinct(tupleCriteria.from(Unit.class)),
                criteriaBuilder.countDistinct(tupleCriteria.from(Side.class))));

        final Tuple tuple = entityManager.createQuery(tupleCriteria).getSingleResult();
        report.add("Total units count[%s], total sides count[%s]".formatted(tuple.get(0), tuple.get(1)));
        return (long) tuple.get(0);
    }

    /**
     * Creates the {@link Unit}s.
     */
    private void createUnits() {

        final Unit unitA = new Unit("A");
        final Unit unitB = new Unit("B", unitA);
        final Unit unitC = new Unit("C", unitB);

        unitC.addChild(new Unit("X"));
        unitC.addChild(new Unit("Y"));
        unitC.addChild(new Unit("Z"));

        report.add("Before Persist");
        entityManager.persist(unitA);
        entityManager.persist(unitB);
        entityManager.persist(unitC);
        report.add("After Persist");
        logger.info("createUnits():");
    }

    /**
     * Reads and deletes the {@link Unit}s.
     */
    private void readAndDeleteUnits() {

        final Unit unitA = entityManager.find(Unit.class, "A");
        final Unit unitB = unitA.getNext();
        final Unit unitC = unitB.getNext();
        final Iterator<Unit> iterator = unitC.getChildren().iterator();
        final Unit unitX = iterator.next();
        final Unit unitY = iterator.next();
        final Unit unitZ = iterator.next();

        report.add(unitA.toString());
        report.add(unitB.toString());
        report.add(unitC.toString());
        report.add(unitX.toString());
        report.add(unitY.toString());
        report.add(unitZ.toString());

        report.add("Before Remove");
        entityManager.remove(unitA);
        report.add("After Remove");
        logger.info("readAndDeleteUnits():");
    }
}