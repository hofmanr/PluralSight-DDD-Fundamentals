package com.pluralsight.frontdesk.api.endpoints;

import com.pluralsight.frontdesk.api.OfficeSettings;
import com.pluralsight.frontdesk.api.models.*;
import com.pluralsight.frontdesk.core.interfaces.AppointmentTypeRepository;
import com.pluralsight.frontdesk.core.interfaces.ClientRepository;
import com.pluralsight.frontdesk.core.interfaces.ScheduleRepository;
import com.pluralsight.frontdesk.core.scheduleaggregate.Appointment;
import com.pluralsight.frontdesk.core.scheduleaggregate.Schedule;
import com.pluralsight.frontdesk.core.syncedaggregates.AppointmentType;
import com.pluralsight.frontdesk.core.syncedaggregates.Client;
import com.pluralsight.frontdesk.api.mappers.Mapper;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pluralsightddd.sharedkernel.core.valueobjects.DateTimeOffset;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@Stateless
@Path("/schedules")
@Produces(MediaType.APPLICATION_JSON)
public class ScheduleResource {

    private ScheduleRepository scheduleRepository;

    private ClientRepository clientRepository;

    private AppointmentTypeRepository appointmentTypeRepository;

    private OfficeSettings settings;

    public ScheduleResource() {
        // REST services need a public default constructor
    }

    @Inject
    public ScheduleResource(ScheduleRepository scheduleRepository,
                            ClientRepository clientRepository,
                            AppointmentTypeRepository appointmentTypeRepository,
                            OfficeSettings officeSettings) {
        this.scheduleRepository = scheduleRepository;
        this.clientRepository = clientRepository;
        this.appointmentTypeRepository = appointmentTypeRepository;
        this.settings = officeSettings;
    }

    // Returns all de clinics without the appointments
    @GET
    @Path("/")
    public Response listSchedules() {
        List<Schedule> schedules = scheduleRepository.list();
        List<ScheduleDto> scheduleDtoList = Mapper.mapList(schedules); // without appointments
//        List<ScheduleDto> scheduleDtoList = schedules.stream().<ScheduleDto>map(Mapper::map).toList();

        return scheduleDtoList.isEmpty() ?
                Response.noContent().build() :
                Response.ok().entity(scheduleDtoList).build();
    }

    // Returns the default schedule (from the settings) without appointments
    @GET
    @Path("/clinic")
    public Response getScheduleForClinic() {
        Schedule schedule = scheduleRepository.getByClinic(settings.getClinicId());
        ScheduleDto scheduleDto = Mapper.map(schedule);

        return scheduleDto == null ?
                Response.noContent().build() :
                Response.ok().entity(scheduleDto).build();
    }

