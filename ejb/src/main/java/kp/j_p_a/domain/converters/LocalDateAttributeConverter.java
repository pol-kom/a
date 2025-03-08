package kp.j_p_a.domain.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

/**
 * The {@link AttributeConverter} implementation for the {@link LocalDate}.
 */
@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Date> {

    /**
     * Default constructor.
     */
    public LocalDateAttributeConverter() {
        // constructor is empty
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date convertToDatabaseColumn(LocalDate localDate) {
        return Optional.ofNullable(localDate).map(Date::valueOf).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDate convertToEntityAttribute(Date date) {
        return Optional.ofNullable(date).map(Date::toLocalDate).orElse(null);
    }
}