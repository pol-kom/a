package kp.j_m_s.reply;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.inject.Inject;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A message-driven bean servicing the <b>reply queue</b>.
 */
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/StudyReplyQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue")})
public class ReplyQueueMDB implements MessageListener {
    private Logger logger;
    private List<String> report;

    /**
     * Default constructor.
     */
    public ReplyQueueMDB() {
        // constructor is empty
    }

    /**
     * Sets the logger.
     *
     * @param logger the logger
     */
    @Inject
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    /**
     * Sets the report.
     *
     * @param report the report
     */
    @Inject
    public void setReport(List<String> report) {
        this.report = report;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onMessage(Message message) {

        String label;
        try {
            label = message.getStringProperty("label");
        } catch (JMSException e) {
            logger.severe("onMessage(): JMSException[%s]".formatted(e.getMessage()));
            return;
        }
        report.add("'reply queue' received CONTROL message, label[%s]".formatted(label));
        if (logger.isLoggable(Level.INFO)) {
            logger.info("onMessage(): label[%s]".formatted(label));
        }
    }
}