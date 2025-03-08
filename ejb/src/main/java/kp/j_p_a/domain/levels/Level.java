package kp.j_p_a.domain.levels;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.util.Objects;

/**
 * Mapped superclass for the <b>level</b>.
 */
//SonarQube signals 'duplicated blocks of code'.  But this rule is deprecated in Sonar! 
@MappedSuperclass
public abstract class Level implements Comparable<Level> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String text;

    /**
     * Default constructor.
     */
    protected Level() {
        // constructor is empty
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

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((text == null) ? 0 : text.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Level other = (Level) obj;
        if (id != other.id) {
            return false;
        }
        if (text == null) {
            return other.text == null;
        }
        return text.equals(other.text);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Level level) {

        if (Objects.isNull(level)) {
            return 0;
        }
        final int cmp = this.id - level.getId();
        return cmp == 0 ? this.text.compareTo(level.text) : cmp;
    }
}