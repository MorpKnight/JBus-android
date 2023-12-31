package com.GiovanChristoffelSihombingJBusRS.jbus_android.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Bus extends Serializable {
    public int accountId;
    public String name;
    public List<Facility> facilities;
    public Price price;
    public int capacity;
    public BusType busType;
    public Station departure;
    public Station arrival;
    public List<Schedule> schedules;

    public Bus(int accountId, String name, List<Facility> facilities, Price price, int capacity, BusType busType, Station departure, Station arrival, List<Schedule> schedules) {
        this.accountId = accountId;
        this.name = name;
        this.facilities = facilities;
        this.price = price;
        this.capacity = capacity;
        this.busType = busType;
        this.departure = departure;
        this.arrival = arrival;
        this.schedules = schedules;
    }

//    public static List<Bus> sampleBusList(int size) {
//        List<Bus> busList = new ArrayList<>();
//
//        for (int i = 1; i <= size; i++) {
//            Bus bus = new Bus();
//            bus.name = "Bus " + i;
//            busList.add(bus);
//        }
//
//        return busList;
//    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
