package entity;

public class Price {
    private int priceID;
    private int lodgingID;
    private int roomID;
    private double winterAdultPrice;
    private double winterChildPrice;
    private double summerAdultPrice;
    private double summerChildPrice;

    public Price() {}
    public Price(int priceID, int lodgingID, int roomID, double winterAdultPrice, double winterChildPrice, double summerAdultPrice, double summerChildPrice) {
        this.priceID = priceID;
        this.lodgingID = lodgingID;
        this.roomID = roomID;
        this.winterAdultPrice = winterAdultPrice;
        this.winterChildPrice = winterChildPrice;
        this.summerAdultPrice = summerAdultPrice;
        this.summerChildPrice = summerChildPrice;
    }

    public int getPriceID() {
        return priceID;
    }

    public void setPriceID(int priceID) {
        this.priceID = priceID;
    }

    public int getLodgingID() {
        return lodgingID;
    }

    public void setLodgingID(int lodgingID) {
        this.lodgingID = lodgingID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public double getWinterAdultPrice() {
        return winterAdultPrice;
    }

    public void setWinterAdultPrice(double winterAdultPrice) {
        this.winterAdultPrice = winterAdultPrice;
    }

    public double getWinterChildPrice() {
        return winterChildPrice;
    }

    public void setWinterChildPrice(double winterChildPrice) {
        this.winterChildPrice = winterChildPrice;
    }

    public double getSummerAdultPrice() {
        return summerAdultPrice;
    }

    public void setSummerAdultPrice(double summerAdultPrice) {
        this.summerAdultPrice = summerAdultPrice;
    }

    public double getSummerChildPrice() {
        return summerChildPrice;
    }

    public void setSummerChildPrice(double summerChildPrice) {
        this.summerChildPrice = summerChildPrice;
    }
}
