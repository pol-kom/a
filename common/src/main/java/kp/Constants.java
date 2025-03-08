package kp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The constants.
 *
 */
public final class Constants {
	/**
	 * The long horizontal line with '#'.
	 */
	public static final String BREAK = new StringBuilder().append(System.getProperty("line.separator"))//
			.append(IntStream.rangeClosed(1, 150).boxed().map(arg -> "#").collect(Collectors.joining()))//
			.append(System.getProperty("line.separator")).toString();
	/**
	 * The horizontal line with '-'.
	 */
	public static final String LINE = IntStream.rangeClosed(1, 50).boxed().map(arg -> "- ")
			.collect(Collectors.joining());
	/**
	 * The label for empty.
	 */
	public static final String EMPTY = "empty";
	/**
	 * The {@link ZonedDateTime} example value.
	 */
	public static final ZonedDateTime EXAMPLE_ZONED_DATE_TIME = ZonedDateTime.parse("2099-12-31T23:59:59+01:00",
			DateTimeFormatter.ISO_ZONED_DATE_TIME);
	/**
	 * The {@link LocalDate} example value.
	 */
	public static final LocalDate EXAMPLE_LOCAL_DATE = EXAMPLE_ZONED_DATE_TIME.toLocalDate();
	/**
	 * The {@link LocalDateTime} example value.
	 */
	public static final LocalDateTime EXAMPLE_LOCAL_DATE_TIME = EXAMPLE_ZONED_DATE_TIME.toLocalDateTime();

	/**
	 * Private constructor to prevent instantiation.
	 */
	private Constants() {
		throw new IllegalStateException("Utility class");
	}
}
