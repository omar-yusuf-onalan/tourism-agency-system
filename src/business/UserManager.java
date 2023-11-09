package business;

import core.Utility;
import dao.UserDAO;
import entity.User;

import java.util.ArrayList;

public class UserManager {
    private final UserDAO userDAO;

    public UserManager() {
        this.userDAO = new UserDAO();
    }

    public ArrayList<User> getList() {
        return this.userDAO.getList();
    }

    public ArrayList<User> searchList(String name, String email, String role) {
        return this.userDAO.searchList(name, email, role);
    }

    public boolean add(User user) {
        if (this.userDAO.findByEmail(user.getEmail()) != null) {
            Utility.showMessage("error");
            return false;
        }
        return this.userDAO.add(user);
    }

    public ArrayList<User> getListByRole(String role) {
        return this.userDAO.getListByRole(role);
    }

    public User findByLogin(String email, String pass) {
        return this.userDAO.findByLogin(email, pass);
    }

    public User findByEmail(String email) {
        return findByEmail(email);
    }

    public User findById(int id) {
        return this.userDAO.findById(id);
    }

    public boolean update(User user) {
        if (this.userDAO.findByEmail(user.getEmail()) != null && this.userDAO.findByEmail(user.getEmail()).getId() != user.getId()) {
            Utility.showMessage("User already exists\nEnter a different email");
            return false;
        }

        if (!user.getRole().equals("admin") && !user.getRole().equals("agent")) {
            Utility.showMessage("Role must be set to admin or agent");
            return false;
        }

        return this.userDAO.update(user);
    }


    public boolean delete(int id) {
        return this.userDAO.delete(id);
    }
}
