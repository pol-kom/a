package kp.e_j_b.controller;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import kp.e_j_b.interceptors.TimeElapsedBean;
import kp.e_j_b.interceptors.TimeElapsedInterceptor;

import java.util.logging.Logger;

/**
 * The CDI managed bean for the {@link TimeElapsedBean} with interceptor {@link TimeElapsedInterceptor}.
 */
@Named
@RequestScoped
public class InterceptorsManagedBean {
    private final Logger logger;

    @EJB
    private static TimeElapsedBean timeElapsedBean;

    /**
     * Parameterized constructor.
     *
     * @param logger the {@link Logger}
     */
    @Inject
    public InterceptorsManagedBean(Logger logger) {
        this.logger = logger;
    }

    /**
     * Researches the interceptor.
     *
     * @return the result
     */
    public String researchInterceptor() {

        timeElapsedBean.pausedMilli();
        timeElapsedBean.pausedNano();
        timeElapsedBean.notPaused();
        logger.info("researchInterceptor():");
        return "";
    }
}