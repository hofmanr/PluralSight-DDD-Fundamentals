package pluralsightddd.sharedkernel.infrastructure.data.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.UUID;

@Converter
public class IDAttributeConverter implements AttributeConverter<Object, String> {
    @Override
    public String convertToDatabaseColumn(Object object) {
        return object.toString();
    }

    /**
     * String can only be a UUID or a Long
     * @param s
     * @return
     */
    @Override
    public Object convertToEntityAttribute(String s) {
        if (s == null) return null;
        if (s.contains("-")) { // UUID e.g. 58915602-29f1-44bf-a787-cbb3f73c6e46
            return UUID.fromString(s);
        }
        return Long.parseLong(s);
    }
}
