package kp.j_m_s.async.topic;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.inject.Inject;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import kp.j_m_s.async.ConsumerAsync;

import java.util.logging.Logger;

/**
 * A message-driven bean servicing the <b>topic</b>.
 */
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/StudyTopicAsync"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Topic")})
public class TopicConsumerMDB extends ConsumerAsync implements MessageListener {
    private Logger logger;

    /**
     * Default constructor.
     */
    public TopicConsumerMDB() {
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
        process(message, "topic");
        logger.info("onMessage():");
    }
}