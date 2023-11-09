package business;

import dao.PriceDAO;
import entity.Price;

import java.util.ArrayList;

public class PriceManager {
    private final PriceDAO priceDAO;

    public PriceManager() {
        this.priceDAO = new PriceDAO();
    }

    public ArrayList<Price> getList() {
        return this.priceDAO.getList();
    }

    public ArrayList<Price> getListByRoomID(int roomID) {
        return this.priceDAO.getListByRoomID(roomID);
    }

    public ArrayList<Price> getListByLodgingID(int lodgingID) {
        return this.priceDAO.getListByLodgingID(lodgingID);
    }

    public boolean add(Price price) {
        return this.priceDAO.add(price);
    }

    public Price findByPriceID(int priceID) {
        return this.priceDAO.findByPriceID(priceID);
    }

    public Price findByLodgingIDAndRoomID(int lodgingID, int roomID) {
        return this.priceDAO.findByLodgingIDAndRoomID(lodgingID, roomID);
    }

    public boolean delete(int priceID) {
        return this.priceDAO.delete(priceID);
    }

    public boolean deleteByRoomID(int roomID) {
        return this.priceDAO.delete(roomID);
    }

    public boolean deleteByLodgingID(int lodgingID) {
        return this.priceDAO.deleteByLodgingID(lodgingID);
    }

    public boolean update(Price price) {
        return this.priceDAO.update(price);
    }




}
