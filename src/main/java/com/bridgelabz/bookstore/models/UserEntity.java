package com.bridgelabz.bookstore.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;
    private String firstName;
    private String lastName;
    @Size(min = 4, max = 255, message = "Minimum username length: 4 characters")
    @Column(unique = true, nullable = false)
    private String userName;
    @Column(length = 10, unique = true)
    private long mobileNumber;
    @Column(unique = true)
    @Email
    private String emailId;
    private String password;
    @Column(length = 30)
    private String createdDateTime;
    private boolean isVerified;
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "user_addresses", joinColumns = @JoinColumn(name = "user_id"))
    private List<Address> addresses;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Role> roles;

    public UserEntity() {
        this.addresses = new ArrayList<>();
        this.roles = new LinkedList<>();
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId( long userId ) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName( String firstName ) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName( String lastName ) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName( String userName ) {
        this.userName = userName;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber( long mobileNumber ) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId( String emailId ) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword( String password ) {
        this.password = password;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime( String createdDateTime ) {
        this.createdDateTime = createdDateTime;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified( boolean verified ) {
        isVerified = verified;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses( List<Address> addresses ) {
        this.addresses = addresses;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles( List<Role> roles ) {
        this.roles = roles;
    }
}
