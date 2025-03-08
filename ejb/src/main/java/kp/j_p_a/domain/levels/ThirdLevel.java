package kp.j_p_a.domain.levels;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;

import java.util.Set;
import java.util.TreeSet;

/**
 * Entity class for the <b>third level</b>.
 */
@Entity
public class ThirdLevel extends Level {
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy
    private final Set<FourthLevel> fourthLevels = new TreeSet<>();

    /**
     * Default constructor.
     */
    public ThirdLevel() {
        // constructor is empty
    }

    /**
     * Gets the fourth levels set.
     *
     * @return the fourth levels set.
     */
    public Set<FourthLevel> getFourthLevels() {
        return fourthLevels;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + fourthLevels.hashCode();
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
        ThirdLevel other = (ThirdLevel) obj;
        return fourthLevels.equals(other.fourthLevels);
    }

}