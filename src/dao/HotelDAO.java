package dao;

import core.DB;
import core.Utility;
import entity.Hotel;

import java.sql.*;
import java.util.ArrayList;

public class HotelDAO {
    private final Connection connection;

    public HotelDAO() {
        this.connection = DB.getInstance();
    }

    public ArrayList<Hotel> getList() {
        ArrayList<Hotel> hotelList = new ArrayList<>();
        String query = "SELECT * FROM hotel";

        try {
            Statement st = this.connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                hotelList.add(this.match(rs));
            }

            st.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return hotelList;
    }

    public boolean add(Hotel hotel) {
        String query = "INSERT INTO hotel (name, city, region, address, email, telephone, star) VALUES (?,?,?,?,?,?,?)";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);

            this.match(ps, hotel);

            int response = ps.executeUpdate();

            ps.close();

            return response != -1;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public Hotel findByHotelID(int hotelID) {
        Hotel obj = null;
        String query = "SELECT * FROM hotel WHERE hotel_id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, hotelID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                obj = this.match(rs);
            }

            ps.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return obj;
    }

    public Hotel findByEmail(String email) {
        Hotel obj = null;
        String query = "SELECT * FROM hotel WHERE email = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                this.match(rs);
            }

            ps.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return obj;
    }

    public boolean delete(int hotelID) {
        String query = "DELETE FROM hotel WHERE hotel_id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, hotelID);

            return ps.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public boolean update(Hotel hotel) {
        String query = "UPDATE hotel SET name = ?, city = ?, region = ?, address = ?, email = ?, telephone = ?, star = ? WHERE hotel_id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);

            this.match(ps, hotel);

            ps.setInt(8, hotel.getHotelID());

            int response = ps.executeUpdate();

            ps.close();

            return response != -1;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public ArrayList<Hotel> searchList(Hotel hotel) {
        ArrayList<Hotel> hotelList = new ArrayList<>();

        String[] searchCriteria = {"name", hotel.getName(), "city", hotel.getCity(), "region", hotel.getRegion(), "star", hotel.getStar()};
        String query = Utility.createSearchQuery("hotel", searchCriteria, "AND");

        try {
            Statement st = this.connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                hotelList.add(this.match(rs));
            }

            st.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return hotelList;
    }

    public ArrayList<Hotel> searchList(String value) {
        ArrayList<Hotel> hotelList = new ArrayList<>();

        String[] searchCriteria = {"name", value, "city", value, "region", value};
        String query = Utility.createSearchQuery("hotel", searchCriteria, "OR");

        try {
            Statement st = this.connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                hotelList.add(this.match(rs));
            }

            st.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return hotelList;
    }

    public Hotel match(ResultSet rs) throws SQLException {
        Hotel obj = new Hotel();
        obj.setHotelID(rs.getInt("hotel_id"));
        obj.setName(rs.getString("name"));
        obj.setCity(rs.getString("city"));
        obj.setRegion(rs.getString("region"));
        obj.setAddress(rs.getString("address"));
        obj.setEmail(rs.getString("email"));
        obj.setTelephone(rs.getString("telephone"));
        obj.setStar(rs.getString("star"));
        return obj;
    }

    public void match(PreparedStatement ps, Hotel hotel) throws SQLException {
        ps.setString(1, hotel.getName());
        ps.setString(2, hotel.getCity());
        ps.setString(3, hotel.getRegion());
        ps.setString(4, hotel.getAddress());
        ps.setString(5, hotel.getEmail());
        ps.setString(6, hotel.getTelephone());
        ps.setString(7, hotel.getStar());
    }
}
