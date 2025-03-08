package kp.e_j_b;

/**
 * The common interface for other remote view and local view interfaces.
 */
public interface Common {
    /**
     * Checks the bean implementation.
     * <p>
     * The business method.
     * </p>
     *
     * @param stamp the stamp
     * @return message the message
     */
    public String check(String stamp);

    /**
     * Changes value of the session bean field.
     * <p>
     * The business method.
     * </p>
     *
     * @param stamp  the stamp
     * @param number the number
     * @return the message
     */
    public String change(String stamp, int number);
}
