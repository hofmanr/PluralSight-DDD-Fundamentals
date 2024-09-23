package com.pluralsight.frontdesk.api.mappers;

import com.pluralsight.frontdesk.api.models.AppointmentDto;
import com.pluralsight.frontdesk.core.scheduleaggregate.Appointment;
import pluralsightddd.sharedkernel.common.types.ObjectMapper;

public class AppointmentMapper implements ObjectMapper<Appointment> {

    @Override
    public AppointmentDto map(Appointment source) {
        if (source == null) return null;
        AppointmentDto target = new AppointmentDto();
        target.scheduleId = source.getScheduleId();
        target.appointmentId = source.getId();
        target.clientId = source.getClientId();
        target.patientId = source.getPatientId();
        target.roomId = source.getRoomId();
        target.doctorId = source.getDoctorId();
        target.title = source.getTitle();
        if (source.getTimeRange() != null) {
            target.start = source.getTimeRange().getStart().getLocalDateTime();
            target.end = source.getTimeRange().getEnd().getLocalDateTime();
        }
        target.isConfirmed = source.getDateTimeConfirmed() != null;
        target.isPotentiallyConflicting = source.isPotentiallyConflicting();
        target.isAllDay = source.getTimeRange().isAllDay();

        return target;
    }
}
