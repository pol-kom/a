package kp.r_s.controller;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.logging.Logger;

/**
 * CDI managed bean for <B>JAX-RS</B> research.
 * <p>
 * JAX-RS: Java API for RESTful Web Services.
 * </p>
 */
@Named
@RequestScoped
public class RsManagedBean {
    private final Logger logger;
    private final List<String> report;
    private static final String SERVICE_ENDPOINT = "http://localhost:8080/Study14/rs/text/";

    /**
     * Parameterized constructor.
     *
     * @param logger the {@link Logger}
     * @param report the report
     */
    @Inject
    public RsManagedBean(Logger logger, List<String> report) {
        this.logger = logger;
        this.report = report;
    }

    /**
     * Researches RESTful web service.
     *
     * @return the result
     */
    public String researchRestfulWebService() {

        try (Client client = ClientBuilder.newClient()) {
            final WebTarget webTarget = client.target(SERVICE_ENDPOINT);
            final Response response = webTarget.request().get();
            report.add("JAX-RS response status[%s]".formatted(response.getStatusInfo()));
            report.add("JAX-RS response entity[%s]".formatted(response.readEntity(String.class)));
        }
        logger.info("researchRestfulWebService():");
        return "";
    }
}