package com.GiovanChristoffelSihombingJBusRS.jbus_android.model;

public class Account extends Serializable {
    public String name;
    public String email;
    public String password;
    public double balance;
    public Renter company;

    public Account(String username, String email, String password, double balance, Object o) {
        super();
        this.name = username;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.company = (Renter) o;
    }
}
