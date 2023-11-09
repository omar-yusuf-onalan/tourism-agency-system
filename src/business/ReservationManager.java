package business;

import dao.ReservationDAO;
import dao.RoomDAO;
import entity.Reservation;
import entity.Room;

import java.util.ArrayList;

public class ReservationManager {
    private static ReservationManager instance = null;
    private final ReservationDAO reservationDAO;
    private RoomManager roomManager;

    private ReservationManager() {
        this.reservationDAO = new ReservationDAO();

    }

    public static ReservationManager getInstance() {
        if (instance == null) {
            synchronized (RoomManager.class) {
                if (instance == null) {
                    instance = new ReservationManager();
                }
            }
        }
        return instance;
    }

    public ArrayList<Reservation> getList() {
        return this.reservationDAO.getList();
    }

    public ArrayList<Reservation> getListByRoomID(int roomID) {
        return this.reservationDAO.getListByRoomID(roomID);
    }

    public Reservation findByReservationID(int reservationID) {
        return this.reservationDAO.findByReservationID(reservationID);
    }

    public boolean add(Reservation reservation) {
        this.roomManager = RoomManager.getInstance();
        Room room = this.roomManager.findByRoomID(reservation.getRoomID());

        if (room != null) {
            room.setStock(room.getStock() - 1);
            this.roomManager.update(room);
        }

        return this.reservationDAO.add(reservation);
    }

    public boolean delete(int reservationID) {
        this.roomManager = RoomManager.getInstance();
        Reservation reservation = findByReservationID(reservationID);

        Room room = this.roomManager.findByRoomID(reservation.getRoomID());

        if (room != null) {
            room.setStock(room.getStock() + 1);
            this.roomManager.update(room);
        }


        return this.reservationDAO.delete(reservationID);
    }

    public boolean deleteByRoomID(int roomID) {
        return this.reservationDAO.deleteByRoomID(roomID);
    }
}
