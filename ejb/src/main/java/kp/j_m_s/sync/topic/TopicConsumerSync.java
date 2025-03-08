package kp.j_m_s.sync.topic;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.jms.Topic;
import kp.j_m_s.sync.ConsumerSync;

import java.util.logging.Logger;

/**
 * JMS messages synchronous <b>topic consumer</b>.
 */
@Named
@RequestScoped
public class TopicConsumerSync extends ConsumerSync {
    private Logger logger;

    @Resource(lookup = "jms/StudyTopicSync")
    private Topic topic;

    /**
     * Default constructor.
     */
    public TopicConsumerSync() {
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
     * Receives the topic messages.
     *
     * @return the result
     */
    public String receiveTopicMessages() {
        receiveMessages(topic, "topic");
        logger.info("receiveTopicMessages():");
        return "";
    }
}