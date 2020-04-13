package com.bridgelabz.bookstore.dto;

import com.bridgelabz.bookstore.models.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * User Registration DTO class which has the parameters which user will give the data
 *
 * @author Durgasankar Mishra
 * @version 1.1
 * @created 2020-01-22
 */
public class UserDto {
    @Pattern(regexp = "[a-zA-Z]*", message = "only alphabets are allowed")
    private String firstName;
    @Pattern(regexp = "[a-zA-Z]*", message = "only alphabets are allowed")
    private String lastName;
    @Email
    private String emailId;
    private String password;
    @NotNull(message = "field should not be empty")
    private long mobileNumber;
    private String userName;
    private List<Role> roles;

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

    public long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber( long mobileNumber ) {
        this.mobileNumber = mobileNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName( String userName ) {
        this.userName = userName;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles( List<Role> roles ) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailId='" + emailId + '\'' +
                ", password='" + password + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", userName='" + userName + '\'' +
                ", roles=" + roles +
                '}';
    }
}
