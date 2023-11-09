package dao;

import core.DB;
import entity.Lodging;
import entity.Period;

import java.sql.*;
import java.util.ArrayList;

public class PeriodDAO {
    private final Connection connection;

    public PeriodDAO() {
        this.connection = DB.getInstance();
    }

    public ArrayList<Period> getList() {
        ArrayList<Period> periodList = new ArrayList<>();
        String query = "SELECT * FROM period";

        try {
            Statement st = this.connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                periodList.add(this.match(rs));
            }

            st.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return periodList;
    }

    public boolean add(Period period) {
        String query = "INSERT INTO period (hotel_id, winter_start, winter_end, summer_start, summer_end) VALUES (?,?,?,?,?)";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);

            this.match(ps, period);

            int response = ps.executeUpdate();
            ps.close();

            return response != -1;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public Period findByPeriodID(int periodID) {
        Period obj = null;
        String query = "SELECT * FROM period WHERE period_id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, periodID);
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

    public Period findByHotelID(int hotelID) {
        Period obj = null;
        String query = "SELECT * FROM period WHERE hotel_id = ?";

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

    public boolean delete(int periodID) {
        String query = "DELETE FROM period WHERE period_id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, periodID);

            return ps.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public boolean deleteByHotelID(int hotelID) {
        String query = "DELETE FROM period WHERE hotel_id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, hotelID);

            return ps.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public boolean update(Period period) {
        String query = "UPDATE period SET hotel_id = ?, winter_start = ?, winter_end = ?, summer_start = ?, summer_end = ? WHERE period_id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);

            this.match(ps, period);

            ps.setInt(6, period.getPeriodID());

            int response = ps.executeUpdate();

            ps.close();

            return response != -1;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public Period match(ResultSet rs) throws SQLException {
        Period obj = new Period();
        obj.setPeriodID(rs.getInt("period_id"));
        obj.setHotelID(rs.getInt("hotel_id"));
        obj.setWinterStart(rs.getDate("winter_start"));
        obj.setWinterEnd(rs.getDate("winter_end"));
        obj.setSummerStart(rs.getDate("summer_start"));
        obj.setSummerEnd(rs.getDate("summer_end"));
        return obj;
    }

    public void match(PreparedStatement ps, Period period) throws SQLException {
        ps.setInt(1, period.getHotelID());
        ps.setDate(2, period.getWinterStart());
        ps.setDate(3, period.getWinterEnd());
        ps.setDate(4, period.getSummerStart());
        ps.setDate(5, period.getSummerEnd());
    }
}
