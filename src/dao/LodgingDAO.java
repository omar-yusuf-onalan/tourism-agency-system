package dao;

import core.DB;
import core.Utility;
import entity.Facility;
import entity.Lodging;

import java.sql.*;
import java.util.ArrayList;

public class LodgingDAO {
    private final Connection connection;

    public LodgingDAO() {
        this.connection = DB.getInstance();
    }

    public ArrayList<Lodging> getList() {
        ArrayList<Lodging> lodgingList = new ArrayList<>();
        String query = "SELECT * FROM lodging";

        try {
            Statement st = this.connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                lodgingList.add(this.match(rs));
            }

            st.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return lodgingList;
    }

    public ArrayList<Lodging> getListByHotelID(int hotelID) {
        ArrayList<Lodging> lodgingList = new ArrayList<>();
        String query = "SELECT * FROM lodging WHERE hotel_id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, hotelID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lodgingList.add(this.match(rs));
            }

            ps.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return lodgingList;
    }

    public boolean add(Lodging lodging) {
        String query = "INSERT INTO lodging (hotel_id, type) VALUES (?,?)";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);

            this.match(ps, lodging);

            int response = ps.executeUpdate();
            ps.close();

            return response != -1;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public Lodging findByLodgingID(int lodgingID) {
        Lodging obj = null;
        String query = "SELECT * FROM lodging WHERE lodging_id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, lodgingID);
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

    public Lodging findByHotelID(int hotelID) {
        Lodging obj = null;
        String query = "SELECT * FROM lodging WHERE hotel_id = ?";

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

    public Lodging findByHotelIDAndType(int hotelID, String type) {
        Lodging obj = null;
        String query = "SELECT * FROM lodging WHERE hotel_id = ? AND type = ?";

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

    public boolean delete(int lodgingID) {
        String query = "DELETE FROM lodging WHERE lodging_id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, lodgingID);

            return ps.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public boolean update(Lodging lodging) {
        String query = "UPDATE lodging SET hotel_id = ?, type = ?, number_available = ? WHERE lodging_id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);

            this.match(ps, lodging);

            ps.setInt(3, lodging.getLodgingID());

            int response = ps.executeUpdate();

            ps.close();

            return response != -1;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public ArrayList<Lodging> searchList(Lodging lodging) {
        ArrayList<Lodging> lodgingList = new ArrayList<>();

        String[] searchCriteria = {
            "hotel_id", String.valueOf(lodging.getHotelID()),
            "type", lodging.getType()
        };

        String query = Utility.createSearchQuery("lodging", searchCriteria, "AND");

        try {
            Statement st = this.connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                lodgingList.add(this.match(rs));
            }

            st.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return lodgingList;
    }

    public Lodging match(ResultSet rs) throws SQLException {
        Lodging obj = new Lodging();
        obj.setLodgingID(rs.getInt("lodging_id"));
        obj.setHotelID(rs.getInt("hotel_id"));
        obj.setType(rs.getString("type"));
        return obj;
    }

    public void match(PreparedStatement ps, Lodging lodging) throws SQLException {
        ps.setInt(1, lodging.getHotelID());
        ps.setString(2, lodging.getType());
    }
}
