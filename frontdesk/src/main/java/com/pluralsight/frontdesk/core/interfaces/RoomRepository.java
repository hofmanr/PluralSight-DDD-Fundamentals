package com.pluralsight.frontdesk.core.interfaces;

import com.pluralsight.frontdesk.core.syncedaggregates.Room;
import pluralsightddd.sharedkernel.core.repositories.ReadonlyRepository;

public interface RoomRepository extends ReadonlyRepository<Room> {
}
