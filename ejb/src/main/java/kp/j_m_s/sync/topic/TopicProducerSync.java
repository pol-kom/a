package kp.j_m_s.sync.topic;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.jms.Topic;
import kp.j_m_s.Producer;

import java.util.logging.Logger;

/**
 * JMS messages <b>topic producer</b> (for synchronous consumer).
 */
@Named
@RequestScoped
public class TopicProducerSync extends Producer {
    private Logger logger;

    @Resource(lookup = "jms/StudyTopicSync")
    private Topic topic;

    /**
     * Default constructor.
     */
    public TopicProducerSync() {
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
     * Sends the topic messages.
     *
     * @return the result
     */
    public String sendTopicMessages() {
        sendMessages(topic, "topic");
        logger.info("sendTopicMessages():");
        return "";
    }
}