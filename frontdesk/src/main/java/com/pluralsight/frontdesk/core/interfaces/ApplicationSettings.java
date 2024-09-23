package com.pluralsight.frontdesk.core.interfaces;

import pluralsightddd.sharedkernel.core.valueobjects.DateTimeOffset;

public interface ApplicationSettings {
    Integer getClinicId();
    DateTimeOffset getTestDate();

}
