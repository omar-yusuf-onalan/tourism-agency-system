package entity;

public class Hotel {
    private int hotelID;
    private String name;
    private String city;
    private String region;
    private String address;
    private String email;
    private String telephone;
    private String star;

    public Hotel() {}

    public Hotel(int hotelID, String name, String city, String region, String address, String email, String telephone, String star) {
        this.hotelID = hotelID;
        this.name = name;
        this.city = city;
        this.region = region;
        this.address = address;
        this.email = email;
        this.telephone = telephone;
        this.star = star;
    }

    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }
}
