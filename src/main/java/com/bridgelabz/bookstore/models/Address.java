package com.bridgelabz.bookstore.models;

import javax.persistence.Embeddable;

/**
 * Embedded model address which contains several fields and follows
 * Builder design pattern.
 *
 * @author Durgasankar Mishra
 * @version 1.0
 * @created 2020-04-12
 * @see {@link UserEntity}
 */
@Embeddable
public class Address {

    private String street;
    private String town;
    private String district;
    private String state;
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

    @Override
    public String toString() {
        return "AddressDto{" +
                "street='" + street + '\'' +
                ", town='" + town + '\'' +
                ", district='" + district + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", pinCode=" + pinCode +
                '}';
    }
}
