package com.GiovanChristoffelSihombingJBusRS.jbus_android.model;

public class LoggedAccount extends Account{

    public static Account loggedAccount = null;

    public LoggedAccount(String name, String email, String password, double balance, Renter company) {
        super(name, email, password, balance, company);
    }
}
