package dao;

import core.DB;
import core.Utility;
import entity.Facility;
import entity.Hotel;

import java.sql.*;
import java.util.ArrayList;

public class FacilityDAO {
    private final Connection connection;

    public FacilityDAO() {
        this.connection = DB.getInstance();
    }

    public ArrayList<Facility> getList() {
        ArrayList<Facility> facilityList = new ArrayList<>();
        String query = "SELECT * FROM facility";

        try {
            Statement st = this.connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                facilityList.add(this.match(rs));
            }

            st.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return facilityList;
    }

    public ArrayList<Facility> getListByHotelID(int hotelID) {
        ArrayList<Facility> facilityList = new ArrayList<>();
        String query = "SELECT * FROM facility WHERE hotel_id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, hotelID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                facilityList.add(this.match(rs));
            }

            ps.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return facilityList;
    }

    public boolean add(Facility facility) {
        String query = "INSERT INTO facility (hotel_id, type) VALUES (?,?)";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);

            this.match(ps, facility);

            int response = ps.executeUpdate();
            ps.close();

            return response != -1;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public Facility findByFacilityID(int facilityID) {
        Facility obj = null;
        String query = "SELECT * FROM facility WHERE facility_id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, facilityID);
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

    public Facility findByHotelID(int hotelID) {
        Facility obj = null;
        String query = "SELECT * FROM facility WHERE hotel_id = ?";

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

    public Facility findByHotelIDAndType(int hotelID, String type) {
        Facility obj = null;
        String query = "SELECT * FROM facility WHERE hotel_id = ? AND type = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, hotelID);
            ps.setString(2, type);
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

    public boolean delete(int facilityID) {
        String query = "DELETE FROM facility WHERE facility_id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, facilityID);

            return ps.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public boolean update(Facility facility) {
        String query = "UPDATE facility SET hotel_id = ?, type = ? WHERE facility_id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);

            this.match(ps, facility);

            ps.setInt(3, facility.getFacilityID());

            int response = ps.executeUpdate();

            ps.close();

            return response != -1;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public ArrayList<Facility> searchList(Facility facility) {
        ArrayList<Facility> facilityList = new ArrayList<>();

        String[] searchCriteria = {
                "hotel_id", String.valueOf(facility.getHotelID()),
                "type", facility.getType()
        };

        String query = Utility.createSearchQuery("facility", searchCriteria, "AND");


        try {
            Statement st = this.connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                facilityList.add(this.match(rs));
            }

            st.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return facilityList;
    }

    public Facility match(ResultSet rs) throws SQLException {
        Facility obj = new Facility();
        obj.setFacilityID(rs.getInt("facility_id"));
        obj.setHotelID(rs.getInt("hotel_id"));
        obj.setType(rs.getString("type"));
        return obj;
    }

    public void match(PreparedStatement ps, Facility facility) throws SQLException {
        ps.setInt(1, facility.getHotelID());
        ps.setString(2, facility.getType());
    }
}
