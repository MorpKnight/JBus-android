package com.GiovanChristoffelSihombingJBusRS.jbus_android.model;

public class Account extends Serializable {
    public String name;
    public String email;
    public String password;
    public double balance;
    public Renter company;

    public Account(String name, String email, String password, double balance, Renter company) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.company = company;
    }
}
