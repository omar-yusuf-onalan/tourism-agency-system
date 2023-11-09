package view.PopupView;

import business.*;
import core.Utility;
import entity.*;
import view.Layout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;

public class FilteredRoomPopupView extends Layout {

    private JPanel container;
    private JPanel pnl_filteredroom_list;
    private JScrollPane scrl_filteredroom_list;
    private JTable tbl_filteredroom_list;

    private DefaultTableModel mdl_filteredroom_list;
    private Object[] row_filteredroom_list;
    HotelManager hotelManager;
    RoomManager roomManager;
    PeriodManager periodManager;
    LodgingManager lodgingManager;
    PriceManager priceManager;
    ArrayList<Room> filteredRooms;
    ArrayList<String> guestInformation;

    JPopupMenu filteredRoomListPopup;
    public FilteredRoomPopupView(ArrayList<Room> filteredRooms, ArrayList<String> guestInformation) {
        this.add(container);
        this.initView(650, 400, "Choose a room");

        this.hotelManager = new HotelManager();
        this.roomManager = RoomManager.getInstance();
        this.periodManager = new PeriodManager();
        this.lodgingManager = new LodgingManager();
        this.priceManager = new PriceManager();

        this.filteredRooms = filteredRooms;
        this.guestInformation = guestInformation;
        this.filteredRoomListPopup = new JPopupMenu();


        initFilteredRoomList();
        initRoomPopupMenu();

    }

    private void initFilteredRoomList() {
        mdl_filteredroom_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0 || column == 1 || column == 2 || column == 3 || column == 4 || column == 5 || column == 6)
                    return false;
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_filteredroom_list  = {"Hotel Name", "City", "Region", "Address", "Telephone", "Star", "Facilities", "Room ID",
                "Room Name", "Lodging Type", "Number of Beds", "Items", "Nights", "Adults", "Children", "Total Price"};
        mdl_filteredroom_list.setColumnIdentifiers(col_filteredroom_list);
        row_filteredroom_list = new Object[col_filteredroom_list.length];

        tbl_filteredroom_list.setModel(mdl_filteredroom_list);
        tbl_filteredroom_list.getTableHeader().setReorderingAllowed(false);

        loadFilteredRoomModel(this.filteredRooms);
    }
    private void initRoomPopupMenu() {
        JMenuItem reserveRoom= new JMenuItem("Reserve This Room");

        this.filteredRoomListPopup.add(reserveRoom);



        createFilteredRoomMouseListener();

        createReserveRoomListener(reserveRoom);

    }
    private void createFilteredRoomMouseListener() {
        tbl_filteredroom_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int r = tbl_filteredroom_list.rowAtPoint(e.getPoint());
                if (r >= 0 && r < tbl_filteredroom_list.getRowCount()) {
                    tbl_filteredroom_list.setRowSelectionInterval(r, r);
                } else {
                    tbl_filteredroom_list.clearSelection();
                }

                int rowIndex = tbl_filteredroom_list.getSelectedRow();

                if (rowIndex < 0)
                    return;
                if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
                    filteredRoomListPopup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    private void createReserveRoomListener(JMenuItem reserveRoom) {
        reserveRoom.addActionListener(e -> {

            int selectedID = Integer.parseInt(tbl_filteredroom_list.getValueAt(tbl_filteredroom_list.getSelectedRow() , 7).toString());
            Room room = this.roomManager.findByRoomID(selectedID);

            new CompleteReservationPopupView(guestInformation, room);
        });
    }

    private void loadFilteredRoomModel(ArrayList<Room> roomList) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_filteredroom_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Room room : roomList) {
            ArrayList<Lodging> lodgingList = this.lodgingManager.getListByHotelID(room.getHotelID());

            for (Lodging lodging : lodgingList) {
                i = 0;
                fillRow(room,lodging, i);
            }

        }
    }

    private void fillRow(Room room, Lodging lodging, int i) {
        Hotel hotel = this.hotelManager.findByHotelID(room.getHotelID());
        Period period = this.periodManager.findByHotelID(room.getHotelID());

        String arrival = this.guestInformation.get(2);
        String departure = this.guestInformation.get(3);

        ArrayList<String> stringList = new ArrayList<>();
        stringList.add(arrival);
        stringList.add(departure);

        ArrayList<Date> dateList;
        try {
            dateList = Utility.parseDates(stringList);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        int nights = Utility.getDayDifference(dateList.get(0), dateList.get(1));

        String winterOrSummer = Utility.whichPeriod(dateList, period);

        Price price = this.priceManager.findByLodgingIDAndRoomID(lodging.getLodgingID(), room.getRoomID());

        double totalPrice;

        int adultNumber = Integer.parseInt(this.guestInformation.get(0));
        int childNumber = Integer.parseInt(this.guestInformation.get(1));

        double winterAdultPrice = price.getWinterAdultPrice();
        double winterChildPrice = price.getWinterChildPrice();

        double summerAdultPrice = price.getSummerAdultPrice();
        double summerChildPrice = price.getSummerChildPrice();

        if ("winter".equals(winterOrSummer))
            totalPrice = ((adultNumber * winterAdultPrice) + (childNumber * winterChildPrice)) * nights;
        else
            totalPrice = ((adultNumber * summerAdultPrice) + (childNumber * summerChildPrice)) * nights;

        row_filteredroom_list[i++] = hotel.getName();
        row_filteredroom_list[i++] = hotel.getCity();
        row_filteredroom_list[i++] = hotel.getRegion();
        row_filteredroom_list[i++] = hotel.getAddress();
        row_filteredroom_list[i++] = hotel.getTelephone();
        row_filteredroom_list[i++] = hotel.getStar();
        row_filteredroom_list[i++] = Utility.parseFacility(hotel);
        row_filteredroom_list[i++] = room.getRoomID();
        row_filteredroom_list[i++] = room.getName();
        row_filteredroom_list[i++] = lodging.getType();
        row_filteredroom_list[i++] = room.getNumberOfBeds();
        row_filteredroom_list[i++] = room.getItem();
        row_filteredroom_list[i++] = nights;
        row_filteredroom_list[i++] = adultNumber;
        row_filteredroom_list[i++] = childNumber;
        row_filteredroom_list[i++] = totalPrice;

        mdl_filteredroom_list.addRow(row_filteredroom_list);
    }
}
