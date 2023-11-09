package dao;

import business.PeriodManager;
import core.DB;
import core.Utility;
import entity.Room;

import java.sql.*;
import java.util.ArrayList;

public class RoomDAO {
    private final Connection connection;



    public RoomDAO() {
        this.connection = DB.getInstance();
    }

    public ArrayList<Room> getList() {
        ArrayList<Room> roomList = new ArrayList<>();
        String query = "SELECT * FROM room";

        try {
            Statement st = this.connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                roomList.add(this.match(rs));
            }

            st.close();
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return roomList;
    }

    public ArrayList<Room> getListByHotelID(int hotelID) {
        ArrayList<Room> roomList = new ArrayList<>();
        String query = "SELECT * FROM room WHERE hotel_id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, hotelID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                roomList.add(this.match(rs));
            }

            ps.close();
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return roomList;
    }

    public ArrayList<Room> getListByDates(Date customerArrival, Date customerDeparture) {
        ArrayList<Room> roomList = new ArrayList<>();
        String query = "SELECT room.room_id, room.hotel_id, room.period_id, room.name, room.number_of_beds, room.item, room.square_meter, room.stock\n" +
                "FROM room\n" +
                "INNER JOIN period ON room.period_id = period.period_id\n" +
                "WHERE\n" +
                "period.winter_start <= ?\n" +
                "  AND period.winter_end >= ?\n" +
                "OR period.summer_start <= ? AND period.summer_end >= ?" +
                "AND room.stock > 0";


        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setDate(1, customerArrival);
            ps.setDate(2, customerDeparture);
            ps.setDate(3, customerArrival);
            ps.setDate(4, customerDeparture);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                roomList.add(this.match(rs));
            }

            ps.close();
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return roomList;
    }

    public boolean add(Room room) {
        String query = "INSERT INTO room (hotel_id, period_id, name, number_of_beds, item, square_meter, stock) VALUES (?,?,?,?,?,?,?)";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);

            this.match(ps, room);

            int response = ps.executeUpdate();
            ps.close();

            return response != -1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
            }
        }

    public Room findByRoomID(int roomID) {
        Room obj = null;
        String query = "SELECT * FROM room WHERE room_id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, roomID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                obj = this.match(rs);
            }

            ps.close();
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return obj;
    }

    public Room findByHotelIDAndName(int hotelID, String name) {
        Room obj = null;
        String query = "SELECT * FROM room WHERE hotel_id = ? AND name = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, hotelID);
            ps.setString(2, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                obj = this.match(rs);
            }

            ps.close();
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return obj;
    }

    public boolean delete(int roomID) {
        String query = "DELETE FROM room WHERE room_id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, roomID);

            return ps.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean update(Room room) {
        String query = "UPDATE room SET hotel_id =?, period_id = ?, name = ?, number_of_beds = ?, item = ?, square_meter = ?, stock = ? WHERE room_id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);

            this.match(ps, room);

            ps.setInt(8, room.getRoomID());

            int response = ps.executeUpdate();
            ps.close();

            return response != -1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Room> searchList(Room room) {
        ArrayList<Room> roomList = new ArrayList<>();

        String query = getQuery(room);

        try {
            Statement st = this.connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                roomList.add(this.match(rs));
            }

            st.close();
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return roomList;
    }

    private static String getQuery(Room room) {
        String[] searchCriteria = {
                "hotel_id", String.valueOf(room.getHotelID()),
                "name", room.getName(),
        };

        return Utility.createSearchQuery("room", searchCriteria, "AND");
    }

    private Room match(ResultSet rs) throws SQLException {
            Room obj = new Room();
            obj.setRoomID(rs.getInt("room_id"));
            obj.setHotelID(rs.getInt("hotel_id"));
            obj.setPeriodID(rs.getInt("period_id"));
            obj.setName(rs.getString("name"));
            obj.setNumberOfBeds(rs.getInt("number_of_beds"));
            obj.setItem(rs.getString("item"));
            obj.setSquareMeter(rs.getString("square_meter"));
            obj.setStock(rs.getInt("stock"));
            return obj;
        }

    private void match(PreparedStatement ps, Room room) throws SQLException {
        ps.setInt(1, room.getHotelID());
        ps.setInt(2, room.getPeriodID());
        ps.setString(3, room.getName());
        ps.setInt(4, room.getNumberOfBeds());
        ps.setString(5, room.getItem());
        ps.setString(6, room.getSquareMeter());
        ps.setInt(7, room.getStock());
    }
}
