package kp.trans_c_m_t.queues;

import static kp.trans_c_m_t.queues.ParcelQueueQualifier.Type.CONFIRM_CREATE;
import static kp.trans_c_m_t.queues.ParcelQueueQualifier.Type.CREATE;
import static kp.trans_c_m_t.queues.ParcelQueueQualifier.Type.READ;

import jakarta.annotation.Resource;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.enterprise.inject.Produces;
import jakarta.jms.JMSDestinationDefinition;
import jakarta.jms.Queue;

/**
 * The queues initializer for the message-driven beans.
 * 
 */
@JMSDestinationDefinition(
		name = "java:/jms/CreateParcelQueue",
		destinationName = "CreateParcelQueue", interfaceName = "jakarta.jms.Queue")
@JMSDestinationDefinition(
		name = "java:/jms/ConfirmCreateParcelQueue",
		destinationName = "ConfirmCreateParcelQueue", interfaceName = "jakarta.jms.Queue")
@JMSDestinationDefinition(
		name = "java:/jms/ReadParcelQueue",
		destinationName = "ReadParcelQueue", interfaceName = "jakarta.jms.Queue")
@Startup
@Singleton
public class ParcelQueuesInitializer {
	/**
	 * The {@link Queue} for the <b>creation</b> of the parcel.
	 */
	@Resource(lookup = "java:/jms/CreateParcelQueue")
	@Produces
	@ParcelQueueQualifier(CREATE)
	protected static Queue createParcelQueue;

	/**
	 * The {@link Queue} for the <b>confirmation</b> of the parcel creation.
	 */
	@Resource(lookup = "java:/jms/ConfirmCreateParcelQueue")
	@Produces
	@ParcelQueueQualifier(CONFIRM_CREATE)
	protected static Queue confirmCreateParcelQueue;

	/**
	 * The {@link Queue} for the <b>reading</b> of the parcel.
	 */
	@Resource(lookup = "java:/jms/ReadParcelQueue")
	@Produces
	@ParcelQueueQualifier(READ)
	protected static Queue readParcelQueue;

	/**
	 * Default constructor.
	 */
	protected ParcelQueuesInitializer() {
		// empty constructor
	}
}
/*-
 These three queues above are created without using any CLI commands.
 
 An example of the CLI command for listing:
 ls /subsystem=messaging-activemq/server=default/runtime-queue=jms.queue.CreateParcelQueue/
*/