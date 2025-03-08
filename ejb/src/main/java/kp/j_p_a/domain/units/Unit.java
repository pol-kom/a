package kp.j_p_a.domain.units;

import jakarta.persistence.*;
import kp.j_p_a.domain.components.CardinalDirection;

import java.util.*;

/**
 * Entity class for the <b>unit</b>.
 */
@Entity
public class Unit implements Comparable<Unit> {
    @Id
    private String id;

    @OneToOne
    private Unit previous = null;

    @OneToOne(mappedBy = "previous", cascade = CascadeType.ALL, orphanRemoval = true)
    private Unit next;

    /*-
     * Self-Referential Relationship: 'parent' - 'children'
     */

    @ManyToOne
    private Unit parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy
    private final Set<Unit> children = new TreeSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKeyEnumerated(EnumType.STRING)
    private final Map<CardinalDirection, Side> sides = new TreeMap<>();

    /**
     * Default constructor.
     */
    public Unit() {
        // constructor is empty
    }

    /**
     * Parameterized constructor.
     *
     * @param id the unit id
     */
    public Unit(String id) {
        this();
        this.id = id;
        List.of(CardinalDirection.values()).forEach(cd -> this.sides.put(cd, new Side()));
    }

    /**
     * Parameterized constructor.
     *
     * @param id       the unit id
     * @param previous the previous unit
     */
    public Unit(String id, Unit previous) {
        this(id);
        this.previous = previous;
    }

    /**
     * Adds the child unit.
     *
     * @param child the child unit to add
     */
    public void addChild(Unit child) {

        children.add(child);
        child.setParent(this);
    }

    /**
     * Removes the child unit.
     *
     * @param child the child unit to remove
     */
    public void removeChild(Unit child) {

        children.remove(child);
        child.setParent(null);
    }

    /**
     * Gets the unit id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the previous unit.
     *
     * @return the previous unit
     */
    public Unit getPrevious() {
        return previous;
    }

    /**
     * Sets the previous unit.
     *
     * @param previous the previous unit to set
     */
    public void setPrevious(Unit previous) {
        this.previous = previous;
    }

    /**
     * Gets the next unit.
     *
     * @return the next unit
     */
    public Unit getNext() {
        return next;
    }

    /**
     * Sets the next unit.
     *
     * @param next the next unit to set
     */
    public void setNext(Unit next) {
        this.next = next;
    }

    /**
     * Gets the parent unit.
     *
     * @return the parent unit
     */
    public Unit getParent() {
        return parent;
    }

    /**
     * Sets the parent unit.
     *
     * @param parent the parent unit to set
     */
    public void setParent(Unit parent) {
        this.parent = parent;
    }

    /**
     * Gets the children unit set.
     *
     * @return the children unit set
     */
    public Set<Unit> getChildren() {
        return children;
    }

    /**
     * Gets the sides map.
     *
     * @return the sides map
     */
    public Map<CardinalDirection, Side> getSides() {
        return sides;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        Unit other = (Unit) obj;
        if (id == null) {
            return other.id == null;
        } else
            return id.equals(other.id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Unit unit) {

        return Objects.nonNull(unit) ? this.id.compareTo(unit.id) : 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ("Unit: id[%s], previous[%s], next[%s], parent[%s], children size[%d], "
                + "north side[%d], east side[%d], south side[%d], west side[%d]").formatted(
                id,
                Objects.nonNull(previous) ? previous.id : "-",
                Objects.nonNull(next) ? next.id : "-",
                Objects.nonNull(parent) ? parent.id : "-",
                children.size(),
                Objects.nonNull(sides.get(CardinalDirection.NORTH)) ? sides.get(CardinalDirection.NORTH).id : 0,
                Objects.nonNull(sides.get(CardinalDirection.EAST)) ? sides.get(CardinalDirection.EAST).id : 0,
                Objects.nonNull(sides.get(CardinalDirection.SOUTH)) ? sides.get(CardinalDirection.SOUTH).id : 0,
                Objects.nonNull(sides.get(CardinalDirection.WEST)) ? sides.get(CardinalDirection.WEST).id : 0);
    }
}