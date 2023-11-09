package dao;

import core.DB;
import core.Utility;
import entity.Price;
import entity.Reservation;
import entity.User;

import java.sql.*;
import java.util.ArrayList;

public class ReservationDAO {
    private final Connection connection;

    public ReservationDAO() {
        this.connection = DB.getInstance();
    }

    public ArrayList<Reservation> getList() {
        String query = "SELECT * FROM reservation";

        ArrayList<Reservation> reservationList = new ArrayList<>();
        try {
            Statement st = this.connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                reservationList.add(this.match(rs));
            }

            st.close();
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return reservationList;
    }

    public ArrayList<Reservation> getListByRoomID(int roomID) {
        String query = "SELECT * FROM reservation WHERE room_id = ?";

        ArrayList<Reservation> reservationList = new ArrayList<>();
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, roomID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                reservationList.add(this.match(rs));
            }

            ps.close();
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return reservationList;
    }

    public Reservation findByReservationID(int reservationID) {
        Reservation obj = null;
        String query = "SELECT * FROM reservation WHERE reservation_id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, reservationID);
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

    public boolean add(Reservation reservation) {
        String query = "INSERT INTO reservation (reservation_id, room_id, contact_name, contact_telephone, contact_email, " +
                "note, adult_information, child_information, arrival, departure) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);

            this.match(ps, reservation);

            int response = ps.executeUpdate();

            ps.close();

            return response != -1;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public boolean delete(int reservationID) {
        String query = "DELETE FROM reservation WHERE reservation_id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, reservationID);

            return ps.executeUpdate() != -1;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return true;
    }

    public boolean deleteByRoomID(int roomID) {
        String query = "DELETE FROM reservation WHERE room_id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, roomID);

            return ps.executeUpdate() != -1;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return true;
    }

    public Reservation match(ResultSet rs) throws SQLException {
        Reservation obj = new Reservation();
        obj.setReservationID(rs.getInt("reservation_id"));
        obj.setRoomID(rs.getInt("room_id"));
        obj.setContactName(rs.getString("contact_name"));
        obj.setContactTelephone(rs.getString("contact_telephone"));
        obj.setContactEmail(rs.getString("contact_email"));
        obj.setNote(rs.getString("note"));
        obj.setAdultInformation(rs.getString("adult_information"));
        obj.setChildInformation(rs.getString("child_information"));
        obj.setArrival(rs.getDate("arrival"));
        obj.setDeparture(rs.getDate("departure"));

        return obj;
    }

    public void match(PreparedStatement ps, Reservation reservation) throws SQLException {
        ps.setInt(1, reservation.getReservationID());
        ps.setInt(2, reservation.getRoomID());
        ps.setString(3, reservation.getContactName());
        ps.setString(4, reservation.getContactTelephone());
        ps.setString(5, reservation.getContactEmail());
        ps.setString(6, reservation.getNote());
        ps.setString(7, reservation.getAdultInformation());
        ps.setString(8, reservation.getChildInformation());
        ps.setDate(9, reservation.getArrival());
        ps.setDate(10, reservation.getDeparture());
    }
}
