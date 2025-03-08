package kp.j_m_s.sync;

import jakarta.inject.Inject;
import jakarta.jms.*;
import kp.j_m_s.reply.ReplyHelper;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The <b>synchronous</b> consumer base class.
 */
public abstract class ConsumerSync {
    private Logger logger;
    private List<String> report;
    private JMSContext context;
    private ReplyHelper replyHelper;

    private static final long RECEIVING_TIMEOUT = 1000;

    /**
     * Default constructor.
     */
    protected ConsumerSync() {
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
     * Sets the context.
     *
     * @param context the context
     */
    @Inject
    public void setContext(JMSContext context) {
        this.context = context;
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
     * Receives the messages.
     *
     * @param destination the {@link Destination}
     * @param domain      the domain
     */
    protected void receiveMessages(Destination destination, String domain) {

        try (final JMSConsumer consumer = context.createConsumer(destination)) {
            boolean loopFlag = true;
            while (loopFlag) {
                final Message message = consumer.receive(RECEIVING_TIMEOUT);
                if (Objects.isNull(message)) {
                    continue;
                }
                if (message instanceof TextMessage) {
                    report.add("'%s' received message[%s]".formatted(domain, message.getBody(String.class)));
                } else {
                    replyHelper.processControlMessage(message, domain);
                    loopFlag = false;
                }
            }
        } catch (JMSException e) {
            logger.severe("receiveMessages(): domain[%s], exception[%s]".formatted(domain, e.getMessage()));
        }
    }
}