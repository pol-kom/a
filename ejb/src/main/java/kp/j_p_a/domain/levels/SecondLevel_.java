package kp.j_p_a.domain.levels;

import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

/**
 * Metamodel for the {@link SecondLevel} entity class.
 */
@SuppressWarnings("java:S101") // switch off Sonarqube rule 'class naming convention'
@StaticMetamodel(SecondLevel.class)
public class SecondLevel_ extends Level_ {
    /**
     * The third levels.
     */
    public static volatile SetAttribute<SecondLevel, ThirdLevel> thirdLevels;// NOSONAR not thread-safe with "volatile"

    /**
     * Default constructor.
     */
    public SecondLevel_() {
        // constructor is empty
    }
}