package com.dtvn.pojo;

public class Trip {

    private int idTrip;
    private String nameTrip;
    private String departure;
    private String destination;
    private String date;
    private String time;
    private double price;

    public Trip(int idTrip, String nameTrip, String departure, String destination, String date, String time, double price) {
        this.idTrip = idTrip;
        this.nameTrip = nameTrip;
        this.departure = departure;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.price = price;
    }

    public void setIdTrip(int idTrip) {
        this.idTrip = idTrip;
    }

    public void setNameTrip(String nameTrip) {
        this.nameTrip = nameTrip;
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

    public void setPrice(double price) {
        this.price = price;
    }

    public int getIdTrip() {
        return idTrip;
    }

    public String getNameTrip() {
        return nameTrip;
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

    public double getPrice() {
        return price;
    }
}