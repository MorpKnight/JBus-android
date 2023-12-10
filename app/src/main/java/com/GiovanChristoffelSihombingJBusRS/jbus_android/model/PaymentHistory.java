package com.GiovanChristoffelSihombingJBusRS.jbus_android.model;

import java.util.ArrayList;
import java.util.List;

public class PaymentHistory {
    public String busName;
    public String route;
    public String date;
    public double price;
    public Payment payment;
    public Bus bus;
    public static List<PaymentHistory> paymentHistories = new ArrayList<>();

    public PaymentHistory(Bus bus, Payment payment) {
        this.payment = payment;
        this.bus = bus;
        this.busName = bus.name;
        this.route = bus.departure.stationName + " - " + bus.arrival.stationName;
        this.date = payment.departureDate;
        this.price = bus.price.price;
    }
}
