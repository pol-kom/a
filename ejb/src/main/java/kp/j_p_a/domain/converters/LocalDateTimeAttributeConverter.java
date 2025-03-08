package kp.j_p_a.domain.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * The {@link AttributeConverter} implementation for the {@link LocalDateTime}.
 */
@Converter(autoApply = true)
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    /**
     * Default constructor.
     */
    public LocalDateTimeAttributeConverter() {
        // constructor is empty
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
        return Optional.ofNullable(localDateTime).map(Timestamp::valueOf).orElse(null);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp timestamp) {
        return Optional.ofNullable(timestamp).map(Timestamp::toLocalDateTime).orElse(null);
    }
}