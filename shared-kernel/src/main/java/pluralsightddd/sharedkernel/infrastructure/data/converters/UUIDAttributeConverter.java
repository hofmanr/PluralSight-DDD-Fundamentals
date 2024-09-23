package pluralsightddd.sharedkernel.infrastructure.data.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.UUID;

@Converter
public class UUIDAttributeConverter implements AttributeConverter<UUID, String> {
    @Override
    public String convertToDatabaseColumn(UUID uuid) {
        if (uuid == null) return null;
        return uuid.toString();
    }

    @Override
    public UUID convertToEntityAttribute(String s) {
        if (s == null) return null;
        return UUID.fromString(s);
    }
}
