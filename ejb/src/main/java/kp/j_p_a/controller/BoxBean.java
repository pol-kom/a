package kp.j_p_a.controller;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.EJBContext;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import kp.Constants;
import kp.j_p_a.domain.boxes.*;
import kp.j_p_a.domain.components.TermDates;
import kp.util.Tools;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The stateless session bean for Box.
 * <p>
 * Uses container-managed transactions.
 * </p>
 */
@Named
@Stateless
public class BoxBean {
    private Logger logger;
    private List<String> report;
    @Resource
    private EJBContext ejbContext;
    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;

    private static final boolean USE_CRITERIA = true;
    private static final Class<?>[] BOX_CLASSES_ARR = {CentralBox.class, UpperBox.class, SingleBox.class,
            MultiBox.class, LowerBox.class};
    private static final List<String> MUTABLE_TEXT_LIST = new ArrayList<>(Tools.getTextList());
    private static final String IDENTIFIER = "identifier";

    /**
     * Default constructor.
     */
    public BoxBean() {
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
    public BoxBean(Logger logger, List<String> report, EntityManager entityManager) {
        this();
        this.logger = logger;
        this.report = report;
        this.entityManager = entityManager;
    }

    /**
     * Initializes the bean.
     */
    @PostConstruct
    private void init() {
        criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    /**
     * Creates the {@link CentralBox}.
     * <p>
     * This method uses bean-managed transaction.
     * </p>
     *
     * @return the result
     */
    public String createCentralBox() {

        report.clear();
        try {
            final CentralBox centralBox = createAndFillCentralBox();
            if (getNumberOfCentralBoxes() > 3) {
                // simulate validation error
                ejbContext.setRollbackOnly();
                final String msg = "The maximum number of boxes is 3. The creating was aborted and the transaction was marked for rollback.";
                report.add(msg);
                if (logger.isLoggable(Level.WARNING)) {
                    logger.warning("createCentralBox(): %s".formatted(msg));
                }
                return "";
            }
            loadLinesFromBox(centralBox);
        } catch (Exception e) {
            logger.severe("createCentralBox(): exception[%s]".formatted(e.getMessage()));
            return "";
        }
        logger.info("createCentralBox():");
        return "";
    }

    /**
     * Reads the {@link CentralBox} list.
     *
     * @return the result
     */
    public String readCentralBoxes() {

        report.clear();
        showBoxesCount();
        final List<CentralBox> centralBoxList = findCentralBoxes();
        if (centralBoxList.isEmpty()) {
            report.add(Constants.EMPTY);
            logger.info("readCentralBoxes(): empty");
            return "";
        }
        centralBoxList.forEach(centralBox -> {
            report.add(Constants.LINE);
            loadLinesFromBox(centralBox);
        });
        if (logger.isLoggable(Level.INFO)) {
            logger.info("readCentralBoxes(): centralBoxList size[%d]".formatted(centralBoxList.size()));
        }
        return "";
    }

    /**
     * Updates the {@link CentralBox} list.
     * <p>
     * This method uses bean-managed transaction.
     * </p>
     *
     * @return the result
     */
    public String updateCentralBoxes() {

        report.clear();
        showBoxesCount();
        if (getNumberOfCentralBoxes() < 1) {
            report.add(Constants.EMPTY);
            logger.info("updateCentralBoxes(): nothing to update");
            return "";
        }
        List<CentralBox> centralBoxList;
        try {
            centralBoxList = findCentralBoxes();
            centralBoxList.forEach(centralBox -> {
                centralBox.setCardinalDirection(centralBox.getCardinalDirection().getNext());
                entityManager.merge(centralBox);
            });
        } catch (Exception e) {
            logger.severe("updateCentralBoxes(): exception[%s]".formatted(e.getMessage()));
            return "";
        }
        centralBoxList.forEach(centralBox -> {
            report.add(Constants.LINE);
            loadLinesFromBox(centralBox);
        });
        logger.info("updateCentralBoxes():");
        return "";
    }

    /**
     * Deletes the {@link CentralBox}.
     *
     * @return the result
     */
    public String deleteCentralBox() {

        report.clear();
        if (getNumberOfCentralBoxes() < 1) {
            showBoxesCount();
            report.add(Constants.EMPTY);
            logger.info("deleteCentralBox(): nothing to delete");
            return "";
        }
        final CentralBox centralBox = findCentralBoxes().getLast();
        clearMultiplicityBeforeRemove(centralBox);
        try {
            entityManager.remove(centralBox);
        } catch (Exception e) {
            logger.severe("deleteCentralBox(): exception[%s]".formatted(e.getMessage()));
            return "";
        }
        showBoxesCount();
        logger.info("deleteCentralBox():");
        return "";
    }

    /**
     * Creates and fills the {@link CentralBox}.
     *
     * @return the {@link CentralBox}.
     */
    private CentralBox createAndFillCentralBox() {

        final CentralBox centralBox = new CentralBox();
        entityManager.persist(centralBox);
        centralBox.setText(MUTABLE_TEXT_LIST.getFirst());
        centralBox.setTermDates(new TermDates());

        addUpperBox(centralBox);

        final String label = "created by %s".formatted(MUTABLE_TEXT_LIST.getFirst());

        final SingleBox singleBox = new SingleBox();
        singleBox.setText(label);
        singleBox.setCentralBox(centralBox);
        centralBox.setSingleBox(singleBox);

        createMultiplicity(centralBox, label);

        for (int i = 1; i <= 2; i++) {
            final LowerBox lowerBox = new LowerBox();
            lowerBox.setText("%s - item %d".formatted(label, i));
            centralBox.addLowerBox(lowerBox);
        }
        entityManager.merge(centralBox);
        Collections.rotate(MUTABLE_TEXT_LIST, -1);
        return centralBox;
    }

    /**
     * Adds the {@link UpperBox}.
     *
     * @param centralBox the {@link CentralBox}.
     */
    private void addUpperBox(CentralBox centralBox) {

        final CriteriaQuery<UpperBox> upperBoxCriteria = criteriaBuilder.createQuery(UpperBox.class);
        upperBoxCriteria.select(upperBoxCriteria.from(UpperBox.class));
        final TypedQuery<UpperBox> upperBoxQuery = entityManager.createQuery(upperBoxCriteria);
        final List<UpperBox> upperBoxList = upperBoxQuery.getResultList();
        final UpperBox upperBox;
        if (upperBoxList.isEmpty()) {
            upperBox = new UpperBox();
            upperBox.setText("root");
        } else {
            upperBox = upperBoxList.getFirst();
        }
        upperBox.addCentralBox(centralBox);
    }

    /**
     * Creates the multiplicity.
     *
     * @param centralBox the {@link CentralBox}
     * @param label      the label
     */
    private void createMultiplicity(CentralBox centralBox, String label) {

        final MultiBox multiBox = new MultiBox();
        multiBox.setText(label);

        final CriteriaQuery<CentralBox> centralBoxCriteria = criteriaBuilder.createQuery(CentralBox.class);
        centralBoxCriteria.select(centralBoxCriteria.from(CentralBox.class));
        final TypedQuery<CentralBox> centralBoxQuery = entityManager.createQuery(centralBoxCriteria);
        final List<CentralBox> centralBoxList = centralBoxQuery.getResultList();
        centralBoxList.forEach(cb -> cb.addMultiBox(multiBox));

        final CriteriaQuery<MultiBox> multiBoxCriteria = criteriaBuilder.createQuery(MultiBox.class);
        multiBoxCriteria.select(multiBoxCriteria.from(MultiBox.class));
        final TypedQuery<MultiBox> multiBoxQuery = entityManager.createQuery(multiBoxCriteria);
        final List<MultiBox> multiBoxList = multiBoxQuery.getResultList();
        multiBoxList.forEach(centralBox::addMultiBox);
    }

    /**
     * Finds the {@link CentralBox} list.
     *
     * @return the centralBoxList
     */
    private List<CentralBox> findCentralBoxes() {

        TypedQuery<CentralBox> query;
        if (USE_CRITERIA) {
            final CriteriaQuery<CentralBox> centralBoxCriteria = criteriaBuilder.createQuery(CentralBox.class);
            final Root<CentralBox> centralBoxRoot = centralBoxCriteria.from(CentralBox.class);
            centralBoxRoot.fetch("multiBoxes");
            centralBoxRoot.fetch("lowerBoxes");
            final Order order = criteriaBuilder.asc(centralBoxRoot.get(IDENTIFIER));
            centralBoxCriteria.select(centralBoxRoot).distinct(true).orderBy(order);
            query = entityManager.createQuery(centralBoxCriteria);
        } else {
            query = entityManager.createNamedQuery("CentralBox.findAll", CentralBox.class);
        }
        return query.getResultList();
    }

    /**
     * Clears the multiplicity before remove.
     *
     * @param centralBox the {@link CentralBox}
     */
    private void clearMultiplicityBeforeRemove(CentralBox centralBox) {

        new ArrayList<>(centralBox.getMultiBoxes()).forEach(centralBox::removeMultiBox);

        final CriteriaQuery<MultiBox> multiBoxCriteria = criteriaBuilder.createQuery(MultiBox.class);
        multiBoxCriteria.select(multiBoxCriteria.from(MultiBox.class));
        final TypedQuery<MultiBox> multiBoxQuery = entityManager.createQuery(multiBoxCriteria);
        final List<MultiBox> multiBoxList = multiBoxQuery.getResultList();

        final CriteriaQuery<CentralBox> centralBoxCriteria = criteriaBuilder.createQuery(CentralBox.class);
        centralBoxCriteria.select(centralBoxCriteria.from(CentralBox.class));
        final TypedQuery<CentralBox> centralBoxQuery = entityManager.createQuery(centralBoxCriteria);
        final List<CentralBox> centralBoxList = centralBoxQuery.getResultList();

        for (MultiBox multiBox : multiBoxList) {
            if (!multiBox.getCentralBoxes().isEmpty()) {
                continue;
            }
            // remove empty multi box
            centralBoxList.forEach(cb -> cb.removeMultiBox(multiBox));
            entityManager.remove(multiBox);
        }
    }

    /**
     * Gets the {@link CentralBox} number.
     *
     * @return the number
     */
    private long getNumberOfCentralBoxes() {

        final CriteriaQuery<Long> countCriteria = criteriaBuilder.createQuery(Long.class);
        countCriteria.select(criteriaBuilder.countDistinct(countCriteria.from(CentralBox.class)));
        final TypedQuery<Long> countQuery = entityManager.createQuery(countCriteria);
        return countQuery.getSingleResult();
    }

    /**
     * Loads lines from the box.
     *
     * @param centralBox the {@link CentralBox}
     */
    private void loadLinesFromBox(CentralBox centralBox) {

        report.add("centralBox: identifier[%d], text[%s], cardinalDirection[%s]".formatted(
                centralBox.getIdentifier(), centralBox.getText(), centralBox.getCardinalDirection()));
        final TermDates termDates = centralBox.getTermDates();
        report.add("localDate[%s], localDateTime[%s]".formatted(
                termDates.getLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                termDates.getLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
        /*
         * Many-To-One
         */
        final UpperBox upperBox = centralBox.getUpperBox();
        if (Objects.nonNull(upperBox)) {
            report.add("'Many-To-One' upperBox: identifier[%d], text[%s]".formatted(
                    upperBox.getIdentifier(), upperBox.getText()));
        } else {
            report.add("'Many-To-One' NULL upperBox");
        }
        /*
         * One-To-One
         */
        final SingleBox singleBox = centralBox.getSingleBox();
        if (Objects.nonNull(singleBox)) {
            report.add("'One-To-One' singleBox: identifier[%d], text[%s]".formatted(
                    singleBox.getIdentifier(), singleBox.getText()));
        } else {
            report.add("'One-To-One' NULL singleBox");
        }
        /*
         * Many-To-Many
         */
        final Set<MultiBox> multiBoxes = centralBox.getMultiBoxes();
        if (!multiBoxes.isEmpty()) {
            multiBoxes.forEach(multiBox -> report.add(
                    "'Many-To-Many' multiBox: identifier[%d], text[%s]".formatted(
                            multiBox.getIdentifier(), multiBox.getText())));
        } else {
            report.add("'Many-To-Many' EMPTY multiBoxes");
        }
        /*
         * One-To-Many
         */
        final Set<LowerBox> lowerBoxes = centralBox.getLowerBoxes();
        if (!lowerBoxes.isEmpty()) {
            lowerBoxes.forEach(lowerBox -> report.add(
                    "'One-To-Many' lowerBox: identifier[%d], text[%s]".formatted(
                            lowerBox.getIdentifier(), lowerBox.getText())));
        } else {
            report.add("'One-To-Many' EMPTY lowerBoxes");
        }
    }

    /**
     * Shows the boxes count.
     */
    private void showBoxesCount() {

        final Long[] countArr = new Long[BOX_CLASSES_ARR.length];
        for (int i = 0; i < BOX_CLASSES_ARR.length; i++) {
            final CriteriaQuery<Long> countCriteria = criteriaBuilder.createQuery(Long.class);
            countCriteria.select(criteriaBuilder.countDistinct(countCriteria.from(BOX_CLASSES_ARR[i])));
            final TypedQuery<Long> countQuery = entityManager.createQuery(countCriteria);
            countArr[i] = countQuery.getSingleResult();
        }
        report.add(("Total count: CentralBoxes[%d], UpperBoxes[%d], SingleBoxes[%d], MultiBoxes[%d], LowerBoxes[%d]")
                .formatted(countArr[0], countArr[1], countArr[2], countArr[3], countArr[4]));
    }
}
