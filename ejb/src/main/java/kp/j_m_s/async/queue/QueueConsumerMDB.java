package kp.j_m_s.async.queue;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.inject.Inject;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import kp.j_m_s.async.ConsumerAsync;

import java.util.logging.Logger;

/**
 * A message-driven bean servicing the <b>queue</b>.
 */
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/StudyQueueAsync"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue")})
public class QueueConsumerMDB extends ConsumerAsync implements MessageListener {
    private Logger logger;

    /**
     * Default constructor.
     */
    public QueueConsumerMDB() {
        // constructor is empty
    }

    /**
     * Sets the logger.
     *
     * @param logger the logger
     */
    @Inject
    @Override
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onMessage(Message message) {
        process(message, "queue");
        logger.info("onMessage():");
    }
}