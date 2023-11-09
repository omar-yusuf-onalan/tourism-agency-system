package view;

import business.UserManager;
import core.Utility;
import entity.User;

import javax.swing.*;
import java.awt.*;

public class LoginView extends Layout {
    private JPanel container;
    private JPanel pnl_user_login;
    private JLabel lbl_user_login;
    private JLabel lbl_user_email;
    private JTextField tf_user_email;
    private JLabel lbl_user_pass;
    private JTextField tf_user_pass;
    private JButton btn_user_login;
    private final UserManager userManager;

    public LoginView() {
        this.userManager = new UserManager();

        this.add(container);
        this.initView(400, 400, "User Login");

        btn_user_login.addActionListener(e -> {
            JTextField[] checkFieldList = {this.tf_user_email, this.tf_user_pass};
            if (Utility.isFieldListEmpty(checkFieldList)) {
                Utility.showMessage("fill");
                return;
            }

            User user = this.userManager.findByLogin(tf_user_email.getText(), tf_user_pass.getText());

            if (user == null) {
                Utility.showMessage("notFound");
                return;
            }

            Utility.showMessage("success");

            login(user);

        });
        }

    public void login(User user) {
        switch (user.getRole()) {
            case "admin" -> new AdminView(user);
            case "agent" -> new AgentView(user);
        }
        dispose();
    }
    }
