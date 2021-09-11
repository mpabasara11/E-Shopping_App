package com.example.coursework.Models;

public class Orders {
    String address, email, name, phone, town, date;

    public Orders() {

    }

    public Orders(String address, String email, String name, String phone, String town, String date) {
        this.address = address;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.town = town;
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
