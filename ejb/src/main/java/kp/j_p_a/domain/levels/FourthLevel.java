package kp.j_p_a.domain.levels;

import jakarta.persistence.Entity;

/**
 * Entity class for the <b>fourth level</b>.
 */
@Entity
public class FourthLevel extends Level {
    /**
     * Default constructor.
     */
    public FourthLevel() {
        // constructor is empty
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        return getClass() == obj.getClass();
    }

}