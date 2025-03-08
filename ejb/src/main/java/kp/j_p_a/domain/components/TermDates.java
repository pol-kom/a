package kp.j_p_a.domain.components;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Embeddable;
import kp.Constants;

/**
 * The <b>term dates</b>.
 * <p>
 * For {@link LocalDate} and {@link LocalDateTime} it was implemented the
 * 'AttributeConverter' in the package 'kp.j_p_a.domain.converters'.
 * </p>
 */
@Embeddable
public class TermDates {
	private LocalDate localDate;
	private LocalDateTime localDateTime;

	/**
	 * Default constructor with initialization.
	 * 
	 */
	public TermDates() {
		this.localDate = Constants.EXAMPLE_LOCAL_DATE;
		this.localDateTime = Constants.EXAMPLE_LOCAL_DATE_TIME;
	}

	/**
	 * Gets the {@link LocalDate}.
	 * 
	 * @return the {@link LocalDate}
	 */
	public LocalDate getLocalDate() {
		return localDate;
	}

	/**
	 * Sets the {@link LocalDate}.
	 * 
	 * @param localDate the {@link LocalDate} to set
	 */
	public void setLocalDate(LocalDate localDate) {
		this.localDate = localDate;
	}

	/**
	 * Gets the {@link LocalDateTime}.
	 * 
	 * @return the {@link LocalDateTime}
	 */
	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	/**
	 * Sets the {@link LocalDateTime}.
	 * 
	 * @param localDateTime the {@link LocalDateTime} to set
	 */
	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}
}