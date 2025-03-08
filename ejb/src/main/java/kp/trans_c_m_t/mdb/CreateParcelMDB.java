package kp.trans_c_m_t.mdb;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.inject.Inject;
import jakarta.jms.*;
import kp.trans_c_m_t.helper.Approver;
import kp.trans_c_m_t.service.ParcelAdministratorBean;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Message-driven bean for the <B>parcel</B> creation.
 */
//SonarQube signals 'duplicated blocks of code'.  But this rule is deprecated in Sonar! 
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(
                propertyName = "destinationLookup", propertyValue = "jms/CreateParcelQueue"),
        @ActivationConfigProperty(
                propertyName = "destinationType", propertyValue = "jakarta.jms.Queue")})
public class CreateParcelMDB implements MessageListener {
    private Logger logger;
    ParcelAdministratorBean parcelAdministratorBean;
    Approver approver;
    private JMSContext context;
    private List<String> report;

    /**
     * Default constructor.
     */
    public CreateParcelMDB() {
        // empty constructor
    }

    /**
     * Parameterized constructor.
     *
     * @param logger                  the {@link Logger}
     * @param parcelAdministratorBean the {@link ParcelAdministratorBean}
     * @param approver                the {@link Approver}
     * @param context                 the {@link JMSContext}
     * @param report                  the report
     */
    @Inject
    public CreateParcelMDB(Logger logger, ParcelAdministratorBean parcelAdministratorBean, Approver approver,
                           JMSContext context, List<String> report) {
        this();
        this.logger = logger;
        this.parcelAdministratorBean = parcelAdministratorBean;
        this.approver = approver;
        this.context = context;
        this.report = report;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onMessage(Message message) {

        String text;
        try {
            text = ((TextMessage) message).getText();
        } catch (JMSException e) {
            logger.severe("onMessage(): exception[%s]".formatted(e.getMessage()));
            return;
        }
        report.add("CreateParcelMDB received text[%s] from 'CreateParcelQueue'.".formatted(text));
        int parcelId = parcelAdministratorBean.create(text);
        sendConfirmMessage(message, parcelId);
        // last step in current transaction
        approver.approve(parcelId);
        if (logger.isLoggable(Level.INFO)) {
            logger.info("onMessage(): text[%s], parcelId[%d]".formatted(text, parcelId));
        }
    }

    /**
     * Sends confirm message.
     *
     * @param message  the {@link Message}
     * @param parcelId the parcel id
     */
    private void sendConfirmMessage(Message message, int parcelId) {

        String text;
        try {
            final Queue confirmQueue = (Queue) message.getJMSReplyTo();
            text = "%s:%d".formatted(((TextMessage) message).getText(), parcelId);
            final Message confirmMessage = context.createTextMessage(text);
            context.createProducer().send(confirmQueue, confirmMessage);
        } catch (JMSException e) {
            logger.severe("sendConfirmMessage(): exception[%s]".formatted(e.getMessage()));
            return;
        }
        report.add("CreateParcelMDB sent confirm message with text[%s] to 'ConfirmCreateParcelQueue'"
                .formatted(text));
    }
}