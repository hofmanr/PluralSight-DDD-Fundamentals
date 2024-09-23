package com.pluralsight.frontdesk.api;

import com.pluralsight.frontdesk.core.interfaces.ApplicationSettings;
import jakarta.enterprise.context.Dependent;
import pluralsightddd.sharedkernel.core.valueobjects.DateTimeOffset;

// These can be read from config but for demo purposes are hard-coded here.
@Dependent
public class OfficeSettings implements ApplicationSettings {
    @Override
    public Integer getClinicId() {
        return 1;
    }

    @Override
    public DateTimeOffset getTestDate() {
        return new DateTimeOffset(2030, 9, 23, 0, 0, 0, false);
    }
}
