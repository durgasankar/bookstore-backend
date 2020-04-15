package com.bridgelabz.bookstore.models;

import javax.persistence.*;

/**
 * one to many mapped address which contains several fields and follows
 *
 * @author Durgasankar Mishra
 * @version 1.0
 * @created 2020-04-15
 * @see {@link UserEntity}
 */
@Entity
@Table(name = "users_addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private long addressId;
    @Column(length = 25)
    private String street;
    @Column(length = 25)
    private String town;
    @Column(length = 25)
    private String district;
    @Column(length = 25)
    private String state;
    @Column(length = 25)
    private String country;
    private int pinCode;

    public Address() { }

    public String getStreet() { return street; }

    public void setStreet( String street ) { this.street = street; }

    public String getTown() { return town; }

    public void setTown( String town ) { this.town = town; }

    public String getDistrict() { return district; }

    public void setDistrict( String district ) { this.district = district; }

    public String getState() { return state; }

    public void setState( String state ) { this.state = state; }

    public String getCountry() { return country; }

    public void setCountry( String country ) { this.country = country; }

    public int getPinCode() { return pinCode; }

    public void setPinCode( int pinCode ) { this.pinCode = pinCode; }

    public long getAddressId() { return addressId; }

    public void setAddressId( long addressId ) { this.addressId = addressId; }

    @Override
    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", street='" + street + '\'' +
                ", town='" + town + '\'' +
                ", district='" + district + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", pinCode=" + pinCode +
                '}';
    }
}
