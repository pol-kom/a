package kp.j_p_a.domain.components;

/**
 * The <b>cardinal direction</b> enumeration.
 */
public enum CardinalDirection {
    /**
     * North direction.
     */
    NORTH,
    /**
     * East direction.
     */
    EAST,
    /**
     * South direction.
     */
    SOUTH,
    /**
     * West direction.
     */
    WEST;

    /**
     * Gets the next cardinal direction.
     *
     * @return the next cardinal direction
     */
    public CardinalDirection getNext() {

        return switch (this) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            default -> NORTH;
        };
    }
}
