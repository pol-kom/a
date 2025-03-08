package kp.j_m_s.async.queue;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.jms.Queue;
import kp.j_m_s.Producer;

import java.util.logging.Logger;

/**
 * JMS messages <b>queue producer</b> (for asynchronous consumer).
 */
@Named
@RequestScoped
public class QueueProducerAsync extends Producer {
    private Logger logger;

    @Resource(lookup = "jms/StudyQueueAsync")
    private Queue queue;

    /**
     * Default constructor.
     */
    public QueueProducerAsync() {
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
     * Sends queue messages.
     *
     * @return the result
     */
    public String sendQueueMessages() {
        sendMessages(queue, "queue");
        logger.info("sendQueueMessages():");
        return "";
    }
}