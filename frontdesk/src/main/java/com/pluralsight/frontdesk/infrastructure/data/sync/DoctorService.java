package com.pluralsight.frontdesk.infrastructure.data.sync;

import com.pluralsight.frontdesk.infrastructure.data.sync.entities.Doctor;
import com.pluralsight.frontdesk.infrastructure.data.sync.model.DoctorDto;
import com.pluralsight.frontdesk.infrastructure.data.sync.repositories.DoctorRepository;
import jakarta.inject.Inject;
import org.slf4j.Logger;

public class DoctorService {
    private final Logger logger;
    private final DoctorRepository doctorRepository;

    @Inject
    public DoctorService(Logger logger, DoctorRepository doctorRepository) {
        this.logger = logger;
        this.doctorRepository = doctorRepository;
    }

    public void add(DoctorDto doctorDto) {
        logger.info("Add new Doctor: {}", doctorDto);
        // The Doctor is the synced-doctor!
        Doctor doctor = new Doctor(doctorDto.id(), doctorDto.name());

        doctorRepository.add(doctor);
    }
}
