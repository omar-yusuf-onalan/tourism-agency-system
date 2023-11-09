package business;

import dao.PeriodDAO;
import entity.Period;

import java.util.ArrayList;

public class PeriodManager {
    private final PeriodDAO periodDAO;

    public PeriodManager() {
        this.periodDAO = new PeriodDAO();
    }

    public ArrayList<Period> getList() {
        return this.periodDAO.getList();
    }

    public boolean add(Period period) {
        if (period.getWinterStart().equals("") || period.getWinterEnd().equals("") || period.getSummerStart().equals("") || period.getSummerEnd().equals(""))
            return false;
        return this.periodDAO.add(period);
    }

    public Period findByPeriodId(int periodID) {
        return this.periodDAO.findByPeriodID(periodID);
    }

    public Period findByHotelID(int hotelID) {
        return this.periodDAO.findByHotelID(hotelID);
    }

    public boolean delete(int periodID) {
        return this.periodDAO.delete(periodID);
    }
    public boolean deleteByHotelID(int hotelID) {
        return this.periodDAO.deleteByHotelID(hotelID);
    }

    public boolean update(Period period) {
        if (period.getWinterStart().equals("") || period.getWinterEnd().equals("") || period.getSummerStart().equals("") || period.getSummerEnd().equals(""))
            return false;
        return this.periodDAO.update(period);
    }


}
