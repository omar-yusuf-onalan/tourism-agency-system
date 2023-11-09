package business;

import core.Utility;
import dao.FacilityDAO;
import entity.Facility;

import java.util.ArrayList;

public class FacilityManager {
    private final FacilityDAO facilityDAO;

    public FacilityManager() {
        this.facilityDAO = new FacilityDAO();
    }

    public ArrayList<Facility> getList() {
        return this.facilityDAO.getList();
    }
    public ArrayList<Facility> getListByHotelID(int hotelID) {
        return this.facilityDAO.getListByHotelID(hotelID);
    }

    public boolean add(Facility facility) {

        return this.facilityDAO.add(facility);
    }

    public Facility findByFacilityID(int facilityID) {
        return this.facilityDAO.findByFacilityID(facilityID);
    }

    public Facility findByHotelID(int hotelID) {
        return this.facilityDAO.findByHotelID(hotelID);
    }

    public Facility findByHotelIDAndType(int hotelID, String type) {
        return this.facilityDAO.findByHotelIDAndType(hotelID, type);
    }

    public boolean delete(int facilityID) {
        return this.facilityDAO.delete(facilityID);
    }

    public boolean update(Facility facility) {
        return this.facilityDAO.update(facility);
    }

    public ArrayList<Facility> searchList(Facility facility) {
        return this.facilityDAO.searchList(facility);
    }
}
