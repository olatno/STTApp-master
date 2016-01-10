package com.space.time.android.tracker.bean;

/**
 * Created by olatu on 05/01/2016.
 */
public class ReservationArrival extends Reservation{

    String name;
    String arriDate;
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
}
