/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtvn.pojo;

/**
 *
 * @author datph
 */
public class Tickets {

    private int ticketID;
    private String ticketsName;
    private String tripName;
    private String licensePlates;
    private String departure;
    private String destination;
    private String date;
    private String time;
    private String seat;
    private double price;
    private String lastNamePassenger;
    private String firstNamePassenger;
    private String sex;
    private String phone;
    private String address;
    private String note;

    public Tickets(int ticketID, String ticketsName, String tripName, String licensePlates, String departure, String destination, String date, String time, String seat, double price, String lastNamePassenger, String firstNamePassenger, String sex, String phone, String address, String note) {
        this.ticketID = ticketID;
        this.ticketsName = ticketsName;
        this.tripName = tripName;
        this.licensePlates = licensePlates;
        this.departure = departure;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.seat = seat;
        this.price = price;
        this.lastNamePassenger = lastNamePassenger;
        this.firstNamePassenger = firstNamePassenger;
        this.sex = sex;
        this.phone = phone;
        this.address = address;
        this.note = note;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public void setTicketsName(String ticketsName) {
        this.ticketsName = ticketsName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public void setLicensePlates(String licensePlates) {
        this.licensePlates = licensePlates;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setLastNamePassenger(String lastNamePassenger) {
        this.lastNamePassenger = lastNamePassenger;
    }

    public void setFirstNamePassenger(String firstNamePassenger) {
        this.firstNamePassenger = firstNamePassenger;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getTicketID() {
        return ticketID;
    }

    public String getTicketsName() {
        return ticketsName;
    }

    public String getTripName() {
        return tripName;
    }

    public String getLicensePlates() {
        return licensePlates;
    }

    public String getDeparture() {
        return departure;
    }

    public String getDestination() {
        return destination;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getSeat() {
        return seat;
    }

    public double getPrice() {
        return price;
    }

    public String getLastNamePassenger() {
        return lastNamePassenger;
    }

    public String getFirstNamePassenger() {
        return firstNamePassenger;
    }

    public String getSex() {
        return sex;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getNote() {
        return note;
    }
}
