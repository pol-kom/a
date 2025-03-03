package kp.cdi;

import jakarta.enterprise.event.Event;
import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.util.AnnotationLiteral;
import jakarta.inject.Inject;
import kp.cdi.alternatives.BasicBean;
import kp.cdi.alternatives.Script;
import kp.cdi.decorators.PlainBean;
import kp.cdi.events.Payload;
import kp.cdi.interceptors.ElapsedBean;
import kp.cdi.producers.Foreseeable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import static kp.cdi.producers.ForeseeableDate.NEAR;

/**
 * The bean for CDI research.
 */
public class ResearchCDIBean {
    private final Logger logger;
    private final List<List<String>> report;
    private final ResearchCDIHelper researchCDIHelper;
    private final BasicBean basicBean;
    private final BasicBean basicBeanWithQualifier;
    private final Instance<BasicBean> basicBeanInstance;
    private final PlainBean plainBean;
    private final Event<Payload> payloadEvent;
    private final Event<List<Payload>> payloadListEvent;
    private final Event<List<String>> textListEvent;
    private final String foreseeableF;
    private final String foreseeableN;
    private final Instance<String> foreseeableInstance;
    private final ElapsedBean elapsedBean;

    @SuppressWarnings(value = {"all"})
    private static class ScriptQualifier extends AnnotationLiteral<Script> implements Script {
    }

    private static final String CLASS_NAME = ResearchCDIBean.class.getSimpleName();
    private static final List<String> EMPTY_ROW = Arrays.asList(new String[3]);

