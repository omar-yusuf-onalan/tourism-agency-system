package core;

import business.FacilityManager;
import business.HotelManager;
import business.PeriodManager;
import entity.Facility;
import entity.Hotel;
import entity.Period;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.sql.Date;

public class Utility {
    public static void setTheme(String layoutName) {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if (layoutName.equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException |
                         InstantiationException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }
    }

    public static int getScreenCenter(String axis, Dimension size) {
        return switch (axis) {
            case "x" -> (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
            case "y" -> (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
            default -> 0;
        };
    }

    public static boolean isFieldEmpty(JTextField field) {
        return field.getText().trim().isEmpty();
    }

    public static boolean isFieldListEmpty(JTextField[] fieldList) {
        for (JTextField field : fieldList) {
            if (isFieldEmpty(field)) return true;
        }

        return false;
    }

    public static boolean isComboBoxEmpty(JComboBox cmb) {
        return cmb.getModel().getSelectedItem().equals("");
    }

    public static void showMessage(String str) {
        String message = "";
        String title = "";

        switch (str) {
            case "fill" -> {
                message = "Please fill in all details";
                title = "Fill Error";
            }
            case "success" -> {
                message = "Operation successful";
                title = "Result";
            }

            case "error" -> {
                message = "An error has occurred";
                title = "Error";
            }

            case "notFound" -> {
                message = "Record not found";
                title = "Not Found";
            }

            case "invalid date" -> {
                message = "Invalid Date. Valid Dates are in yyyy-MM-dd format";
                title = "Invalid Date";
            }
            default -> message = str;
        }

        JOptionPane.showMessageDialog(
                null, message,
                title, JOptionPane.INFORMATION_MESSAGE);

    }

    public static String createSearchQuery(String table, String[] searchCriteria, String logicalOperator) {
        String query = "SELECT * FROM " + table + " WHERE";
        boolean useOperator = false;

        for (int i = 0; i < searchCriteria.length; i += 2) {
            if (searchCriteria[i + 1].isEmpty()) {
                continue;
            }

            if (useOperator) {
                query += " " + logicalOperator;
            }

            query += " " + searchCriteria[i] + " LIKE " + "'%" + searchCriteria[i + 1] + "%'";

            if (!useOperator) {
                useOperator = true;
            }
        }

        return query;
    }

    public static boolean confirm(String messageType){
        optionPane();
        String message;

        switch (messageType){
            case "sure":
                message = "Are you sure you would like to perform this operation?";
                break;
            default:
                message = messageType;
        }

        return JOptionPane.showConfirmDialog(null , message , "Confirm Operation" , JOptionPane.YES_NO_OPTION) == 0;
    }

    public static void optionPane(){
        UIManager.put("OptionPane.okButtonText" , "Ok");
        UIManager.put("OptionPane.yesButtonText" , "Yes");
        UIManager.put("OptionPane.noButtonText" , "No");
    }

    public static ArrayList<Date> parseDates(ArrayList<String> dateList) throws ParseException {
        ArrayList<Date> formattedDates = new ArrayList<>();

        for (String str : dateList) {
            Date date = Date.valueOf(str.trim());
            formattedDates.add(date);
        }

        return formattedDates;
    }

    public static int getDayDifference(Date dateOne, Date dateTwo) {
        LocalDate firstDate = dateOne.toLocalDate();
        LocalDate secondDate = dateTwo.toLocalDate();

        return (int) ChronoUnit.DAYS.between(firstDate, secondDate);
    }

    public static String whichPeriod(ArrayList<Date> dateList, Period period) {
        LocalDate winterStart = period.getWinterStart().toLocalDate();
        LocalDate winterEnd = period.getWinterEnd().toLocalDate();
        LocalDate summerStart = period.getSummerStart().toLocalDate();
        LocalDate summerEnd = period.getSummerEnd().toLocalDate();

        LocalDate arrival = dateList.get(0).toLocalDate();
        LocalDate departure = dateList.get(1).toLocalDate();

        if (arrival.isAfter(winterStart) && departure.isBefore(winterEnd))
            return "winter";

        if (arrival.isAfter(summerStart) && departure.isBefore(summerEnd))
            return "summer";

        return null;
    }

    public static String parseFacility(Hotel hotel) {
        FacilityManager facilityManager = new FacilityManager();

        String output = "";

        for (Facility facility : facilityManager.getListByHotelID(hotel.getHotelID())) {

            output += facility.getType() + ", ";

        }

        return output.trim();
    }
}
