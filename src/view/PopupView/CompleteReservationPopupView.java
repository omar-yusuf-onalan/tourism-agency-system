package view.PopupView;

import business.HotelManager;
import business.LodgingManager;
import business.ReservationManager;
import business.RoomManager;
import core.Utility;
import entity.Reservation;
import entity.Room;
import view.Layout;

import javax.swing.*;
import java.sql.Date;
import java.text.ParseException;
import java.util.*;

public class CompleteReservationPopupView extends Layout {
    private JPanel container;
    private JTextArea ta_completereservation_guestinformation;
    private JButton btn_completereservation_submit;
    private JTextField tf_completereservation_contactname;
    private JTextField tf_completereservation_contacttelephone;
    private JTextField tf_completereservation_contactemail;
    private JTextField tf_completereservation_reservationnote;
    RoomManager roomManager;
    HotelManager hotelManager;
    LodgingManager lodgingManager;
    ReservationManager reservationManager;

    ArrayList<String> guestInformation;
    Room room;

    public CompleteReservationPopupView(ArrayList<String> guestInformation, Room room) {
        this.add(container);
        this.initView(800, 700, "Complete Reservation");

        this.roomManager = RoomManager.getInstance();
        this.hotelManager = new HotelManager();
        this.lodgingManager = new LodgingManager();
        this.reservationManager = ReservationManager.getInstance();

        this.guestInformation = guestInformation;
        this.room = room;

        initSubmitButton();
        fillTextArea();
    }

    private void initSubmitButton() {
        btn_completereservation_submit.addActionListener(e -> {
            JTextField[] contactInformation = getContactInformation();

            if (Utility.isFieldListEmpty(contactInformation)) {
                Utility.showMessage("fill");
                return;
            }

            String[] guestInformation = getGuestInformationFromTextArea();

            ArrayList<String> stringList = new ArrayList<>();

            stringList.add(this.guestInformation.get(2));
            stringList.add(this.guestInformation.get(3));

            ArrayList<Date> dateList;

            try {
                dateList = Utility.parseDates(stringList);
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }

            Reservation reservation = getReservation(contactInformation, guestInformation, dateList);

            if (this.reservationManager.add(reservation))
                Utility.showMessage("success");
            else Utility.showMessage("error");

            dispose();
        });
    }

    private Reservation getReservation(JTextField[] contactInformation, String[] guestInformation, ArrayList<Date> dateList) {
        int ignoreID = 0;
        int roomID = this.room.getRoomID();

        String contactName = contactInformation[0].getText();
        String contactTelephone = contactInformation[1].getText();
        String contactEmail = contactInformation[2].getText();

        String note = contactInformation[3].getText();

        String adultInformation = guestInformation[1];
        String childInformation = guestInformation[0];

        Date arrival = dateList.get(0);
        Date departure = dateList.get(1);

        return new Reservation(
                ignoreID, roomID, contactName, contactTelephone, contactEmail,
                note, adultInformation, childInformation, arrival, departure
        );
    }

    private void fillTextArea() {
        int adultNumber = Integer.parseInt(this.guestInformation.get(0));
        int childNumber = Integer.parseInt(this.guestInformation.get(1));

        for (int i = 0; i < adultNumber; i++) {
            ta_completereservation_guestinformation.append("Adult " + (i + 1));
            ta_completereservation_guestinformation.append("\n");
            ta_completereservation_guestinformation.append("\n");
            ta_completereservation_guestinformation.append("Name: ");
            ta_completereservation_guestinformation.append("\n");
            ta_completereservation_guestinformation.append("\n");
            ta_completereservation_guestinformation.append("Country of Residence: ");
            ta_completereservation_guestinformation.append("\n");
            ta_completereservation_guestinformation.append("\n");
            ta_completereservation_guestinformation.append("Identification/Passport Number: ");
            ta_completereservation_guestinformation.append("\n");
            ta_completereservation_guestinformation.append("\n");
        }

        ta_completereservation_guestinformation.append("\n");
        ta_completereservation_guestinformation.append("!");
        ta_completereservation_guestinformation.append("\n");

        for (int i = 0; i < childNumber; i++) {
            ta_completereservation_guestinformation.append("Child " + (i + 1));
            ta_completereservation_guestinformation.append("\n");
            ta_completereservation_guestinformation.append("\n");
            ta_completereservation_guestinformation.append("Name: ");
            ta_completereservation_guestinformation.append("\n");
            ta_completereservation_guestinformation.append("\n");
            ta_completereservation_guestinformation.append("Country of Residence: ");
            ta_completereservation_guestinformation.append("\n");
            ta_completereservation_guestinformation.append("\n");
            ta_completereservation_guestinformation.append("Identification/Passport Number: ");
            ta_completereservation_guestinformation.append("\n");
            ta_completereservation_guestinformation.append("\n");
        }
    }

    private JTextField[] getContactInformation() {
        return new JTextField[] {tf_completereservation_contactname, tf_completereservation_contacttelephone,
                tf_completereservation_contactemail, tf_completereservation_reservationnote};
    }
    private String[] getGuestInformationFromTextArea() {
        String str = ta_completereservation_guestinformation.getText();

        String[] strArray = str.split("!");

        System.out.println(Arrays.toString(strArray));

        return strArray;
    }

}
