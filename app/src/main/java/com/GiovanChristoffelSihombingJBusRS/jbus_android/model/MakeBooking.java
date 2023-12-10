package com.GiovanChristoffelSihombingJBusRS.jbus_android.model;

public class MakeBooking {
    public Payment payment;
    public Account account;

    public MakeBooking(Payment payment, Account account) {
        this.payment = payment;
        this.account = account;
    }
}
