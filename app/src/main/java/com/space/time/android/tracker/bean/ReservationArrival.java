package com.space.time.android.tracker.bean;

/**
 * Created by olatu on 05/01/2016.
 */
public class ReservationArrival {

    String name;
    String arriDate;
    String reservationDate;
    String mobileNumber;
    String email;
    String channel;
    String packageBooked;
    String numberReversed;

    public ReservationArrival(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArriDate() {
        return arriDate;
    }

    public void setArriDate(String arriDate) {
        this.arriDate = arriDate;
    }

    public String getPackageBooked() {
        return packageBooked;
    }

    public void setPackageBooked(String packageBooked) {
        this.packageBooked = packageBooked;
    }

    public String getNumberReversed() {
        return numberReversed;
    }

    public void setNumberReversed(String numberReversed) {
        this.numberReversed = numberReversed;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
