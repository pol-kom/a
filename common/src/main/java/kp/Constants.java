package kp;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The constants.
 */
public final class Constants {
    /**
     * The label
     */
    public static final String LABEL = "* * * 'WebSe' from created service instance: * * *";
    /**
     * The long horizontal line with '#'.
     */
    public static final String BREAK = IntStream.rangeClosed(1, 150).boxed().map(_ -> "#")
            .collect(Collectors.joining()).concat(System.lineSeparator());
    /**
     * The target namespace.
     */
    public static final String TARGET_NAMESPACE = "http://w_s.kp/";

    /**
     * Private constructor to prevent instantiation.
     */
    private Constants() {
        throw new IllegalStateException("Utility class");
    }
}