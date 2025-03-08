package kp.j_p_a.domain.levels;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

/**
 * Metamodel for the {@link Level} mapped superclass.
 */
@SuppressWarnings("java:S101") // switch off Sonarqube rule 'class naming convention'
@MappedSuperclass
@StaticMetamodel(Level.class)
public abstract class Level_ {
    /**
     * The id.
     */
    public static volatile SingularAttribute<Level, Integer> id;// NOSONAR not thread-safe with "volatile"
    /**
     * The text.
     */
    public static volatile SingularAttribute<Level, String> text;// NOSONAR not thread-safe with "volatile"

    /**
     * Default constructor.
     */
    protected Level_() {
        // constructor is empty
    }
}