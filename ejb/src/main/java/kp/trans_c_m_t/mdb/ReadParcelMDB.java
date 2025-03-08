package kp.trans_c_m_t.mdb;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.inject.Inject;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import kp.trans_c_m_t.service.ParcelAdministratorBean;

import java.util.List;
import java.util.logging.Logger;

/**
 * Message-driven bean for the <b>parcel</b> list reading.
 */
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(
                propertyName = "destinationLookup", propertyValue = "jms/ReadParcelQueue"),
        @ActivationConfigProperty(
                propertyName = "destinationType", propertyValue = "jakarta.jms.Queue")})
public class ReadParcelMDB implements MessageListener {
    private Logger logger;
    ParcelAdministratorBean parcelAdministratorBean;
    private List<String> report;

    /**
     * Default constructor.
     */
    public ReadParcelMDB() {
        // empty constructor
    }

    /**
     * Parameterized constructor.
     *
     * @param logger                  the {@link Logger}
     * @param parcelAdministratorBean the {@link ParcelAdministratorBean}
     * @param report                  the report
     */
    @Inject
    public ReadParcelMDB(Logger logger, ParcelAdministratorBean parcelAdministratorBean, List<String> report) {
        this();
        this.logger = logger;
        this.parcelAdministratorBean = parcelAdministratorBean;
        this.report = report;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onMessage(Message message) {

        report.add("ReadParcelMDB received message from 'ReadParcelQueue'.");
        parcelAdministratorBean.read();
        logger.info("onMessage():");
    }
}