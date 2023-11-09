package business;

import core.Utility;
import dao.LodgingDAO;
import entity.Lodging;

import java.util.ArrayList;

public class LodgingManager {
    private final LodgingDAO lodgingDAO;
    private PriceManager priceManager;

    public LodgingManager() {
        this.lodgingDAO = new LodgingDAO();
        this.priceManager = new PriceManager();
    }

    public ArrayList<Lodging> getList() {
        return this.lodgingDAO.getList();
    }

    public ArrayList<Lodging> getListByHotelID(int hotelID) {
        return this.lodgingDAO.getListByHotelID(hotelID);
    }

    public boolean add(Lodging lodging) {
        return this.lodgingDAO.add(lodging);
    }

    public Lodging findBylodgingID(int lodgingID) {
        return this.lodgingDAO.findByLodgingID(lodgingID);
    }

    public Lodging findByHotelID(int hotelID) {
        return this.lodgingDAO.findByHotelID(hotelID);
    }

    public Lodging findByHotelIDAndType(int hotelID, String type) {
        return this.lodgingDAO.findByHotelIDAndType(hotelID, type);
    }

    public boolean delete(int lodgingID) {
        if (!priceManager.getListByLodgingID(lodgingID).isEmpty())
            priceManager.deleteByLodgingID(lodgingID);

        return this.lodgingDAO.delete(lodgingID);
    }

    public boolean update(Lodging lodging) {
        return this.lodgingDAO.update(lodging);
    }

    public ArrayList<Lodging> searchList(Lodging lodging) {
        return this.lodgingDAO.searchList(lodging);
    }
}
