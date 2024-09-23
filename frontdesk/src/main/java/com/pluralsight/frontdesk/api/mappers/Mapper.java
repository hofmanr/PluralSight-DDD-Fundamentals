package com.pluralsight.frontdesk.api.mappers;

import pluralsightddd.sharedkernel.common.types.ObjectMapper;

import java.util.Collections;
import java.util.List;

public class Mapper {
    private static final ScheduleMapper SCHEDULE_MAPPER = new ScheduleMapper();
    private static final AppointmentMapper APPOINTMENT_MAPPER = new AppointmentMapper();
    private static final AppointmentTypeMapper APPOINTMENT_TYPE_MAPPER = new AppointmentTypeMapper();

    private Mapper() {
        // To prevent instantiation
    }

    public static <T, U> U map(Object object) {
        if (object == null) return null;

        ObjectMapper<T> objectMapper = (ObjectMapper<T>) getMapper(object.getClass());
        if (objectMapper == null) return null;
        return objectMapper.map((T) object);
    }

    public static <T, U> List<U> mapList(List<?> object) {
        if (object == null || object.isEmpty()) return Collections.emptyList();

        ObjectMapper<T> mapper = (ObjectMapper<T>) getMapper(object.get(0).getClass());
        if (mapper == null) return Collections.emptyList();
        return (List<U>) mapper.transformList((List<T>) object);
    }

    private static ObjectMapper<?> getMapper(Class<?> isClass) {
        return switch (isClass.getSimpleName()) {
            case "Schedule" -> SCHEDULE_MAPPER;
            case "Appointment" -> APPOINTMENT_MAPPER;
            case "AppointmentType" -> APPOINTMENT_TYPE_MAPPER;
            default -> null;
        };
    }
}
