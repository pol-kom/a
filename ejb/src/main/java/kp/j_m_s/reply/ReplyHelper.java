package kp.j_m_s.reply;

import jakarta.inject.Inject;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Queue;

import java.util.List;

/**
 * The reply helper.
 * <p>
 * Sends the control messages to the <b>reply queue</b>.
 * </p>
 */
public class ReplyHelper {
    private final List<String> report;
    private final JMSContext context;

    /**
     * Parameterized constructor.
     *
     * @param report  the report list
     * @param context the {@link JMSContext}
     */
    @Inject
    public ReplyHelper(List<String> report, JMSContext context) {
        this.report = report;
        this.context = context;
    }

    /**
     * Processes the control message.
     *
     * @param message the {@link Message}
     * @param domain  the domain
     * @throws JMSException the JMS exception
     */
    public void processControlMessage(Message message, String domain) throws JMSException {

        final String label = message.getStringProperty("label");
        report.add("'%s' received CONTROL message, label[%s]".formatted(domain, label));

        final Queue replyQueue = (Queue) message.getJMSReplyTo();


        final Message replyMessage = context.createMessage();
        replyMessage.setStringProperty("label", label);
        context.createProducer().send(replyQueue, replyMessage);
        report.add("'reply queue' sent CONTROL message, label[%s]".formatted(label));
    }
}