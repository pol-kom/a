package kp.trans_c_m_t.controller;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Queue;
import kp.trans_c_m_t.queues.ParcelQueueQualifier;
import kp.trans_c_m_t.service.AuditorBean;
import kp.util.Tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import static kp.trans_c_m_t.queues.ParcelQueueQualifier.Type.*;

/**
 * CDI managed bean for the <b>container-managed</b> transactions research.
 */
@Named
@RequestScoped
public class TransCmtManagedBean {
    private final Logger logger;
    private final JMSContext context;
    private final AuditorBean auditorBean;
    private final Queue createParcelQueue;
    private final Queue confirmCreateParcelQueue;
    private final Queue readParcelQueue;

    private static final List<String> MUTABLE_TEXT_LIST = new ArrayList<>(Tools.getTextList());

    /**
     * Parameterized constructor.
     *
     * @param logger                   the {@link Logger}
     * @param context                  the {@link JMSContext}
     * @param auditorBean              the {@link AuditorBean}
     * @param createParcelQueue        the {@link Queue} for creating the parcel
     * @param confirmCreateParcelQueue the {@link Queue} for confirming the parcel creation
     * @param readParcelQueue          the {@link Queue} for reading the parcel
     */
    @Inject
    public TransCmtManagedBean(Logger logger, JMSContext context, AuditorBean auditorBean,
                               @ParcelQueueQualifier(CREATE)
                               Queue createParcelQueue,
                               @ParcelQueueQualifier(CONFIRM_CREATE)
                               Queue confirmCreateParcelQueue,
                               @ParcelQueueQualifier(READ)
                               Queue readParcelQueue) {
        this.logger = logger;
        this.context = context;
        this.auditorBean = auditorBean;
        this.createParcelQueue = createParcelQueue;
        this.confirmCreateParcelQueue = confirmCreateParcelQueue;
        this.readParcelQueue = readParcelQueue;
    }

    /**
     * Creates the parcel.
     *
     * @return the result
     */
    public String create() {

        final Message message = context.createTextMessage(MUTABLE_TEXT_LIST.getFirst());
        try {
            message.setJMSReplyTo(confirmCreateParcelQueue);
        } catch (JMSException e) {
            logger.severe("create(): exception[%s]".formatted(e.getMessage()));
            return "";
        }
        context.createProducer().send(createParcelQueue, message);
        Collections.rotate(MUTABLE_TEXT_LIST, -1);
        logger.info("create():");
        return "";
    }

    /**
     * Reads the parcel.
     *
     * @return the result
     */
    public String read() {

        context.createProducer().send(readParcelQueue, context.createMessage());
        logger.info("read():");
        return "";
    }

    /**
     * Shows the audit.
     *
     * @return the result
     */
    public String showAudit() {

        auditorBean.showAudit();
        logger.info("showAudit():");
        return "";
    }
}