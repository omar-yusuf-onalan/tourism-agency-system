package entity;

import business.HotelManager;

public class Facility {
    private int facilityID;
    private int hotelID;
    private String type;

    public Facility() {}

    public Facility(int facilityID, int hotelID, String type) {
        this.facilityID = facilityID;
        this.hotelID = hotelID;
        this.type = type;
    }

    public int getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(int facilityID) {
        this.facilityID = facilityID;
    }

    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
