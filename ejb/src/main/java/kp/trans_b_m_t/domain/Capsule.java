package kp.trans_b_m_t.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Entity class for the <b>capsule</b>.
 */
@Entity
public class Capsule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String text;

    /**
     * Default constructor.
     */
    public Capsule() {
        // constructor is empty
    }

    /**
     * Parameterized constructor.
     *
     * @param text the text
     */
    public Capsule(String text) {
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
