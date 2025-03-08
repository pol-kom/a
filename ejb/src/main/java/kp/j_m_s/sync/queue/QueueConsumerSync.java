package kp.j_m_s.sync.queue;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.jms.Queue;
import kp.j_m_s.sync.ConsumerSync;

import java.util.logging.Logger;

/**
 * The JMS messages synchronous <b>queue consumer</b>.
 */
@Named
@RequestScoped
public class QueueConsumerSync extends ConsumerSync {
    private Logger logger;

    @Resource(lookup = "jms/StudyQueueSync")
    private Queue queue;

    /**
     * Default constructor.
     */
    public QueueConsumerSync() {
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
     * Receives queue messages.
     *
     * @return the result
     */
    public String receiveQueueMessages() {
        receiveMessages(queue, "queue");
        logger.info("receiveQueueMessages():");
        return "";
    }
}