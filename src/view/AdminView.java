package view;

import business.UserManager;
import core.Utility;
import entity.User;
import view.PopupView.UserPopupView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class AdminView extends Layout {
    private JPanel container;
    private JButton btn_user_logout;
    private JTabbedPane tab_admin;
    private JPanel pnl_user_list;
    private JScrollPane scrl_user_list;
    private JTable tbl_user_list;
    private JButton btn_user_search;
    private JButton btn_user_add;
    private JLabel lbl_user_welcome;

    DefaultTableModel mdl_user_list;
    Object[] row_user_list;

    private User user;

    private final UserManager userManager;

    public AdminView(User user) {
        this.user = user;
        this.userManager = new UserManager();

        this.add(container);
        this.initView(800, 600, "Admin Panel");
        this.lbl_user_welcome.setText("Welcome " + user.getName());

        initUserList();
        initUserPopupMenu();

        initLogoutButton();
    }

    private void initUserList() {
        mdl_user_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0 || column == 1 || column == 2 || column == 3 || column == 4)
                    return false;
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_user_list = {"ID", "Name", "Email", "Password", "Role"};
        mdl_user_list.setColumnIdentifiers(col_user_list);
        row_user_list = new Object[col_user_list.length];
        loadUserModel();

        tbl_user_list.setModel(mdl_user_list);
        tbl_user_list.getTableHeader().setReorderingAllowed(false);
    }

    private void initUserPopupMenu() {
        JPopupMenu userListPopup = new JPopupMenu();

        JMenuItem updateUser = new JMenuItem("Update User");
        JMenuItem deleteUser = new JMenuItem("Delete User");

        userListPopup.add(updateUser);
        userListPopup.add(deleteUser);

        createMouseListener(userListPopup);
        createAddUserListeners();
        createSearchUserListeners();
        createUpdateUserListeners(updateUser);
        createDeleteUserListeners(deleteUser);
    }

    private void createMouseListener(JPopupMenu userListPopup) {
        tbl_user_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

                int r = tbl_user_list.rowAtPoint(e.getPoint());
                if (r >= 0 && r < tbl_user_list.getRowCount()) {
                    tbl_user_list.setRowSelectionInterval(r, r);
                } else {
                    tbl_user_list.clearSelection();
                }

                int rowIndex = tbl_user_list.getSelectedRow();

                if (rowIndex < 0)
                    return;
                if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
                    userListPopup.show(e.getComponent(), e.getX(), e.getY());
                }

            }
        });
    }

    private void createAddUserListeners() {
        btn_user_add.addActionListener(e -> {
            UserPopupView userPopupGUI = new UserPopupView(new User(), "add");
            userPopupGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadUserModel();
                }
            });
        });
    }

    private void createSearchUserListeners() {
        btn_user_search.addActionListener(e -> {
            UserPopupView userPopupGUI = new UserPopupView(new User(), "search");
            userPopupGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    ArrayList<User> filteredUsers = UserPopupView.getFilteredUsers();
                    if (!(filteredUsers == null)) {
                        loadUserModel(filteredUsers);
                    } else {
                        loadUserModel();
                    }
                }
            });
        });
    }

    private void createUpdateUserListeners(JMenuItem updateUser) {
        updateUser.addActionListener(e -> {

            int selectedId = Integer.parseInt(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow() , 0).toString());
            User selectedUser = this.userManager.findById(selectedId);

            UserPopupView userPopupView = new UserPopupView(selectedUser, "update");
            userPopupView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadUserModel();
                }
            });
        });
    }

    private void createDeleteUserListeners(JMenuItem deleteUser) {
        deleteUser.addActionListener(e -> {
            if (!Utility.confirm("sure")){
                return;
            }

            int selectedId = Integer.parseInt(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow() , 0).toString());

            if (this.userManager.delete(selectedId)){
                Utility.showMessage("success");
                loadUserModel();
                return;
            }

            Utility.showMessage("error");

        });
    }

    private void loadUserModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (User obj : this.userManager.getList()) {
            i = 0;
            row_user_list[i++] = obj.getId();
            row_user_list[i++] = obj.getName();
            row_user_list[i++] = obj.getEmail();
            row_user_list[i++] = obj.getPass();
            row_user_list[i++] = obj.getRole();
            mdl_user_list.addRow(row_user_list);
        }
    }

    private void loadUserModel(ArrayList<User> userList) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);

        for (User obj : userList) {
            int i = 0;
            row_user_list[i++] = obj.getId();
            row_user_list[i++] = obj.getName();
            row_user_list[i++] = obj.getEmail();
            row_user_list[i++] = obj.getPass();
            row_user_list[i++] = obj.getRole();
            mdl_user_list.addRow(row_user_list);
        }
    }

    private void initLogoutButton() {
        btn_user_logout.addActionListener(e -> {
            dispose();

            LoginView loginView = new LoginView();
        });
    }
}
