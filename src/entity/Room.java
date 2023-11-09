package entity;

public class Room {
    private int roomID;
    private int hotelID;
    private int lodgingID;
    private int periodID;
    private String name;
    private int numberOfBeds;
    private String item;
    private String squareMeter;
    private int stock;


    public Room() {}

    public Room(int roomID, int hotelID, int periodID, String name, int numberOfBeds, String item, String squareMeter, int stock) {
        this.roomID = roomID;
        this.hotelID = hotelID;
        this.periodID = periodID;
        this.name = name;
        this.numberOfBeds = numberOfBeds;
        this.item = item;
        this.squareMeter = squareMeter;
        this.stock = stock;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }


    public int getPeriodID() {
        return periodID;
    }

    public void setPeriodID(int periodID) {
        this.periodID = periodID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getSquareMeter() {
        return squareMeter;
    }

    public void setSquareMeter(String squareMeter) {
        this.squareMeter = squareMeter;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
