package dao;

import core.DB;
import core.Utility;
import entity.Price;
import entity.Room;

import java.sql.*;
import java.util.ArrayList;

public class PriceDAO {

    private final Connection connection;

    public PriceDAO() {
        this.connection = DB.getInstance();
    }

    public ArrayList<Price> getList() {
        ArrayList<Price> priceList = new ArrayList<>();
        String query = "SELECT * FROM price";

        try {
            Statement st = this.connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                priceList.add(this.match(rs));
            }

            st.close();
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return priceList;
    }

    public ArrayList<Price> getListByRoomID(int roomID) {
        ArrayList<Price> priceList = new ArrayList<>();
        String query = "SELECT * FROM price WHERE room_id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, roomID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                priceList.add(this.match(rs));
            }

            ps.close();
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return priceList;
    }

    public ArrayList<Price> getListByLodgingID(int lodgingID) {
        ArrayList<Price> priceList = new ArrayList<>();
        String query = "SELECT * FROM price WHERE lodging_id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, lodgingID);
            ps.execute();

            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return priceList;
    }

    public boolean add(Price price) {
        String query = "INSERT INTO price (lodging_id, room_id, winter_adult_price, winter_child_price, summer_adult_price, summer_child_price) VALUES (?,?,?,?,?,?)";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);

            this.match(ps, price);

            int response = ps.executeUpdate();
            ps.close();

            return response != -1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Price findByPriceID(int priceID) {
        Price obj = null;
        String query = "SELECT * FROM price WHERE price_id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, priceID);
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

    public Price findByLodgingIDAndRoomID(int lodgingID, int roomID) {
        Price obj = null;
        String query = "SELECT * FROM price WHERE lodging_id = ? AND room_id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, lodgingID);
            ps.setInt(2, roomID);
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

    public boolean delete(int priceID) {
        String query = "DELETE FROM price WHERE price_id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, priceID);

            return ps.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteByRoomID(int roomID) {
        String query = "DELETE FROM price WHERE room_id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, roomID);

            return ps.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteByLodgingID(int lodgingID) {
        String query = "DELETE FROM price WHERE lodging_id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, lodgingID);

            return ps.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean update(Price price) {
        String query = "UPDATE price SET lodging_id = ?, room_id = ?, winter_adult_price = ?, winter_child_price = ?, summer_adult_price = ?, summer_child_price = ? WHERE price_id = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);

            this.match(ps, price);

            ps.setInt(7, price.getPriceID());

            int response = ps.executeUpdate();
            ps.close();

            return response != -1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Price match(ResultSet rs) throws SQLException {
        Price obj = new Price();
        obj.setPriceID(rs.getInt("price_id"));
        obj.setLodgingID(rs.getInt("lodging_id"));
        obj.setRoomID(rs.getInt("room_id"));
        obj.setWinterAdultPrice(rs.getDouble("winter_adult_price"));
        obj.setWinterChildPrice(rs.getDouble("winter_child_price"));
        obj.setSummerAdultPrice(rs.getDouble("summer_adult_price"));
        obj.setSummerChildPrice(rs.getDouble("summer_child_price"));
        return obj;
    }

    private void match(PreparedStatement ps, Price price) throws SQLException {
        ps.setInt(1, price.getLodgingID());
        ps.setInt(2, price.getRoomID());
        ps.setDouble(3, price.getWinterAdultPrice());
        ps.setDouble(4, price.getWinterChildPrice());
        ps.setDouble(5, price.getSummerAdultPrice());
        ps.setDouble(6, price.getSummerChildPrice());
    }
}
