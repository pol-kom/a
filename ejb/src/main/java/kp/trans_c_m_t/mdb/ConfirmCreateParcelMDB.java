package kp.trans_c_m_t.mdb;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.inject.Inject;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Message-driven bean for the <b>parcel</b> creation confirmation.
 */
// SonarQube signals 'duplicated blocks of code'.  But this rule is deprecated in Sonar! 
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(
                propertyName = "destinationLookup", propertyValue = "jms/ConfirmCreateParcelQueue"),
        @ActivationConfigProperty(
                propertyName = "destinationType", propertyValue = "jakarta.jms.Queue")})
public class ConfirmCreateParcelMDB implements MessageListener {
    private Logger logger;
    private List<String> report;

    /**
     * Default constructor.
     */
    public ConfirmCreateParcelMDB() {
        // empty constructor
    }

    /**
     * Parameterized constructor.
     *
     * @param logger the {@link Logger}
     * @param report the report
     */
    @Inject
    public ConfirmCreateParcelMDB(Logger logger, List<String> report) {
        this();
        this.logger = logger;
        this.report = report;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onMessage(Message message) {

        String text;
        try {
            text = ((TextMessage) message).getText();
        } catch (JMSException e) {
            logger.severe("onMessage(): exception[%s]".formatted(e.getMessage()));
            return;
        }
        report.add("ConfirmCreateParcelMDB received text[%s] from 'ConfirmCreateParcelQueue'.".formatted(text));
        if (logger.isLoggable(Level.INFO)) {
            logger.info("onMessage(): text[%s]".formatted(text));
        }
    }
}