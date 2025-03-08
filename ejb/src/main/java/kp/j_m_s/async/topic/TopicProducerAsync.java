package kp.j_m_s.async.topic;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.jms.Topic;
import kp.j_m_s.Producer;

import java.util.logging.Logger;

/**
 * JMS messages <b>topic producer</b> (for asynchronous consumer).
 */
@Named
@RequestScoped
public class TopicProducerAsync extends Producer {
    private Logger logger;

    @Resource(lookup = "jms/StudyTopicAsync")
    private Topic topic;

    /**
     * Default constructor.
     */
    public TopicProducerAsync() {
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
     * Sends topic messages.
     *
     * @return the result
     */
    public String sendTopicMessages() {
        sendMessages(topic, "topic");
        logger.info("sendTopicMessages():");
        return "";
    }
}