package kp.j_p_a.domain.boxes;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OrderBy;

import java.util.Set;
import java.util.TreeSet;

/**
 * The <b>multi box</b> entity class.
 */
//SonarQube signals 'duplicated blocks of code'.  But this rule is deprecated in Sonar! 
@Entity
public class MultiBox extends Box {

    @ManyToMany
    @OrderBy
    private final Set<CentralBox> centralBoxes = new TreeSet<>();

    /**
     * Default constructor.
     */
    public MultiBox() {
        // constructor is empty
    }

    /**
     * Gets central boxes.
     *
     * @return the central boxes set
     */
    public Set<CentralBox> getCentralBoxes() {
        return centralBoxes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + centralBoxes.hashCode();
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
        MultiBox other = (MultiBox) obj;
        return centralBoxes.equals(other.centralBoxes);
    }

}