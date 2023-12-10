package com.GiovanChristoffelSihombingJBusRS.jbus_android.model;

import java.util.ArrayList;
import java.util.List;

public class PaymentHistory {
    public String busName;
    public String route;
    public String date;
    public String price;
    public static List<PaymentHistory> paymentHistories = new ArrayList<>();

    public PaymentHistory(String busName, String route, String date, String price) {
        this.busName = busName;
        this.route = route;
        this.date = date;
        this.price = price;
    }
}
