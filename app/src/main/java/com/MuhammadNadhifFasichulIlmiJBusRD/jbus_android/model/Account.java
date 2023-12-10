package com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.model;

// Tanpa import tambahan karena tidak ada perubahan pada import yang diberikan

/**
 * Represents a user account in the JBus Android application.
 * Implements Serializable for supporting serialization.
 */
public class Account extends Serializable {

    /**
     * The name of the account holder.
     */
    public String name;

    /**
     * The email associated with the account.
     */
    public String email;

    /**
     * The password for the account.
     */
    public String password;

    /**
     * The current balance in the account.
     */
    public double balance;

    /**
     * The company details associated with the account (if applicable).
     * For example, in the case of a renter account, this may represent the renting company.
     */
    public Renter company;
}
