package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import business.*;
import core.ComboBoxItem;
import core.Utility;
import entity.*;
import view.PopupView.*;

import java.awt.event.*;
import java.util.ArrayList;

public class AgentView extends Layout {
    private JPanel container, pnl_hotel_list, pnl_room_list, pnl_search_bar;
    private JTabbedPane tab_agent;
    private JScrollPane scrl_hotel_list, scrl_room_list;
    private JTable tbl_hotel_list, tbl_room_list;
    private JButton btn_hotel_search, btn_hotel_add, btn_user_logout, btn_room_add, btn_room_reset;
    private JLabel lbl_user_welcome, lbl_room_hotelname;
    private JComboBox cmb_room_hotelname, cmb_room_lodgingname, cmb_room_roomname;
    private JPanel pnl_reservation_list;
    private JScrollPane scrl_reservation_list;
    private JTable tbl_reservation_list;
    private JButton btn_reservation_reserve;
    private JButton btn_room_search;
    private JButton btn_reservation_load;
    private DefaultTableModel mdl_hotel_list, mdl_room_list, mdl_reservation_list;
    private Object[] row_hotel_list, row_room_list, row_reservation_list;
    private JPopupMenu hotelListPopup, roomListPopup, reservationListPopup;

    private HotelManager hotelManager;
    private FacilityManager facilityManager;
    private RoomManager roomManager;
    private LodgingManager lodgingManager;
    private PeriodManager periodManager;
    private ReservationManager reservationManager;
    private ArrayList<String> guestInfo;

    public AgentView(User user) {
        this.hotelManager = new HotelManager();
        this.facilityManager = new FacilityManager();
        this.roomManager = RoomManager.getInstance();
        this.lodgingManager = new LodgingManager();
        this.periodManager = new PeriodManager();
        this.reservationManager = ReservationManager.getInstance();

        this.hotelListPopup = new JPopupMenu();
        this.roomListPopup = new JPopupMenu();
        this.reservationListPopup = new JPopupMenu();

        this.guestInfo = new ArrayList<>();
        
        this.add(this.container);
        this.initView(1000, 600, "Agent Panel");
        lbl_user_welcome.setText("Welcome " + user.getName());
        initLogoutButton();

        // ----- Hotel Tab -----
        initHotelList();
        initHotelPopupMenu();

        initFacilityPopupMenu();
        initLodgingPopupMenu();
        initPeriodPopupMenu();

        // ----- Room Tab -----
        initRoomList();
        initRoomPopupMenu();
        loadHotelComboBox();
        createSearchRoomListeners();

        // ----- Reservation Tab -----
        initReservationList();
        initReserveRoom();
        initReservationPopupMenu();
        initLoadReservationsButton();
    }

    // Start of Hotel Tab
    private void initHotelList() {
        mdl_hotel_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0 || column == 1 || column == 2 || column == 3 || column == 4 || column == 5 || column == 6 || column == 7)
                    return false;
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_hotel_list = {"ID", "Name", "City", "Region", "Address", "Email", "Telephone", "Star"};
        mdl_hotel_list.setColumnIdentifiers(col_hotel_list);
        row_hotel_list = new Object[col_hotel_list.length];

        tbl_hotel_list.setModel(mdl_hotel_list);
        tbl_hotel_list.getTableHeader().setReorderingAllowed(false);

