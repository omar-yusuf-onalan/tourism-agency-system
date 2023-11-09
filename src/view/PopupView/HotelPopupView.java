package view.PopupView;

import business.HotelManager;
import core.Utility;
import entity.Hotel;
import entity.User;
import view.Layout;

import javax.swing.*;
import java.util.ArrayList;

public class HotelPopupView extends Layout {
    private JPanel pnl_hotel_popup;
    private JLabel lbl_hotel_action;
    private JLabel lbl_hotel_name;
    private JTextField tf_hotel_name;
    private JLabel lbl_hotel_email;
    private JTextField tf_hotel_email;
    private JButton btn_hotel_action;
    private JPanel container;
    private JTextField tf_hotel_city;
    private JTextField tf_hotel_address;
    private JTextField tf_hotel_telephone;
    private JLabel lbl_hotel_city;
    private JLabel lbl_hotel_address;
    private JLabel lbl_hotel_telephone;
    private JLabel lbl_hotel_star;
    private JComboBox cmb_hotel_star;
    private JTextField tf_hotel_region;
    private JLabel lbl_hotel_region;

    private Hotel hotel;
    private HotelManager hotelManager;

    private static ArrayList<Hotel> filteredHotels;

    public HotelPopupView(Hotel hotel, String actionType) {
        this.add(container);
        this.hotel = hotel;
        this.hotelManager = new HotelManager();
        this.initView(400, 500, actionType);

        switch (actionType) {
            case "update" -> {
                displayHotelData();
                initUpdateButton();
            }
            case "add" -> initAddButton();
            case "search" -> initSearchButton();
        }
    }

    private void displayHotelData() {
        tf_hotel_name.setText(hotel.getName());
        tf_hotel_city.setText(hotel.getCity());
        tf_hotel_region.setText(hotel.getRegion());
        tf_hotel_address.setText(hotel.getAddress());
        tf_hotel_email.setText(hotel.getEmail());
        tf_hotel_telephone.setText(hotel.getTelephone());

        cmb_hotel_star.setSelectedIndex(Integer.parseInt(this.hotel.getStar()));
    }

    private void initUpdateButton() {
        lbl_hotel_action.setText("Update Hotel");
        btn_hotel_action.setText("Update");
        btn_hotel_action.addActionListener(e -> {
            JTextField[] checkTextField = {tf_hotel_name, tf_hotel_city, tf_hotel_region, tf_hotel_address, tf_hotel_email, tf_hotel_telephone};

            if (Utility.isFieldListEmpty(checkTextField)){
                Utility.showMessage("fill");
                return;
            }

            if (this.hotelManager.update(this.getHotelFromTextFields(hotel.getHotelID()))) {
                Utility.showMessage("success");
            }

            dispose();

        });
    }

    private void initAddButton() {
        lbl_hotel_action.setText("Add Hotel");
        btn_hotel_action.setText("Add");

        btn_hotel_action.addActionListener(e -> {
            JTextField[] checkTextField = {tf_hotel_name, tf_hotel_city, tf_hotel_region, tf_hotel_address, tf_hotel_email, tf_hotel_telephone};

            if (Utility.isFieldListEmpty(checkTextField)){
                Utility.showMessage("fill");
                return;
            }

            if (this.hotelManager.add(this.getHotelFromTextFields(0))) {
                Utility.showMessage("success");
            }

            dispose();
        });
    }

    private void initSearchButton() {
        lbl_hotel_action.setText("Search Hotel");
        btn_hotel_action.setText("Search");

        this.pnl_hotel_popup.remove(lbl_hotel_address);
        this.pnl_hotel_popup.remove(tf_hotel_address);

        this.pnl_hotel_popup.remove(lbl_hotel_email);
        this.pnl_hotel_popup.remove(tf_hotel_email);

        this.pnl_hotel_popup.remove(lbl_hotel_telephone);
        this.pnl_hotel_popup.remove(tf_hotel_telephone);

        btn_hotel_action.addActionListener(e -> {
            Hotel hotel = getHotelFromTextFields(this.hotel.getHotelID());


            if (hotel.getName().isEmpty() && hotel.getCity().isEmpty() && hotel.getRegion().isEmpty() && hotel.getStar().isEmpty()) {
                dispose();
                return;
            }

            filteredHotels = this.hotelManager.searchList(hotel);

            dispose();
        });
    }

    private Hotel getHotelFromTextFields(int id) {
        String name = tf_hotel_name.getText();
        String city = tf_hotel_city.getText();
        String region = tf_hotel_region.getText();
        String address = tf_hotel_address.getText();
        String email = tf_hotel_email.getText();
        String telephone = tf_hotel_telephone.getText();
        String star = cmb_hotel_star.getSelectedItem().toString();

        return new Hotel(id, name, city, region, address, email, telephone, star);
    }

    public static ArrayList<Hotel> getFilteredHotels() {
        return filteredHotels;
    }
}
