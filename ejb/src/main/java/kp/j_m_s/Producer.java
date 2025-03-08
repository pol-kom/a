package kp.j_m_s;

import jakarta.annotation.Resource;
import jakarta.inject.Inject;
import jakarta.jms.*;
import kp.util.Tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * The JMS messages producer base.
 * <p>
 * A 'jakarta.jms.Destination' object is a JMS administered object.
 * </p>
 * <ul>
 * <li>'Destination' is the target of messages that the client produces.</li>
 * <li>'Destination' is the source of messages that the client consumes.</li>
 * </ul>
 * <ul>
 * <li>'Destination' is the JMS queue in the point-to-point messaging domain.</li>
 * <li>'Destination' is the JMS topic in the 'publish/subscribe' messaging domain.</li>
 * </ul>
 * <p>
 * Because injected JMSContext is container-managed, these methods must not be used:
 * </p>
 * <ul>
 * <li>commit</li>
 * <li>rollback</li>
 * <li>acknowledge</li>
 * <li>start</li>
 * <li>stop</li>
 * <li>recover</li>
 * <li>close</li>
 * </ul>
 */
public abstract class Producer {
    private Logger logger;
    private List<String> report;
    private JMSContext context;

    @Resource(lookup = "jms/StudyReplyQueue")
    private Queue replyQueue;

    private static final List<String> MUTABLE_TEXT_LIST = new ArrayList<>(Tools.getTextList());

    /**
     * Default constructor.
     */
    protected Producer() {
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
     * Sends the messages.
     *
     * @param destination the {@link Destination}
     * @param domain      the domain (queue or topic)
     */
    protected void sendMessages(Destination destination, String domain) {

        final StringBuilder strBld = new StringBuilder();
        try {
            final JMSProducer producer = context.createProducer();
            for (int i = 1; i <= 3; i++) {
                final String text = MUTABLE_TEXT_LIST.getFirst();
                Collections.rotate(MUTABLE_TEXT_LIST, -1);
                producer.send(destination, text);
                report.add("'%s' sent message[%s]".formatted(domain, text));
                strBld.append(text);
            }
            /*
             * Send a non-text control message indicating end of messages.
             */
            final Message controlMessage = context.createMessage();
            final String label = strBld.toString();
            controlMessage.setStringProperty("label", label);
            controlMessage.setJMSReplyTo(replyQueue);
            producer.send(destination, controlMessage);
            report.add("'%s' sent CONTROL message, label[%s]".formatted(domain, label));
        } catch (JMSRuntimeException | JMSException e) {
            logger.severe("sendMessages(): domain[%s], exception[%s]".formatted(domain, e.getMessage()));
        }
    }
}