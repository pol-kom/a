package kp.cdi.alternatives;

import jakarta.inject.Inject;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The implementation of the {@link BasicBean} with Qualifier {@link Script}.
 * <p>
 * Uses the <i>Unicode Mathematical Alphanumeric Symbols</i>.
 * </p>
 */
@Script
public class BasicBeanImplScript implements BasicBean {
    private final Logger logger;
    private final List<List<String>> report;

    /**
     * Parameterized constructor.
     *
     * @param logger the {@link Logger}
     * @param report the report list
     */
    @Inject
    public BasicBeanImplScript(Logger logger, List<List<String>> report) {
        this.logger = logger;
        this.report = report;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show(String content) {

        final String asScript = content.toUpperCase()
                .replace("A", "𝒜").replace("B", "ℬ").replace("C", "𝒞")
                .replace("D", "𝒟").replace("E", "ℰ").replace("F", "ℱ")
                .replace("G", "𝒢").replace("H", "ℋ").replace("I", "ℐ")
                .replace("J", "𝒥").replace("K", "𝒦").replace("L", "ℒ")
                .replace("M", "ℳ").replace("N", "𝒩").replace("O", "𝒪")
                .replace("P", "𝒫").replace("Q", "𝒬").replace("R", "ℛ")
                .replace("S", "𝒮").replace("T", "𝒯").replace("U", "𝒰")
                .replace("V", "𝒱").replace("W", "𝒲").replace("X", "𝒳")
                .replace("Y", "𝒴").replace("Z", "𝒵");

        report.add(List.of(BasicBeanImplScript.class.getSimpleName(), "show", "content[%s]".formatted(asScript)));
        if (logger.isLoggable(Level.INFO)) {
            logger.info("show(): content[%s]".formatted(content));
        }
    }
}