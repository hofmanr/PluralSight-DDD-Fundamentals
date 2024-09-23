package com.pluralsight.frontdesk.api.mappers;

import com.pluralsight.frontdesk.api.models.AppointmentTypeDto;
import com.pluralsight.frontdesk.core.syncedaggregates.AppointmentType;
import pluralsightddd.sharedkernel.common.types.ObjectMapper;

public class AppointmentTypeMapper implements ObjectMapper<AppointmentType> {

    @Override
    public AppointmentTypeDto map(AppointmentType source) {
        if (source == null) return null;

        return new AppointmentTypeDto(source.getId(),source.getName(), source.getMinutes());
    }

}
