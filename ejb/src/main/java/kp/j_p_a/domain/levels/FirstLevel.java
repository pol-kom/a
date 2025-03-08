package kp.j_p_a.domain.levels;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;

import java.util.Set;
import java.util.TreeSet;

/**
 * Entity class for the <b>first level</b>.
 */
@Entity
public class FirstLevel extends Level {
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy
    private final Set<SecondLevel> secondLevels = new TreeSet<>();

    /**
     * Default constructor.
     */
    public FirstLevel() {
        // constructor is empty
    }

    /**
     * Gets the second levels set.
     *
     * @return the second levels set.
     */
    public Set<SecondLevel> getSecondLevels() {
        return secondLevels;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + secondLevels.hashCode();
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        FirstLevel other = (FirstLevel) obj;
        return secondLevels.equals(other.secondLevels);
    }

}