package com.pluralsight.frontdesk.infrastructure.models;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Confirmation response
 * Example JSON object:
 * {
 *     "eventType": "confirmationResponse",
 *     "appointmentId": "3433-54456-54354-23212",
 *     "dateTimeEventOccurred": "2024-07-14 12:30:23"
 * }
 */
public class ConfimationResponseDto  {
    public String eventType;
    public UUID appointmentId;
    public LocalDateTime dateTimeEventOccurred;

}
