package business;

import core.Utility;
import dao.RoomDAO;
import entity.Room;

import java.sql.Date;
import java.util.ArrayList;

public class RoomManager {
    private static RoomManager instance = null;

    private final RoomDAO roomDAO;
    private final ReservationManager reservationManager;
    private final PriceManager priceManager;

    private RoomManager() {
        this.roomDAO = new RoomDAO();
        this.reservationManager = ReservationManager.getInstance();
        this.priceManager = new PriceManager();
    }

    public static RoomManager getInstance() {
        if (instance == null) {
            synchronized (RoomManager.class) {
                if (instance == null) {
                    instance = new RoomManager();
                }
            }
        }
        return instance;
    }

    public ArrayList<Room> getList() {
        return this.roomDAO.getList();
    }

    public ArrayList<Room> getListByDates(Date customerArrival, Date customerDeparture) {
        return this.roomDAO.getListByDates(customerArrival, customerDeparture);
    }

    public ArrayList<Room> getListByHotelID(int hotelID) {
        return this.roomDAO.getListByHotelID(hotelID);
    }

    public boolean add(Room room) {

        if (findByHotelIDAndName(room.getHotelID(), room.getName()) != null) {
            Utility.showMessage("Duplicate room detected! Instead of adding a duplicate room, please increase the stock of an already existing one!");
            return false;
        }

        return this.roomDAO.add(room);
    }

    public Room findByRoomID(int roomID) {
        return this.roomDAO.findByRoomID(roomID);
    }

    public Room findByHotelIDAndName(int hotelID, String name) {
        return this.roomDAO.findByHotelIDAndName(hotelID, name);
    }

    public boolean delete(int roomID) {
        if (!reservationManager.getListByRoomID(roomID).isEmpty())
            reservationManager.deleteByRoomID(roomID);

        if (!priceManager.getListByRoomID(roomID).isEmpty())
            reservationManager.deleteByRoomID(roomID);

        return this.roomDAO.delete(roomID);
    }

    public boolean update(Room room) {
        return this.roomDAO.update(room);
    }

    public ArrayList<Room> searchList(Room room) {
        return this.roomDAO.searchList(room);
    }
}