    // Return schedule with appointments
    @GET
    @Path("/{scheduleId}")
    public Response getScheduleWithAppointments(@PathParam("scheduleId") UUID scheduleId) {
        Schedule schedule = scheduleRepository.getById(scheduleId);
        ScheduleDto scheduleDto = Mapper.map(schedule);
        if (schedule != null) {
            scheduleDto.setAppointments(map(schedule.getAppointments()));
        }
        return scheduleDto == null ?
                Response.noContent().build() :
                Response.ok().entity(scheduleDto).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addSchedule(CreateScheduleRequest request) throws URISyntaxException {
        Schedule schedule =
                new Schedule(UUID.randomUUID(), request.clinicId(), request.start(), request.end());
        schedule = scheduleRepository.add(schedule);
        scheduleRepository.saveChanges(schedule);

        URI createdURI = new URI(schedule.getId().toString());
        var dto = new ScheduleDto(
                schedule.getId(),
                schedule.getClinicId(),
                schedule.getDateRange().getStart().getLocalDateTime(),
                schedule.getDateRange().getStart().getLocalDateTime()
        );

        return Response.created(createdURI).entity(dto).build();
    }

    @DELETE
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteSchedule(Schedule schedule) {
        scheduleRepository.remove(schedule);
        scheduleRepository.saveChanges(schedule);

        return Response.noContent().build();
    }

    @GET
    @Path("/{scheduleId}/appointments/{appointmentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAppointmentById(@PathParam("scheduleId") UUID scheduleId, @PathParam("appointmentId") UUID id) {
        var schedule = scheduleRepository.getById(scheduleId);
        var appointment = schedule.getAppointment(id);

        var dto = map(appointment);
        return Response.ok().entity(dto).build();
    }

    @POST
    @Path("/{scheduleId}/appointments")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addAppointment(@PathParam("scheduleId") UUID scheduleId, CreateAppointRequest request) throws URISyntaxException {
        var schedule = scheduleRepository.getById(scheduleId);

        var appointmentType = appointmentTypeRepository.getById(request.appointmentTypeId());
        DateTimeOffset dateOfAppointment = new DateTimeOffset(request.dateOfAppointment());
        var newAppointment = new Appointment(UUID.randomUUID(), request.scheduleId(), appointmentType, dateOfAppointment,
                request.clientId(), request.selectedDoctor(), request.patientId(), request.roomId(), request.title());

        schedule.addNewAppointment(newAppointment);

        scheduleRepository.alter(schedule);
        scheduleRepository.saveChanges(schedule);

        var dto = map(newAppointment);
        URI createdURI = new URI(schedule.getId().toString());
        return Response.created(createdURI).entity(dto).build();
    }

    @PUT
    @Path("/{scheduleId}/appointments")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAppointment(@PathParam("scheduleId") UUID scheduleId, UpdateAppointmentRequest request) {
        var schedule = scheduleRepository.getById(scheduleId);

        var appointmentType = appointmentTypeRepository.getById(request.appointmentTypeId());
        UUID aptId = request.id();
        var aptToUpdate = schedule.getAppointment(aptId);

        assert aptToUpdate != null;
        aptToUpdate.updateAppointmentType(appointmentType, schedule::appointmentUpdatedHandler);
        aptToUpdate.updateRoom(request.roomId());
        aptToUpdate.updateStartTime(request.start(), schedule::appointmentUpdatedHandler);
        aptToUpdate.updateTitle(request.title());
        aptToUpdate.updateDoctor(request.doctorId());

        scheduleRepository.alter(schedule);
        scheduleRepository.saveChanges(schedule);

        var dto = map(aptToUpdate);
        return Response.ok(dto).build();
    }

    @DELETE
    @Path("/{scheduleId}/appointments")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteAppointment(@PathParam("scheduleId") UUID scheduleId, DeleteAppointmentRequest request) {
        var schedule = scheduleRepository.getById(scheduleId);

        UUID aptId = request.appointmentId();
        var aptToDelete = schedule.getAppointment(aptId);

        schedule.deleteAppointment(aptToDelete);

        scheduleRepository.alter(schedule);
        scheduleRepository.saveChanges(schedule);

        ScheduleDto scheduleDto = Mapper.map(schedule);
        scheduleDto.setAppointments(map(schedule.getAppointments()));
        return Response.ok().entity(scheduleDto).build();
    }


    private List<AppointmentDto> map(List<Appointment> appointments) {
        return appointments.stream().map(this::map).toList();
    }

    private AppointmentDto map(Appointment appointment) {
        if (appointment == null) return null;

        AppointmentDto appointmentDto = Mapper.map(appointment);
        Client client = clientRepository.getById(appointment.getClientId());
        if (client != null) {
            appointmentDto.clientName = client.getPreferredName();
            appointmentDto.patientName = client.getPatientName(appointment.getPatientId());
        }
        AppointmentType appointmentType = appointmentTypeRepository.getById(appointment.getAppointmentTypeId());
        appointmentDto.appointmentType = Mapper.map(appointmentType);

        return appointmentDto;
    }

}
