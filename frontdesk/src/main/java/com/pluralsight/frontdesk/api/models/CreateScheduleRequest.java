package com.pluralsight.frontdesk.api.models;

import java.time.LocalDateTime;

public record CreateScheduleRequest(Integer clinicId, LocalDateTime start, LocalDateTime end) { }
