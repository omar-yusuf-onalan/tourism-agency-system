package view.PopupView;

import business.LodgingManager;
import core.Utility;
import entity.Facility;
import entity.Hotel;
import entity.Lodging;
import view.Layout;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class LodgingPopupView extends Layout {
    private JPanel container;
    private JPanel pnl_lodging;
    private JCheckBox cb_lodging_ultraeverything;
    private JLabel lbl_lodging_ultraeverything;
    private JLabel lbl_lodging_everything;
    private JLabel lbl_lodging_roombreakfast;
    private JLabel lbl_lodging_fulllodging;
    private JLabel lbl_lodging_halflodging;
    private JLabel lbl_lodging_onlybed;
    private JLabel lbl_lodging_fullcredit;
    private JCheckBox cb_lodging_everything;
    private JCheckBox cb_lodging_roombreakfast;
    private JCheckBox cb_lodging_fulllodging;
    private JCheckBox cb_lodging_halflodging;
    private JCheckBox cb_lodging_onlybed;
    private JCheckBox cb_lodging_fullcredit;
    private JLabel lbl_lodging;
    private JButton btn_lodging_update;

    private Hotel hotel;
    private LodgingManager lodgingManager;

    private boolean result;

    public LodgingPopupView(Hotel hotel) {
        this.add(container);
        this.hotel = hotel;

        this.lodgingManager = new LodgingManager();

        this.initView(200, 250, "Lodging Access");

        displayLodgingData();
        initAccessButton();
    }

    public void displayLodgingData() {
        cb_lodging_ultraeverything.setSelected(lodgingManager.findByHotelIDAndType(hotel.getHotelID(), "Ultra Everything") != null);
        cb_lodging_everything.setSelected(lodgingManager.findByHotelIDAndType(hotel.getHotelID(), "Everything") != null);
        cb_lodging_roombreakfast.setSelected(lodgingManager.findByHotelIDAndType(hotel.getHotelID(), "Room Breakfast") != null);
        cb_lodging_fulllodging.setSelected(lodgingManager.findByHotelIDAndType(hotel.getHotelID(), "Full Lodging") != null);
        cb_lodging_halflodging.setSelected(lodgingManager.findByHotelIDAndType(hotel.getHotelID(), "Half Lodging") != null);
        cb_lodging_onlybed.setSelected(lodgingManager.findByHotelIDAndType(hotel.getHotelID(), "Only Bed") != null);
        cb_lodging_fullcredit.setSelected(lodgingManager.findByHotelIDAndType(hotel.getHotelID(), "Full Credit") != null);
    }

    public void initAccessButton() {
        btn_lodging_update.addActionListener(e -> {
            HashMap<String, Boolean> typeHashMap = new HashMap<>(7);

            boolean ultraEverything = cb_lodging_ultraeverything.isSelected();
            boolean everything = cb_lodging_everything.isSelected();
            boolean roomBreakfast = cb_lodging_roombreakfast.isSelected();
            boolean fullLodging = cb_lodging_fulllodging.isSelected();
            boolean halfLodging = cb_lodging_halflodging.isSelected();
            boolean onlyBed = cb_lodging_onlybed.isSelected();
            boolean fullCredit = cb_lodging_fullcredit.isSelected();

            typeHashMap.put("Ultra Everything", ultraEverything);
            typeHashMap.put("Everything", everything);
            typeHashMap.put("Room Breakfast", roomBreakfast);
            typeHashMap.put("Full Lodging", fullLodging);
            typeHashMap.put("Half Lodging", halfLodging);
            typeHashMap.put("Only Bed", onlyBed);
            typeHashMap.put("Full Credit", fullCredit);

            typeHashMap.forEach(
                (key, value) -> {

                    if (!value) {

                        Lodging lodging;

                        if ((lodging = lodgingManager.findByHotelIDAndType(hotel.getHotelID(), key)) != null) {

                            this.result = lodgingManager.delete(lodging.getLodgingID());

                        }


                    } else {

                        if (lodgingManager.findByHotelIDAndType(hotel.getHotelID(), key) == null) {
                            Lodging lodging = new Lodging();
                            lodging.setHotelID(hotel.getHotelID());
                            lodging.setType(key);

                            this.result = lodgingManager.add(lodging);

                        }

                    }
                }
            );

            if (!result) {
                Utility.showMessage("error");
                return;
            }

            Utility.showMessage("success");
            dispose();
        });
    }
}

