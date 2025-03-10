package kp.w_s.controller;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import kp.Constants;
import kp.util.KpException;
import kp.util.Tools;
import kp.w_s.WebSe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * The CDI managed bean for <B>JAX-WS</B> research.
 * <p>
 * JAX-WS: Java API for XML-Based Web Services.
 * </p>
 */
@Named
@RequestScoped
public class WsManagedBean {
    private final Logger logger;
    private final List<String> report;

    private static final List<String> MUTABLE_TEXT_LIST = new ArrayList<>(Tools.getTextList());

    /**
     * Parameterized constructor.
     *
     * @param logger the {@link Logger}
     * @param report the report
     */
    @Inject
    public WsManagedBean(Logger logger, List<String> report) {
        this.logger = logger;
        this.report = report;
    }

    /**
     * Researches web service.
     *
     * @return the result
     */
    public String researchWebService() {

        final String text = MUTABLE_TEXT_LIST.getFirst();
        Collections.rotate(MUTABLE_TEXT_LIST, -1);
        report.add(Constants.LABEL);
        final List<WebSe> webSeList;
        try {
            webSeList = Tools.createWebSeImplementations();
        } catch (KpException ex) {
            logger.severe("researchWebService(): KpException[%s]".formatted(ex.getMessage()));
            return "";
        }
        final StringBuilder strBld = new StringBuilder();
        strBld.append("researchWebService():").append(System.lineSeparator()).append(Constants.BREAK);
        webSeList.forEach(webSe -> strBld.append(webSe.tryIt(text)).append(System.lineSeparator()));
        final String msg = strBld.append(Constants.BREAK).toString();
        logger.info(msg);
        return "";
    }

}