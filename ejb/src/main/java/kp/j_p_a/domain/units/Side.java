package kp.j_p_a.domain.units;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Entity class for the <b>side</b>.
 */
@Entity
public class Side {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    /**
     * Default constructor.
     */
    public Side() {
        // constructor is empty
    }
}
