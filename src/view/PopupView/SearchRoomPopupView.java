package view.PopupView;

import business.HotelManager;
import business.LodgingManager;
import business.PeriodManager;
import business.RoomManager;
import core.Utility;
import entity.Room;
import view.Layout;

import javax.swing.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.sql.Date;

public class SearchRoomPopupView extends Layout {
    private JPanel container, pnl_searchroom_guestinformation, pnl_searchroom_arrivaldeparture;
    private JTextField tf_searchroom_regioncityhotel, tf_searchroom_guestinformation, tf_searchroom_adult, tf_searchroom_children;
    private JFormattedTextField ftf_searchroom_arrival, ftf_searchroom_departure;
    private JButton btn_searchroom_search;
    private JLabel lbl_searchroom_regioncityhotel, lbl_searchroom_adult, lbl_searchroom_children, lbl_searchroom_arrivaldeparture, lbl_searchroom_guestinformation, lbl_searchroom_searchforrooms;
    HotelManager hotelManager;
    RoomManager roomManager;
    PeriodManager periodManager;
    LodgingManager lodgingManager;

    ArrayList<Room> filteredRooms;
    ArrayList<String> guestInformation;

    public SearchRoomPopupView() {
        this.add(container);
        this.initView(650, 200, "Search For A Room");

        this.hotelManager = new HotelManager();
        this.roomManager = RoomManager.getInstance();
        this.periodManager = new PeriodManager();
        this.lodgingManager = new LodgingManager();

        this.filteredRooms = new ArrayList<>();
        this.guestInformation = new ArrayList<>();

        initSearchButton();
    }

    private void initSearchButton() {
        btn_searchroom_search.addActionListener(e -> {

            JTextField[] fieldList = getTextFields();

            if (Utility.isFieldListEmpty(fieldList)) {
                Utility.showMessage("fill");
                return;
            }

            ArrayList<Date> customerDateList;

            try {
                customerDateList = Utility.parseDates(getStringListOfDates());
            } catch (Exception ex) {
                Utility.showMessage("invalid date");
                return;
            }

            filteredRooms = this.roomManager.getListByDates(customerDateList.get(0), customerDateList.get(1));

            for (Room room : filteredRooms)
                System.out.println(room.getRoomID());

            if (filteredRooms.isEmpty()) {
                Utility.showMessage("No rooms found");
            } else {
                new FilteredRoomPopupView(this.filteredRooms, this.guestInformation);
            }


            dispose();
        });
    }

    private JTextField[] getTextFields() {
        JTextField regionCityHotel = tf_searchroom_regioncityhotel;
        JTextField adults = tf_searchroom_adult;
        JTextField children = tf_searchroom_children;
        JTextField arrival = ftf_searchroom_arrival;
        JTextField departure = ftf_searchroom_departure;

        this.guestInformation.add(tf_searchroom_adult.getText());
        this.guestInformation.add(tf_searchroom_children.getText());
        this.guestInformation.add(ftf_searchroom_arrival.getText());
        this.guestInformation.add(ftf_searchroom_departure.getText());

        return new JTextField[]{regionCityHotel, adults, children, arrival, departure};
    }

    private ArrayList<String> getStringListOfDates() {
        ArrayList<String> list = new ArrayList<>();

        list.add(ftf_searchroom_arrival.getText());
        list.add(ftf_searchroom_departure.getText());

        return list;
    }
}
