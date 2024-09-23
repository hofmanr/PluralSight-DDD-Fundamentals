package com.pluralsight.frontdesk.api.mappers;

import com.pluralsight.frontdesk.api.models.ScheduleDto;
import com.pluralsight.frontdesk.core.scheduleaggregate.Schedule;
import pluralsightddd.sharedkernel.common.types.ObjectMapper;

import java.time.LocalDateTime;

// Without appointments
public class ScheduleMapper implements ObjectMapper<Schedule> {

    @Override
    public ScheduleDto map(Schedule source) {
        if (source == null) return null;
        LocalDateTime start = null;
        LocalDateTime end = null;
        if (source.getDateRange() != null) {
            start = source.getDateRange().getStart().getLocalDateTime();
            end = source.getDateRange().getEnd().getLocalDateTime();
        }
        return new ScheduleDto(
                source.getId(),
                source.getClinicId(),
                start,
                end
        );
    }

}
