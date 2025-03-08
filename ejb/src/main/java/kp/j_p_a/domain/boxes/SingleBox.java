package kp.j_p_a.domain.boxes;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

/**
 * The <b>single box</b> entity class.
 */
//SonarQube signals 'duplicated blocks of code'.  But this rule is deprecated in Sonar! 
@Entity
public class SingleBox extends Box {

    // the inverse side of bidirectional relationship
    @OneToOne(mappedBy = "singleBox")
    private CentralBox centralBox;

    /**
     * Default constructor.
     */
    public SingleBox() {
        // constructor is empty
    }

    /**
     * Gets central box.
     *
     * @return the central box
     */
    public CentralBox getCentralBox() {
        return centralBox;
    }

    /**
     * Sets central box.
     *
     * @param centralBox the central box to set
     */
    public void setCentralBox(CentralBox centralBox) {
        this.centralBox = centralBox;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((centralBox == null) ? 0 : centralBox.hashCode());
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
        SingleBox other = (SingleBox) obj;
        if (centralBox == null) {
            return other.centralBox == null;
        }
        return centralBox.equals(other.centralBox);
    }

}