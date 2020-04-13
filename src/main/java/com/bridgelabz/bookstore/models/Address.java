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

    private final String street;
    private final String town;
    private final String district;
    private final String state;
    private final String country;
    private final int pinCode;

    public Address( Builder builder ) {
        this.street = builder.street;
        this.town = builder.town;
        this.district = builder.district;
        this.state = builder.state;
        this.country = builder.country;
        this.pinCode = builder.pinCode;
    }

    public static class Builder {
        private String street;
        private String town;
        private String district;
        private String state;
        private String country;
        private int pinCode;

        public Address createAddress() {
            return new Address (this);
        }

        public Builder setStreet( String street ) {
            this.street = street;
            return this;
        }

        public Builder town( String town ) {
            this.town = town;
            return this;
        }

        public Builder district( String district ) {
            this.district = district;
            return this;
        }

        public Builder state( String state ) {
            this.state = state;
            return this;
        }

        public Builder country( String country ) {
            this.country = country;
            return this;
        }

        public Builder pinCode( int pinCode ) {
            this.pinCode = pinCode;
            return this;
        }
    }

    public String getStreet() {
        return street;
    }

    public String getTown() {
        return town;
    }

    public String getDistrict() {
        return district;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public int getPinCode() {
        return pinCode;
    }


}
