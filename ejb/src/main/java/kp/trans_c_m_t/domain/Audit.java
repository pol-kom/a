package kp.trans_c_m_t.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Entity class for the <b>audit</b>.
 */
@Entity
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int created;

    private int approved;

    /**
     * Gets the created.
     *
     * @return the created
     */
    public int getCreated() {
        return created;
    }

    /**
     * Sets the created.
     *
     * @param created the created to set
     */
    public void setCreated(int created) {
        this.created = created;
    }

    /**
     * Gets the approved.
     *
     * @return the approved
     */
    public int getApproved() {
        return approved;
    }

    /**
     * Sets the approved.
     *
     * @param approved the approved to set
     */
    public void setApproved(int approved) {
        this.approved = approved;
    }
}