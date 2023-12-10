package com.GiovanChristoffelSihombingJBusRS.jbus_android.model;

import java.util.ArrayList;
import java.util.List;

public class StoredBus extends Bus{
    public static List<Bus> storedBus = new ArrayList<>();

    public StoredBus(int accountId, String name, List<Facility> facilities, Price price, int capacity, BusType busType, Station departure, Station arrival, List<Schedule> schedules) {
        super(accountId, name, facilities, price, capacity, busType, departure, arrival, schedules);
    }
}
