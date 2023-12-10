package com.GiovanChristoffelSihombingJBusRS.jbus_android.model;

public class LoggedAccount extends Account{

    public static Account loggedAccount = null;
    public static String email, password;

    public LoggedAccount(String name, String email, String password, double balance, Renter company) {
        super(name, email, password, balance, company);
    }
}
