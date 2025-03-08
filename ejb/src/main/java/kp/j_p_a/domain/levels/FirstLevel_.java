package kp.j_p_a.domain.levels;

import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

/**
 * Metamodel for the {@link FirstLevel} entity class.
 */
@SuppressWarnings("java:S101") // switch off Sonarqube rule 'class naming convention'
@StaticMetamodel(FirstLevel.class)
public class FirstLevel_ extends Level_ {
    /**
     * The second levels
     */
    public static volatile SetAttribute<FirstLevel, SecondLevel> secondLevels;// NOSONAR not thread-safe with "volatile"

    /**
     * Default constructor.
     */
    public FirstLevel_() {
        // constructor is empty
    }
}
