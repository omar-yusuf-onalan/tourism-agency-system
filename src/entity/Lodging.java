package entity;

import business.HotelManager;

public class Lodging {
    private int lodgingID;
    private int hotelID;
    private String type;

    public Lodging() {}

    public Lodging(int lodgingID, int hotelID, String type) {
        this.lodgingID = lodgingID;
        this.hotelID = hotelID;
        this.type = type;
    }

    public int getLodgingID() {
        return lodgingID;
    }

    public void setLodgingID(int lodgingID) {
        this.lodgingID = lodgingID;
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
