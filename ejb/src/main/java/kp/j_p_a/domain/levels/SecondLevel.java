package kp.j_p_a.domain.levels;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;

import java.util.Set;
import java.util.TreeSet;

/**
 * Entity class for the <b>second level</b>.
 */
@Entity
public class SecondLevel extends Level {
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy
    private final Set<ThirdLevel> thirdLevels = new TreeSet<>();

    /**
     * Default constructor.
     */
    public SecondLevel() {
        // constructor is empty
    }

    /**
     * Gets the third levels set.
     *
     * @return the third levels set.
     */
    public Set<ThirdLevel> getThirdLevels() {
        return thirdLevels;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + thirdLevels.hashCode();
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
        SecondLevel other = (SecondLevel) obj;
        return thirdLevels.equals(other.thirdLevels);
    }

}