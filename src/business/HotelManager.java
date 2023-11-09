package business;

import core.Utility;
import dao.HotelDAO;
import entity.Facility;
import entity.Hotel;
import entity.Lodging;
import entity.Room;

import java.util.ArrayList;

public class HotelManager {
    private final HotelDAO hotelDAO;
    private final FacilityManager facilityManager;
    private final LodgingManager lodgingManager;
    private final PeriodManager periodManager;
    private final RoomManager roomManager;

    public HotelManager() {
        this.hotelDAO = new HotelDAO();
        this.facilityManager = new FacilityManager();
        this.lodgingManager = new LodgingManager();
        this.periodManager = new PeriodManager();
        this.roomManager = RoomManager.getInstance();
    }

    public ArrayList<Hotel> getList() {
        return this.hotelDAO.getList();
    }

    public boolean add(Hotel hotel) {
        if (this.hotelDAO.findByEmail(hotel.getEmail()) != null) {
            Utility.showMessage("error");
            return false;
        }

        return this.hotelDAO.add(hotel);
    }

    public Hotel findByHotelID(int id) {
        return this.hotelDAO.findByHotelID(id);
    }

    public Hotel findByEmail(String email) {
        return this.hotelDAO.findByEmail(email);
    }

    public boolean delete(int hotelID) {
        if (!facilityManager.getListByHotelID(hotelID).isEmpty()) {
            ArrayList<Facility> facilitiesToBeDeleted = facilityManager.getListByHotelID(hotelID);

            for (Facility facility : facilitiesToBeDeleted)
                facilityManager.delete(facility.getFacilityID());
        }

        if (lodgingManager.findByHotelID(hotelID) != null) {
            ArrayList<Lodging> lodgingsToBeDeleted = lodgingManager.getListByHotelID(hotelID);

            for (Lodging lodging : lodgingsToBeDeleted)
                lodgingManager.delete(lodging.getLodgingID());
        }

        if (!roomManager.getListByHotelID(hotelID).isEmpty()) {
            ArrayList<Room> roomsToBeDeleted = roomManager.getListByHotelID(hotelID);

            for (Room room : roomsToBeDeleted)
                roomManager.delete(room.getRoomID());
        }

        if (periodManager.findByHotelID(hotelID) != null)
            periodManager.deleteByHotelID(hotelID);

        return this.hotelDAO.delete(hotelID);
    }

    public boolean update(Hotel hotel) {
        if (this.hotelDAO.findByEmail(hotel.getEmail()) != null && this.hotelDAO.findByEmail(hotel.getEmail()).getHotelID() != hotel.getHotelID()) {
            Utility.showMessage("Hotel already exists\nEnter a different email");
            return false;
        }

        return this.hotelDAO.update(hotel);
    }

    public ArrayList<Hotel> searchList(Hotel hotel) {
        return this.hotelDAO.searchList(hotel);
    }
    public ArrayList<Hotel> searchList(String value) {
        return this.hotelDAO.searchList(value);
    }
}
