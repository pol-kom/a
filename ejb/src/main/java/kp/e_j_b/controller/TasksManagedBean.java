package kp.e_j_b.controller;

import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.enterprise.concurrent.ManagedExecutorService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import kp.Constants;
import kp.e_j_b.Common;
import kp.e_j_b.remote.Sing;
import kp.e_j_b.remote.StaFu;
import kp.e_j_b.remote.StaLe;
import kp.util.Tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The CDI managed bean for the tasks.
 */
@Named
@ApplicationScoped
@Singleton
public class TasksManagedBean {

    @Resource(name = "java:comp/DefaultManagedExecutorService")
    private ManagedExecutorService executorService;
    /*
     * STATELESS BEANS
     */
    @EJB
    private StaLe slb1;
    @EJB
    private StaLe slb2;
    @EJB
    private StaLe slb3;
    @EJB
    private StaLe slb4;
    @EJB
    private StaLe slb5;
    /*
     * SINGLETON BEANS
     */
    @EJB
    private Sing sgb1;
    @EJB
    private Sing sgb2;
    @EJB
    private Sing sgb3;
    @EJB
    private Sing sgb4;
    @EJB
    private Sing sgb5;
    /*
     * STATEFUL BEANS
     */
    @EJB
    private StaFu sfb1;
    @EJB
    private StaFu sfb2;
    @EJB
    private StaFu sfb3;
    @EJB
    private StaFu sfb4;
    @EJB
    private StaFu sfb5;

    private List<String> report;

    private static final List<String> MUTABLE_TEXT_LIST = new ArrayList<>(Tools.getTextList());

    /**
     * Default constructor.
     */
    public TasksManagedBean() {
        // constructor is empty
    }

    /**
     * Parameterized constructor.
     *
     * @param report the report
     */
    @Inject
    public TasksManagedBean(List<String> report) {
        this();
        this.report = report;
    }

    /**
     * Researches the <b>stateless</b> beans.
     *
     * @return the result
     */
    public String researchStateless() {

        submitTasks(new Common[]{slb1, slb2, slb3, slb4, slb5}, "*** Stateless session beans: ");
        final String message = ("*** Stateless session beans injected objects hash codes: " +
                                "slb1[%s], slb2[%s], slb3[%s], slb4[%s], slb5[%s]").formatted(
                Tools.hashCodeFormatted(slb1), Tools.hashCodeFormatted(slb2), Tools.hashCodeFormatted(slb3),
                Tools.hashCodeFormatted(slb4), Tools.hashCodeFormatted(slb5));
        report.add(message);
        report.add(Constants.LINE);
        return "";
    }

    /**
     * Researches the <b>singleton</b> beans.
     *
     * @return the result
     */
    public String researchSingleton() {

        submitTasks(new Common[]{sgb1, sgb2, sgb3, sgb4, sgb5}, "*** Singleton session beans: ");
        final String message = ("*** Singleton session beans injected objects hash codes: " +
                                "sgb1[%s], sgb2[%s], sgb3[%s], sgb4[%s], sgb5[%s]").formatted(
                Tools.hashCodeFormatted(sgb1), Tools.hashCodeFormatted(sgb2), Tools.hashCodeFormatted(sgb3),
                Tools.hashCodeFormatted(sgb4), Tools.hashCodeFormatted(sgb5));
        report.add(message);
        report.add(Constants.LINE);
        return "";
    }

    /**
     * Researches the <b>stateful</b> beans.
     *
     * @return the result
     */
    public String researchStateful() {

        submitTasks(new Common[]{sfb1, sfb2, sfb3, sfb4, sfb5}, "*** Stateful session beans: ");
        final String message = ("*** Stateful session beans injected objects hash codes: " +
                                "sfb1[%s], sfb2[%s], sfb3[%s], sfb4[%s], sfb5[%s]").formatted(
                Tools.hashCodeFormatted(sfb1), Tools.hashCodeFormatted(sfb2), Tools.hashCodeFormatted(sfb3),
                Tools.hashCodeFormatted(sfb4), Tools.hashCodeFormatted(sfb5));
        report.add(message);
        report.add(Constants.LINE);
        return "";
    }

    /**
     * Submits the tasks to the {@link ManagedExecutorService}.
     *
     * @param sbArr the session beans array
     * @param label the label
     */
    private void submitTasks(Common[] sbArr, String label) {

        final List<String> list = Collections.synchronizedList(new ArrayList<>());
        final String stamp = MUTABLE_TEXT_LIST.getFirst();
        Collections.rotate(MUTABLE_TEXT_LIST, -1);

        executorService.submit(() -> list.add(sbArr[0].change(stamp, 1)));
        executorService.submit(() -> list.add(sbArr[1].change(stamp, 2)));
        executorService.submit(() -> list.add(sbArr[2].change(stamp, 3)));
        executorService.submit(() -> list.add(sbArr[3].change(stamp, 4)));
        executorService.submit(() -> list.add(sbArr[4].change(stamp, 5)));

        addToReport(label, list);
    }

    /**
     * Adds to the report.
     *
     * @param label the label
     * @param list  the list
     */
    private void addToReport(String label, List<String> list) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Preserve interrupt status
        }
        list.sort(null);
        report.add(label);
        report.addAll(list);
    }
}