        loadHotelModel();
    }
    private void initHotelPopupMenu() {
        JMenuItem updateHotel = new JMenuItem("Update Hotel");
        JMenuItem deleteHotel = new JMenuItem("Delete Hotel");

        this.hotelListPopup.add(updateHotel);
        this.hotelListPopup.add(deleteHotel);

        createUpdateHotelListener(updateHotel);
        createDeleteHotelListener(deleteHotel);

        createHotelMouseListener();
        createAddHotelListeners();
        createSearchHotelListeners();
    }
    private void createHotelMouseListener() {
        tbl_hotel_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int r = tbl_hotel_list.rowAtPoint(e.getPoint());
                if (r >= 0 && r < tbl_hotel_list.getRowCount()) {
                    tbl_hotel_list.setRowSelectionInterval(r, r);
                } else {
                    tbl_hotel_list.clearSelection();
                }

                int rowIndex = tbl_hotel_list.getSelectedRow();

                if (rowIndex < 0)
                    return;
                if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
                    hotelListPopup.show(e.getComponent(), e.getX(), e.getY());
                }

            }
        });
    }
    private void createAddHotelListeners() {
        btn_hotel_add.addActionListener(e -> {
            HotelPopupView hotelPopupView = new HotelPopupView(new Hotel(), "add");
            hotelPopupView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadHotelModel();
                    loadHotelComboBox();
                }
            });
        });
    }
    private void createSearchHotelListeners() {
        btn_hotel_search.addActionListener(e -> {
            HotelPopupView hotelPopupView = new HotelPopupView(new Hotel(), "search");
            hotelPopupView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                ArrayList<Hotel> filteredHotels = HotelPopupView.getFilteredHotels();
                if (!(filteredHotels == null)) {
                    loadHotelModel(filteredHotels);
                } else {
                    loadHotelModel();
                }
                }
            });
        });
    }
    private void createUpdateHotelListener(JMenuItem updateHotel) {
        updateHotel.addActionListener(e -> {

            int selectedId = Integer.parseInt(tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow() , 0).toString());
            Hotel selectedHotel = this.hotelManager.findByHotelID(selectedId);

            HotelPopupView hotelPopupView = new HotelPopupView(selectedHotel, "update");
            hotelPopupView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadHotelModel();
                    loadHotelComboBox();
                }
            });
        });
    }
    private void createDeleteHotelListener(JMenuItem deleteHotel) {
        deleteHotel.addActionListener(e -> {
            if (!Utility.confirm("sure")){
                return;
            }

            int selectedId = Integer.parseInt(tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow() , 0).toString());

            if (this.hotelManager.delete(selectedId)){
                Utility.showMessage("success");
                loadHotelModel();
                loadHotelComboBox();
                return;
            }

            Utility.showMessage("error");

        });
    }
    private void loadHotelModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_hotel_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Hotel obj : this.hotelManager.getList()) {
            i = 0;
            fillRow(obj, i);
        }
    }
    private void loadHotelModel(ArrayList<Hotel> hotelList) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_hotel_list.getModel();
        clearModel.setRowCount(0);

        for (Hotel obj : hotelList) {
            int i = 0;
            fillRow(obj, i);
        }
    }


    // Start of Facility Popup Menu
    private void initFacilityPopupMenu() {
        JMenuItem accessFacility = new JMenuItem("Access Facility Specs");

        this.hotelListPopup.add(accessFacility);

        createAccessFacilityListeners(accessFacility);
    }
    private void createAccessFacilityListeners(JMenuItem addFacility) {
        addFacility.addActionListener(e -> {

            int selectedId = Integer.parseInt(tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow() , 0).toString());
            Hotel hotel = hotelManager.findByHotelID(selectedId);

            FacilityPopupView facilityPopupView = new FacilityPopupView(hotel);
        });
    }

    // End of Facility Popup Menu

    // Start of Lodging Popup Menu
    private void initLodgingPopupMenu() {
        JMenuItem accessLodging = new JMenuItem("Access Lodging Options");

        this.hotelListPopup.add(accessLodging);

        createLodgingListeners(accessLodging);
    }
    private void createLodgingListeners(JMenuItem accessLodging) {
        accessLodging.addActionListener(e -> {

            int selectedId = Integer.parseInt(tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow() , 0).toString());
            Hotel selectedHotel = this.hotelManager.findByHotelID(selectedId);

            LodgingPopupView lodgingPopupView = new LodgingPopupView(selectedHotel);
            lodgingPopupView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadHotelComboBox();
                }
            });
        });
    }

    // End of Lodging Popup Menu

    // Start of Period Popup Menu
    private void initPeriodPopupMenu() {
        JMenuItem accessPeriod = new JMenuItem("Access Hotel Periods");

        this.hotelListPopup.add(accessPeriod);

        createPeriodListeners(accessPeriod);
    }
    private void createPeriodListeners(JMenuItem periodLodging) {
        periodLodging.addActionListener(e -> {

            int selectedId = Integer.parseInt(tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow() , 0).toString());
            Hotel selectedHotel = this.hotelManager.findByHotelID(selectedId);

            PeriodPopupView periodPopupView = new PeriodPopupView(selectedHotel);
            periodPopupView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadHotelComboBox();
                }
            });
        });
    }

    // End of Period Popup Menu

    // End of Hotel Tab

    // Start of Room Tab
    private void initRoomList() {
        mdl_room_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
            if (column == 0 || column == 1 || column == 2 || column == 3 || column == 4 || column == 5 || column == 6)
                return false;
            return super.isCellEditable(row, column);
            }
        };
        Object[] col_room_list = {"ID", "Hotel ID", "Period ID", "Name", "Number of Beds", "Items", "Square Meters", "Stock"};
        mdl_room_list.setColumnIdentifiers(col_room_list);
        row_room_list = new Object[col_room_list.length];

        tbl_room_list.setModel(mdl_room_list);
        tbl_room_list.getTableHeader().setReorderingAllowed(false);

        loadRoomModel();
    }
    private void initRoomPopupMenu() {
        JMenuItem updateRoom = new JMenuItem("Update Room");
        JMenuItem deleteRoom = new JMenuItem("Delete Room");

        this.roomListPopup.add(updateRoom);
        this.roomListPopup.add(deleteRoom);


        createRoomMouseListener();
        createAddRoomListeners();
        createResetRoomListeners();
        createUpdateRoomListeners(updateRoom);
        createDeleteRoomListeners(deleteRoom);
    }
    private void createRoomMouseListener() {
        tbl_room_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
            int r = tbl_room_list.rowAtPoint(e.getPoint());
            if (r >= 0 && r < tbl_room_list.getRowCount()) {
                tbl_room_list.setRowSelectionInterval(r, r);
            } else {
                tbl_room_list.clearSelection();
            }

            int rowIndex = tbl_room_list.getSelectedRow();

            if (rowIndex < 0)
                return;
            if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
                roomListPopup.show(e.getComponent(), e.getX(), e.getY());
            }
            }
        });
    }
    private void createAddRoomListeners() {
        btn_room_add.addActionListener(e -> {
            RoomPopupView roomPopupView = new RoomPopupView();
            roomPopupView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadRoomModel();
                }
            });
        });
    }

    private void createSearchRoomListeners() {
        btn_room_search.addActionListener(e -> {
            ArrayList<Room> filteredRooms;
            ComboBoxItem itemH = (ComboBoxItem) cmb_room_hotelname.getSelectedItem();

            if (itemH == null) {
                loadRoomModel();
                return;
            }

            filteredRooms = this.roomManager.getListByHotelID(itemH.getKey());
            loadRoomModel(filteredRooms);

        });
    }
    private void createResetRoomListeners() {
        btn_room_reset.addActionListener(e -> {
            cmb_room_hotelname.setSelectedItem(null);

            loadRoomModel();
        });
    }


    private void createUpdateRoomListeners(JMenuItem updateRoom) {
        updateRoom.addActionListener(e -> {

            int selectedId = Integer.parseInt(tbl_room_list.getValueAt(tbl_room_list.getSelectedRow() , 0).toString());
            Room selectedRoom = this.roomManager.findByRoomID(selectedId);

            RoomPopupView roomPopupView = new RoomPopupView(selectedRoom);
            roomPopupView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadRoomModel();
                }
            });
        });
    }
    private void createDeleteRoomListeners(JMenuItem deleteRoom) {
        deleteRoom.addActionListener(e -> {
            if (!Utility.confirm("sure")){
                return;
            }

            int selectedId = Integer.parseInt(tbl_room_list.getValueAt(tbl_room_list.getSelectedRow() , 0).toString());

            if (this.roomManager.delete(selectedId)){
                Utility.showMessage("success");
                loadRoomModel();
                return;
            }

            Utility.showMessage("error");

        });
    }
    private void loadRoomModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_room_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Room obj : this.roomManager.getList()) {
            i = 0;
            fillRow(obj, i);
        }
    }
    private void loadRoomModel(ArrayList<Room> roomList) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_room_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Room obj : roomList) {
            if (obj == null)
                continue;
            i = 0;
            fillRow(obj, i);
        }
    }

    private void loadHotelComboBox() {
        cmb_room_hotelname.removeAllItems();
        for (Hotel hotel : this.hotelManager.getList()) {
            cmb_room_hotelname.addItem(new ComboBoxItem(hotel.getHotelID(), hotel.getName()));
        }

        cmb_room_hotelname.setSelectedItem(null);
    }



    // End of Room Tab

    // Start of Reservation Tab

    private void initReservationList() {
        mdl_reservation_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0 || column == 1 || column == 2 || column == 3 || column == 4 || column == 5 || column == 6 || column == 7 || column == 8 || column == 9)
                    return false;
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_reservation_list = {"ID", "Room ID", "Contact Name", "Contact Telephone", "Contact Email", "Note", "Adult Information", "Child Information", "Arrival", "Departure"};
        mdl_reservation_list.setColumnIdentifiers(col_reservation_list);
        row_reservation_list = new Object[col_reservation_list.length];

        tbl_reservation_list.setModel(mdl_reservation_list);
        tbl_reservation_list.getTableHeader().setReorderingAllowed(false);

        loadReservationModel();
    }

    private void initReservationPopupMenu() {
        JMenuItem deleteReservation = new JMenuItem("Delete Reservation");


        this.reservationListPopup.add(deleteReservation);
        createReservationMouseListener();
        createDeleteReservationListener(deleteReservation);
    }

    private void createReservationMouseListener() {
        tbl_reservation_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int r = tbl_reservation_list.rowAtPoint(e.getPoint());
                if (r >= 0 && r < tbl_reservation_list.getRowCount()) {
                    tbl_reservation_list.setRowSelectionInterval(r, r);
                } else {
                    tbl_reservation_list.clearSelection();
                }

                int rowIndex = tbl_reservation_list.getSelectedRow();

                if (rowIndex < 0)
                    return;
                if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
                    reservationListPopup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    private void createDeleteReservationListener(JMenuItem deleteReservation) {
        deleteReservation.addActionListener(e -> {

            int reservationID = Integer.parseInt(tbl_reservation_list.getValueAt(tbl_reservation_list.getSelectedRow() , 0).toString());

            if (Utility.confirm("sure")) {
                if (this.reservationManager.delete(reservationID))
                    Utility.showMessage("success");
                else
                    Utility.showMessage("error");
            }

            loadReservationModel();
        });
    }

    private void initReserveRoom() {
        btn_reservation_reserve.addActionListener(e -> {
            new SearchRoomPopupView();
        });
    }

    private void initLoadReservationsButton() {
        btn_reservation_load.addActionListener(e -> {
            if (!this.reservationManager.getList().isEmpty())
                loadReservationModel();
        });
    }

    private void loadReservationModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_reservation_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Reservation obj : this.reservationManager.getList()) {
            i = 0;
            fillRow(obj, i);
        }
    }

    // End of Reservation Tab

    private void initLogoutButton() {
        btn_user_logout.addActionListener(e -> {
            dispose();

            new LoginView();
        });
    }

    private void fillRow(Hotel obj, int i) {
        row_hotel_list[i++] = obj.getHotelID();
        row_hotel_list[i++] = obj.getName();
        row_hotel_list[i++] = obj.getCity();
        row_hotel_list[i++] = obj.getRegion();
        row_hotel_list[i++] = obj.getAddress();
        row_hotel_list[i++] = obj.getEmail();
        row_hotel_list[i++] = obj.getTelephone();
        row_hotel_list[i++] = obj.getStar();

        mdl_hotel_list.addRow(row_hotel_list);
    }

    private void fillRow(Room obj, int i) {
        row_room_list[i++] = obj.getRoomID();
        row_room_list[i++] = obj.getHotelID();
        row_room_list[i++] = obj.getPeriodID();
        row_room_list[i++] = obj.getName();
        row_room_list[i++] = obj.getNumberOfBeds();
        row_room_list[i++] = obj.getItem();
        row_room_list[i++] = obj.getSquareMeter();
        row_room_list[i++] = obj.getStock();

        mdl_room_list.addRow(row_room_list);
    }

    private void fillRow(Reservation obj, int i) {
        row_reservation_list[i++] = obj.getReservationID();
        row_reservation_list[i++] = obj.getRoomID();
        row_reservation_list[i++] = obj.getContactName();
        row_reservation_list[i++] = obj.getContactTelephone();
        row_reservation_list[i++] = obj.getContactEmail();
        row_reservation_list[i++] = obj.getNote();
        row_reservation_list[i++] = obj.getAdultInformation();
        row_reservation_list[i++] = obj.getChildInformation();
        row_reservation_list[i++] = obj.getArrival();
        row_reservation_list[i++] = obj.getDeparture();

        mdl_reservation_list.addRow(row_reservation_list);
    }
}
