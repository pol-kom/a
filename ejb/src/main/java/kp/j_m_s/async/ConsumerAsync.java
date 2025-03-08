package kp.j_m_s.async;

import jakarta.inject.Inject;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import kp.j_m_s.reply.ReplyHelper;

import java.util.List;
import java.util.logging.Logger;

/**
 * The <b>asynchronous</b> consumer base class.
 */
public abstract class ConsumerAsync {
    private Logger logger;
    private List<String> report;
    private ReplyHelper replyHelper;

    /**
     * Default constructor.
     */
    protected ConsumerAsync() {
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
     * Sets the reply helper.
     *
     * @param replyHelper the replyHelper
     */
    @Inject
    public void setReplyHelper(ReplyHelper replyHelper) {
        this.replyHelper = replyHelper;
    }

    /**
     * Processes the messages.
     *
     * @param message the {@link Message}
     * @param domain  the domain
     */
    protected void process(Message message, String domain) {

        try {
            if (message instanceof TextMessage) {
                report.add("'%s' received message[%s]".formatted(domain, message.getBody(String.class)));
            } else {
                replyHelper.processControlMessage(message, domain);
            }
        } catch (JMSException e) {
            logger.severe("process(): JMSException[%s]".formatted(e.getMessage()));
        }
    }
}