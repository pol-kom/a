package kp.util;

import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.inject.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Resources producer.
 */
public class Resources {

    /**
     * Produces the {@link Logger}.
     *
     * @param injectionPoint the {@link InjectionPoint}
     * @return logger the {@link Logger}
     */
    @Produces
    public Logger produceLogger(InjectionPoint injectionPoint) {
        final String category = injectionPoint.getMember().getDeclaringClass().getName();
        return Logger.getLogger(category);
    }

    /**
     * Produces the report list.
     */
    @Named
    @Produces
    protected static final List<String> report = new ArrayList<>();
}