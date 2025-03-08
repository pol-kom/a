package kp.trans_c_m_t.queues;

import jakarta.inject.Qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The parcel queue qualifier.
 */
@Qualifier
@Documented
@Retention(RUNTIME)
@Target({PARAMETER, FIELD, TYPE})
public @interface ParcelQueueQualifier {
    /**
     * Sets the type of the queue.
     *
     * @return the type
     */
    Type value();

    /**
     * The type enumeration.
     */
    enum Type {
        /**
         * The 'create' type.
         */
        CREATE,
        /**
         * The 'confirm create' type.
         */
        CONFIRM_CREATE,
        /**
         * The 'read' type.
         */
        READ
    }
}