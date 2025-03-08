package kp.util;

import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Resources producer.
 */
public class Resources {
    /**
     * Produces the logger.
     *
     * @param injectionPoint the {@link InjectionPoint}
     * @return the logger
     */
    @Produces
    public Logger produceLogger(InjectionPoint injectionPoint) {
        final String category = injectionPoint.getMember().getDeclaringClass().getName();
        return Logger.getLogger(category);
    }

    /**
     * Produces the report.
     */
    @Named
    @Produces
    protected static final List<String> report = new ArrayList<>();

    /**
     * Produces the {@link EntityManager}.
     */
    @PersistenceContext
    @Produces
    private EntityManager entityManager;
}