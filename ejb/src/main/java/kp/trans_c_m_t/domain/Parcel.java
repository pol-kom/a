package kp.trans_c_m_t.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * The entity class for the <B>parcel</B>.
 */
@Entity
public class Parcel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String text;

    /**
     * Default constructor.
     */
    public Parcel() {
        // empty constructor
    }

    /**
     * Parameterized constructor.
     *
     * @param text the text
     */
    public Parcel(String text) {
        this();
        this.text = text;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text.
     *
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }
}