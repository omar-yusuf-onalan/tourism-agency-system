package entity;

import business.HotelManager;

import java.sql.Date;

public class Period {
    int periodID;
    int hotelID;
    Date winterStart;
    Date winterEnd;
    Date summerStart;
    Date summerEnd;

    public Period() {}

    public Period(int periodID, int hotelID, Date winterStart, Date winterEnd, Date summerStart, Date summerEnd) {
        this.periodID = periodID;
        this.hotelID = hotelID;
        this.winterStart = winterStart;
        this.winterEnd = winterEnd;
        this.summerStart = summerStart;
        this.summerEnd = summerEnd;
    }

    public int getPeriodID() {
        return periodID;
    }

    public void setPeriodID(int periodID) {
        this.periodID = periodID;
    }

    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }

    public Date getWinterStart() {
        return winterStart;
    }

    public void setWinterStart(Date winterStart) {
        this.winterStart = winterStart;
    }

    public Date getWinterEnd() {
        return winterEnd;
    }

    public void setWinterEnd(Date winterEnd) {
        this.winterEnd = winterEnd;
    }

    public Date getSummerStart() {
        return summerStart;
    }

    public void setSummerStart(Date summerStart) {
        this.summerStart = summerStart;
    }

    public Date getSummerEnd() {
        return summerEnd;
    }

    public void setSummerEnd(Date summerEnd) {
        this.summerEnd = summerEnd;
    }
}
