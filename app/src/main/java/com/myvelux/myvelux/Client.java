package com.myvelux.myvelux;

import android.telephony.PhoneNumberUtils;

import java.io.Serializable;

/**
 * Created by geoffrey on 21/04/16.
 * Modified by julien on 20/05/16.
 */
public class Client implements Serializable {

    private String id;
    private String LastName;
    private String firstName;
    private String address;
    private String postalCode;
    private String city;
    private String mobile;
    private String phone;
    private String email;

    public Client() {
    }

    public Client(String id, String lastName, String firstName, String address, Integer postalCode, String city, Integer mobile, Integer phone, String email) {
        this.id = id;
        LastName = lastName;
        this.firstName = firstName;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.mobile = mobile;
        this.phone = phone;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
