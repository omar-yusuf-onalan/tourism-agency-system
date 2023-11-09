package entity;

import java.sql.Date;

public class Reservation {
    private int reservationID;
    private int roomID;
    private String contactName;
    private String contactTelephone;
    private String contactEmail;
    private String note;
    private String adultInformation;
    private String childInformation;
    private Date arrival;
    private Date departure;

    public Reservation() {}

    public Reservation(int reservationID, int roomID, String contactName, String contactTelephone, String contactEmail, String note, String adultInformation, String childInformation, Date arrival, Date departure) {
        this.reservationID = reservationID;
        this.roomID = roomID;
        this.contactName = contactName;
        this.contactTelephone = contactTelephone;
        this.contactEmail = contactEmail;
        this.note = note;
        this.adultInformation = adultInformation;
        this.childInformation = childInformation;
        this.arrival = arrival;
        this.departure = departure;
    }

    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactTelephone() {
        return contactTelephone;
    }

    public void setContactTelephone(String contactTelephone) {
        this.contactTelephone = contactTelephone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAdultInformation() {
        return adultInformation;
    }

    public void setAdultInformation(String adultInformation) {
        this.adultInformation = adultInformation;
    }

    public String getChildInformation() {
        return childInformation;
    }

    public void setChildInformation(String childInformation) {
        this.childInformation = childInformation;
    }

    public Date getArrival() {
        return arrival;
    }

    public void setArrival(Date arrival) {
        this.arrival = arrival;
    }

    public Date getDeparture() {
        return departure;
    }

    public void setDeparture(Date departure) {
        this.departure = departure;
    }
}
